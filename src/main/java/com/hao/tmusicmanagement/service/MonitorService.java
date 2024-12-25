package com.hao.tmusicmanagement.service;

public interface MonitorService {
    String getCpuInfo();
    String getMemoryInfo();
    String getOsInfo();
    String getTopMemoryProcesses();

}
