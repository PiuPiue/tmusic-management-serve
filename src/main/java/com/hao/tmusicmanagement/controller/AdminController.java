package com.hao.tmusicmanagement.controller;

import com.hao.tmusicmanagement.core.AjaxResult;
import com.hao.tmusicmanagement.pojo.Admin;
import com.hao.tmusicmanagement.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/login")
    public String login(@RequestBody Admin admin){
        String token = adminService.login(admin.getAccount(), admin.getPassword());
        return token;
    }

    @GetMapping("/logout")
    public AjaxResult logout(){
        adminService.logout();
        return new AjaxResult("退出成功", "200");
    }

}
