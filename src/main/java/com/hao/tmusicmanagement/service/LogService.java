package com.hao.tmusicmanagement.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hao.tmusicmanagement.pojo.LogInfo;

import java.time.LocalDateTime;
import java.util.List;

public interface LogService {
    //实现日志的分页查询
    public Page<LogInfo> getLogByPage(Integer page, Integer pageSize);

    //获取相应数据
    public List<LogInfo> export(Integer type,Integer startPage, Integer endPage, Integer pageSize, LocalDateTime time);

}
