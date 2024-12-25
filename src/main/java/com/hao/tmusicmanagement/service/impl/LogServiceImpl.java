package com.hao.tmusicmanagement.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hao.tmusicmanagement.dao.LogDao;
import com.hao.tmusicmanagement.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogServiceImpl implements LogService {

    @Autowired
    private LogDao logDao;

    @Override
    public void getLogByPage(Integer page, Integer pageSize) {
        
    }
}
