package com.hao.tmusicmanagement.service.impl;

import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hao.tmusicmanagement.Exception.TMusicException;
import com.hao.tmusicmanagement.dao.AdminDao;
import com.hao.tmusicmanagement.pojo.Admin;
import com.hao.tmusicmanagement.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminDao adminDao;


    @Override
    public String login(String username, String password) {
        LambdaQueryWrapper<Admin> eq = new LambdaQueryWrapper<Admin>().eq(Admin::getAccount, username);
        Admin admin = adminDao.selectOne(eq);
        if(admin==null){
            throw new TMusicException("用户名或密码错误", 400);
        }
        String s = SaSecureUtil.md5(password);
        if(s.equals(admin.getPassword().toLowerCase())){
            StpUtil.login(admin.getId());
            SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
            return StpUtil.getTokenValue();
        }else{
            throw new TMusicException("用户名或密码错误", 400);
        }
    }

    @Override
    public String logout() {
        System.out.println(StpUtil.getTokenInfo());
        StpUtil.logout();
        return null;
    }
}
