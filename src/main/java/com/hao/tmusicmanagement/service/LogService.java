package com.hao.tmusicmanagement.service;

public interface LogService {
    //实现日志的分页查询
    public void getLogByPage(Integer page, Integer pageSize);
}
