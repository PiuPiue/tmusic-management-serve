package com.hao.tmusicmanagement.service.impl;

import com.hao.tmusicmanagement.pojo.MonitorBean;
import com.hao.tmusicmanagement.service.MonitorService;
import org.springframework.stereotype.Service;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HWDiskStore;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.software.os.FileSystem;
import oshi.software.os.OperatingSystem;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        // 排序并获取前十个内存占用最多的进程
        processList.sort((p1, p2) -> Long.compare((Long) p2.get("memory"), (Long) p1.get("memory")));
        StringBuilder sb = new StringBuilder("内存占用最多的前五个进程:\n");

        for (int i = 0; i < Math.min(10, processList.size()); i++) {
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


    // 返回系统监控信息的完整方法
    public MonitorBean getMonitorInfo() {
        File file = null;
        HardwareAbstractionLayer hardware = systemInfo.getHardware();
        CentralProcessor processor = hardware.getProcessor();

        GlobalMemory memory = hardware.getMemory();

        // CPU信息
        int cpuCoreCount = processor.getLogicalProcessorCount();
        double systemLoadAverage = processor.getSystemLoadAverage(1)[0]; // 过去1分钟的平均负载
        // 获取 CPU 使用率
        long[] previousTicks = processor.getSystemCpuLoadTicks(); // 初始的 CPU 状态
        try {
            // 等待一段时间以便获取新的 CPU 状态
            Thread.sleep(1000); // 延迟 1 秒
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 获取当前 CPU 状态并计算 CPU 使用率
        double cpuUsage = processor.getSystemCpuLoadBetweenTicks(previousTicks) * 100;

        // 内存信息
        long totalMemory = memory.getTotal();
        long availableMemory = memory.getAvailable();
        long usedMemory = totalMemory - availableMemory;

        // 系统信息
        String systemInfo = this.getOsInfo();

        // 获取内存占用最多的前五个进程
        List<MonitorBean.ProcessInfo> topProcesses = getTopProcesses();

        //获取磁盘信息
        long sum = hardware.getDiskStores().stream().mapToLong(HWDiskStore::getSize).sum()/1024/1024/1024;
        long used;
        if(operatingSystemMXBean.getName().toLowerCase().contains("win")){
            file = new File("C:\\");
            used = file.getTotalSpace()/1024/1024/1024-file.getFreeSpace()/1024/1024/1024;
            file = new File("D:\\");
            used += file.getTotalSpace()/1024/1024/1024-file.getFreeSpace()/1024/1024/1024;
        }else{
            file = new File("/");
            used = file.getTotalSpace()/1024/1024/1024-file.getFreeSpace()/1024/1024/1024;
        }


        // 构造并返回 MonitorBean 对象
        return new MonitorBean(
                cpuCoreCount,
                systemLoadAverage,
                cpuUsage,
                totalMemory,
                availableMemory,
                usedMemory,
                systemInfo,
                sum,
                used,
                topProcesses
        );
    }

    // 内存占用最多的前五个进程
    private List<MonitorBean.ProcessInfo> getTopProcesses() {
        List<MonitorBean.ProcessInfo> processInfos = new ArrayList<>();

        List<Map<String, Object>> processes = getProcessesMemoryUsage();
        processes.sort((p1, p2) -> Long.compare((Long) p2.get("memory"), (Long) p1.get("memory"))); // 按内存降序排序

        for (int i = 0; i < Math.min(5, processes.size()); i++) {
            Map<String, Object> process = processes.get(i);
            long memoryInBytes = (Long) process.get("memory");
            double memoryInMB = memoryInBytes / (1024.0 * 1024.0);

            processInfos.add(new MonitorBean.ProcessInfo(
                    (String) process.get("name"),
                    (String) process.get("pid"),
                    memoryInMB
            ));
        }

        return processInfos;
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
                if (details.length > 4) {
                    Map<String, Object> processMap = new HashMap<>();
                    processMap.put("name", details[0].replace("\"", ""));
                    processMap.put("pid", details[1].replace("\"", ""));

                    // 处理内存字段
                    String memoryStr = details[4].replace("\"", "").replace(" K", "").trim();
                    if (!memoryStr.isEmpty()) {
                        try {
                            // 转换为字节
                            long memory = Long.parseLong(memoryStr) * 1024;
                            processMap.put("memory", memory);
                        } catch (NumberFormatException e) {
                            processMap.put("memory", 0L);
                        }
                    } else {
                        processMap.put("memory", 0L);
                    }

                    processes.add(processMap);
                }
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

            // 跳过标题行
            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] details = line.split("\\s+");

                // 确保有足够的字段
                if (details.length > 10) {
                    Map<String, Object> processMap = new HashMap<>();
                    processMap.put("name", details[10]);  // 进程名称
                    processMap.put("pid", details[1]);    // 进程ID

                    // 获取内存使用情况（%memory）
                    String memoryStr = details[3]; // 这里是内存使用比例，可以根据需求调整

                    try {
                        // 转换为字节（假设需要转换为实际内存字节数，依据具体系统调整）
                        double memoryPercentage = Double.parseDouble(memoryStr);
                        processMap.put("memory", Math.round(memoryPercentage * 1024 * 1024));  // 假设转换为字节
                    } catch (NumberFormatException e) {
                        processMap.put("memory", 0L);
                    }

                    processes.add(processMap);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return processes;
    }

}
