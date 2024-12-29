package com.hao.tmusicmanagement.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hao.tmusicmanagement.dao.LogDao;
import com.hao.tmusicmanagement.pojo.LogInfo;
import com.hao.tmusicmanagement.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class LogServiceImpl implements LogService {

    @Autowired
    private LogDao logDao;

    @Override
    public Page<LogInfo> getLogByPage(Integer page, Integer pageSize) {
        Page<LogInfo> logInfoPage = new Page<>(page, pageSize);
        LambdaQueryWrapper<LogInfo> logInfoLambdaQueryWrapper = new LambdaQueryWrapper<LogInfo>().orderByDesc(LogInfo::getTime);
        return logDao.selectPage(logInfoPage, logInfoLambdaQueryWrapper);
    }

    @Override
    public List<LogInfo> export(Integer type,Integer startPage, Integer endPage, Integer pageSize, LocalDateTime time) {
        List<LogInfo> list = new ArrayList<>();
        for (int i = startPage; i <= endPage; i++) {
            Page<LogInfo> logInfoPage = new Page<>(i, pageSize);
            LambdaQueryWrapper<LogInfo> logInfoLambdaQueryWrapper = new LambdaQueryWrapper<LogInfo>().orderByDesc(LogInfo::getTime);
            if (time != null) {
                logInfoLambdaQueryWrapper.ge(LogInfo::getTime, time);
            }
            logInfoLambdaQueryWrapper.eq(type!=null,LogInfo::getType,type);
            IPage<LogInfo> logInfoIPage = logDao.selectPage(logInfoPage, logInfoLambdaQueryWrapper);
            list.addAll(logInfoIPage.getRecords());
        }
        return list;
    }
}
