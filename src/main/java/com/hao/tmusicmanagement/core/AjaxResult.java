package com.hao.tmusicmanagement.core;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 统一返回集
 * @param <T>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AjaxResult<T> {

    private T data;

    private String msg;

    private String code;
    public AjaxResult(String msg,String code) {
        this.code =code;
        this.msg = msg;
    }

    public AjaxResult(String msg,String code,T data) {
        this.code =code;
        this.msg = msg;
        this.data = data;
    }
}