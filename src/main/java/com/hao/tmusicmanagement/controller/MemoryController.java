package com.hao.tmusicmanagement.controller;

import com.hao.tmusicmanagement.core.AjaxResult;
import com.hao.tmusicmanagement.service.MemoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/memory")
public class MemoryController {

    @Autowired
    private MemoryService memoryService;

    @GetMapping("/getAllMemory")
    public AjaxResult getAllMemory(){
        return new AjaxResult(memoryService.showMemory(),"获取成功", "200");
    }

    @GetMapping("/clearMemory")
    public AjaxResult clearMemory(){
        memoryService.clearMemory();
        return new AjaxResult("清除成功","200");
    }

    @GetMapping("/deleteMemory")
    public AjaxResult deleteMemory(String key){
        memoryService.deleteMemory(key);
        return new AjaxResult("删除成功","200");
    }

}
