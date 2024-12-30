package com.hao.tmusicmanagement.controller;

import com.hao.tmusicmanagement.core.AjaxResult;
import com.hao.tmusicmanagement.pojo.home.HomeBean;
import com.hao.tmusicmanagement.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 实现首页和监控界面的相关数据返回
 */
@RestController
@RequestMapping("/home")
public class HomeController {

    @Autowired
    private HomeService homeService;

    @GetMapping("/data")
    public AjaxResult<HomeBean> getData() {
        HomeBean homeData = homeService.getHomeData();
        return new AjaxResult<>(homeData,  "获取成功","200");
    }

}
