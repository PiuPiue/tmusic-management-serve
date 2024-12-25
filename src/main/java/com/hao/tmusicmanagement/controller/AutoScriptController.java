package com.hao.tmusicmanagement.controller;

import com.hao.tmusicmanagement.core.AjaxResult;
import com.hao.tmusicmanagement.service.AutoScriptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 实现数据爬取
 */
@RestController
@RequestMapping("/script")
public class AutoScriptController {

    @Autowired
    private AutoScriptService autoScriptService;

    @GetMapping("/addlist")
    public AjaxResult crawlList(String type,Integer startPage,Integer endPage){
        try {
            autoScriptService.getSongList(type,startPage,endPage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new AjaxResult("添加成功", "200");
    }

}
