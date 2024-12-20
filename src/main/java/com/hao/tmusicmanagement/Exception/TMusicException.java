package com.hao.tmusicmanagement.Exception;

public class TMusicException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    private Integer code;

    // 构造方法
    public TMusicException(String message, Integer code) {
        super(message);
        this.code = code;
    }

    // 无参构造方法
    public TMusicException() {
        super();
    }

    // getter 和 setter
    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}

