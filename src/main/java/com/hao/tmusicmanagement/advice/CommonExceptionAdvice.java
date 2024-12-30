package com.hao.tmusicmanagement.advice;




import com.hao.tmusicmanagement.Exception.TMusicException;
import com.hao.tmusicmanagement.Exception.UnauthorizedException;
import com.hao.tmusicmanagement.core.AjaxResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
@Slf4j
public class CommonExceptionAdvice {
    /*
      全局异常处理，没有指定异常的类型，不管什么异常均可以捕获
       */
    @ExceptionHandler(UnauthorizedException.class)
    /*如果不加，则会导致无法进行异常处理*/
    @ResponseBody
    public AjaxResult error(UnauthorizedException e){
        e.printStackTrace();
        return new AjaxResult(e.getMessage(),"401");
    }

    @ExceptionHandler(TMusicException.class)
    /*如果不加，则会导致无法进行异常处理*/
    @ResponseBody
    public AjaxResult error(TMusicException e) {
        e.printStackTrace();
        return new AjaxResult(e.getMessage(),"500");
    }
}