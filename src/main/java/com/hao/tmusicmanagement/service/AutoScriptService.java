package com.hao.tmusicmanagement.service;

import java.io.IOException;

public interface AutoScriptService {


    //实现爬取脚本
    void getSongList(String type,Integer startPage,Integer endPage) throws Exception;
}
