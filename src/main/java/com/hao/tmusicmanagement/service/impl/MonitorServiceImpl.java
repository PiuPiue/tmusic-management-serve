package com.hao.tmusicmanagement.service.impl;

import com.hao.tmusicmanagement.service.MonitorService;
import org.springframework.stereotype.Service;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HardwareAbstractionLayer;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.util.*;

@Service
public class MonitorServiceImpl implements MonitorService {
    private final SystemInfo systemInfo;
    private final OperatingSystemMXBean operatingSystemMXBean;

    public MonitorServiceImpl() {
        this.systemInfo = new SystemInfo();
        this.operatingSystemMXBean = ManagementFactory.getOperatingSystemMXBean();
    }

    // 获取CPU信息
    public String getCpuInfo() {
        HardwareAbstractionLayer hardware = systemInfo.getHardware();
        CentralProcessor processor = hardware.getProcessor();
        int cpuCoreCount = processor.getLogicalProcessorCount(); // 逻辑CPU核心数
        double systemLoadAverage = processor.getSystemLoadAverage(1)[0]; // 系统负载
        return "CPU核心数: " + cpuCoreCount + "\n" + "系统负载: " + systemLoadAverage;
    }

    // 获取内存信息
    public String getMemoryInfo() {
        HardwareAbstractionLayer hardware = systemInfo.getHardware();
        GlobalMemory memory = hardware.getMemory();
        long totalMemory = memory.getTotal(); // 总内存
        long availableMemory = memory.getAvailable(); // 可用内存
        long usedMemory = totalMemory - availableMemory; // 已用内存
        return "总内存: " + totalMemory / (1024 * 1024 * 1024) + " GB\n" +
                "已用内存: " + usedMemory / (1024 * 1024 * 1024) + " GB\n" +
                "可用内存: " + availableMemory / (1024 * 1024 * 1024) + " GB";
    }

    // 获取操作系统信息
    public String getOsInfo() {
        return "操作系统: " + systemInfo.getOperatingSystem().toString();
    }

    // 获取内存使用最多的前五个进程
    public String getTopMemoryProcesses() {
        List<Map<String, Object>> processList = getProcessesMemoryUsage();

        // 排序并获取前五个内存占用最多的进程
        processList.sort((p1, p2) -> Long.compare((Long) p2.get("memory"), (Long) p1.get("memory")));

        StringBuilder sb = new StringBuilder("内存占用最多的前五个进程:\n");

        for (int i = 0; i < Math.min(5, processList.size()); i++) {
            Map<String, Object> process = processList.get(i);
            long memoryInBytes = (Long) process.get("memory");  // 获取内存字节数
            double memoryInMB = memoryInBytes / (1024.0 * 1024.0);  // 转换为MB
            sb.append("进程ID: ").append(process.get("pid"))
                    .append(", 进程名称: ").append(process.get("name"))
                    .append(", 内存使用: ").append(String.format("%.2f", memoryInMB))  // 输出两位小数
                    .append(" MB\n");
        }

        return sb.toString();
    }

    // 获取所有进程的内存使用情况
    private List<Map<String, Object>> getProcessesMemoryUsage() {
        List<Map<String, Object>> processes = new ArrayList<>();

        String os = operatingSystemMXBean.getName().toLowerCase();
        if (os.contains("win")) {
            processes = getWindowsProcesses();
        } else if (os.contains("nix") || os.contains("nux") || os.contains("mac")) {
            processes = getUnixProcesses();
        }

        return processes;
    }

    // 获取Windows系统进程的内存使用情况
    private List<Map<String, Object>> getWindowsProcesses() {
        List<Map<String, Object>> processes = new ArrayList<>();

        try {
            // 执行命令获取进程信息
            String command = "tasklist /fo csv /nh"; // CSV格式
            Process process = Runtime.getRuntime().exec(command);
            Scanner scanner = new Scanner(process.getInputStream());

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] details = line.split(",");
                Map<String, Object> processMap = new HashMap<>();
                processMap.put("name", details[0].replace("\"", ""));
                processMap.put("pid", details[1].replace("\"", ""));
                processMap.put("memory", Long.parseLong(details[4].replace("\"", "").replace(" K", "")) * 1024);
                processes.add(processMap);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return processes;
    }

    // 获取类Unix系统（Linux/macOS）进程的内存使用情况
    private List<Map<String, Object>> getUnixProcesses() {
        List<Map<String, Object>> processes = new ArrayList<>();

        try {
            // 执行命令获取进程信息
            String command = "ps aux --sort=-%mem";
            Process process = Runtime.getRuntime().exec(command);
            Scanner scanner = new Scanner(process.getInputStream());

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (!line.startsWith("USER")) { // 忽略标题行
                    String[] details = line.split("\\s+");
                    Map<String, Object> processMap = new HashMap<>();
                    processMap.put("name", details[10]);
                    processMap.put("pid", details[1]);
                    processMap.put("memory", Long.parseLong(details[3]) * 1024); // 转换为字节
                    processes.add(processMap);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return processes;
    }
}
