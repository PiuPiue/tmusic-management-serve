package com.hao.tmusicmanagement.Exception;

public class UnauthorizedException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    private Integer code;

    public UnauthorizedException(String message) {
        super(message);
        this.code = 401;
    }
}