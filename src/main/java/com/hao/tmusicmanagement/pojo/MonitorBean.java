package com.hao.tmusicmanagement.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MonitorBean {
    private Integer cpuCoreCount;         // CPU核心数
    private Double systemLoadAverage;    // 系统负载平均值
    private Double cpuUsage;            //CPU使用率
    private Long totalMemory;            // 总内存（字节）
    private Long availableMemory;        // 可用内存（字节）
    private Long usedMemory;             // 已用内存（字节）
    private String systemInfo;           // 系统信息
    private Long totalDiskSpace;
    private Long availableDiskSpace;
    private List<ProcessInfo> topProcesses; // 内存占用最多的前五个进程

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ProcessInfo {
        private String name;             // 进程名称
        private String pid;              // 进程ID
        private Double memoryUsageMB;    // 内存使用量（MB）
    }
}
