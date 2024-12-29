package com.hao.tmusicmanagement.controller;

import com.alibaba.excel.EasyExcel;
import com.hao.tmusicmanagement.core.AjaxResult;
import com.hao.tmusicmanagement.pojo.LogInfo;
import com.hao.tmusicmanagement.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.aggregation.ArrayOperators;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 实现日志接口
 */
@RestController
@RequestMapping("/log")
public class LogController {

    @Autowired
    private LogService logService;

    @GetMapping ("/getLog")
    public AjaxResult getLog(Integer page, Integer pageSize) {
        return new AjaxResult(logService.getLogByPage(page,pageSize),"获取成功","200");
    }

    @GetMapping("/export")
    public void export(Integer type,Integer startPage, Integer endPage, Integer pageSize, @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime time, HttpServletResponse response) throws IOException {
        List<LogInfo> export = logService.export(type,startPage, endPage, pageSize, time);
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode("网关日志".concat(".xlsx"), "UTF-8");
        //设置内容类型
        response.setHeader("content-type", "application/octet-stream");
        //设置响应的编码格式
        response.setHeader("content-disposition", "attachment;filename=" + fileName);
        EasyExcel.write(response.getOutputStream(), LogInfo.class).sheet().doWrite(export);
    }


}
