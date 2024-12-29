package com.hao.tmusicmanagement.pojo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hao.tmusicmanagement.util.LogTypeConverter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@TableName("gateway_logs")
@NoArgsConstructor
@AllArgsConstructor
public class LogInfo {
    @ExcelProperty(value = "类型",converter = LogTypeConverter.class)
    private Integer type;
    @ExcelProperty("用户ID")
    private String userId;
    @ExcelProperty("访问路径")
    @ColumnWidth(20)
    private String path;
    @ExcelProperty("访问城市")
    private String city;
    @ExcelProperty("访问IP")
    private String ip;
    @ExcelProperty("访问时间")
    @ColumnWidth(30)
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    private LocalDateTime time;
}
