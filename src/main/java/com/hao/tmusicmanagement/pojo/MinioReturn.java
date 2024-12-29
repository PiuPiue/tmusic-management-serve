package com.hao.tmusicmanagement.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MinioReturn {
 
    /**
     * 文件地址
     */
    private String path;
 
    /**
     * 原始文件名
     */
    private String inputName;
 
    /**
     * 最终文件名
     */
    private String outPutName;

}