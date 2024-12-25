package com.hao.tmusicmanagement;

import com.hao.tmusicmanagement.dao.LogDao;
import com.hao.tmusicmanagement.service.AutoScriptService;
import com.hao.tmusicmanagement.service.MemoryService;
import com.hao.tmusicmanagement.service.MonitorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
class TMusicManagementApplicationTests {

    @Autowired
    private AutoScriptService autoScriptService;

    @Autowired
    private MonitorService monitorService;

    @Autowired
    private LogDao logDao;

    @Autowired
    private MemoryService memoryService;
    @Test
    void contextLoads() {
        try {
           autoScriptService.getSongList("ee",4,1);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testMonitor(){
        System.out.println(monitorService.getCpuInfo());
        System.out.println(monitorService.getMemoryInfo());
        System.out.println(monitorService.getOsInfo());
        System.out.println(monitorService.getTopMemoryProcesses());
    }
    @Test
    void testLog(){
        System.out.println(logDao.selectList(null));
    }

    @Test
    void testMemory(){
        System.out.println(memoryService.showMemory());
    }

}
