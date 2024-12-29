package com.hao.tmusicmanagement.controller;

import com.hao.tmusicmanagement.core.AjaxResult;
import com.hao.tmusicmanagement.pojo.MonitorBean;
import com.hao.tmusicmanagement.service.MonitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/monitor")
@RestController
public class MonitorController {

    @Autowired
    private MonitorService monitorService;

    @GetMapping("/getData")
    public AjaxResult getData(){
        return new AjaxResult(monitorService.getMonitorInfo(),"获取成功","200");
    }

}
