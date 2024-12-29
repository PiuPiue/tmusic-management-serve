package com.hao.tmusicmanagement.service;

import com.hao.tmusicmanagement.pojo.MonitorBean;

import java.util.List;

public interface MonitorService {
    String getCpuInfo();
    String getMemoryInfo();
    String getOsInfo();
    String getTopMemoryProcesses();
    MonitorBean getMonitorInfo();

}
