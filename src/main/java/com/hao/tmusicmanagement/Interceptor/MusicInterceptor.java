package com.hao.tmusicmanagement.Interceptor;

import cn.dev33.satoken.stp.StpUtil;
import com.hao.tmusicmanagement.Exception.UnauthorizedException;
import com.hao.tmusicmanagement.dao.LogDao;
import com.hao.tmusicmanagement.pojo.LogInfo;
import com.hao.tmusicmanagement.util.IpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.Enumeration;


@Component
public class MusicInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private LogDao logDao;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(request.getMethod().equals("OPTIONS")){
            return true;
        }
        String authorization = request.getHeader("Authorization");
        Object loginIdByToken = StpUtil.getLoginIdByToken(authorization);
        if(loginIdByToken != null){
            //当前用户已登录可以记录日志
            LogInfo logInfo = new LogInfo();
            String pathInfo = request.getServletPath();
            logInfo.setPath(pathInfo);
            String remoteAddr = request.getRemoteAddr();
            logInfo.setIp(remoteAddr);
            logInfo.setUserId(loginIdByToken.toString());
            logInfo.setType(0);
            logInfo.setTime(LocalDateTime.now());
            logInfo.setCity(IpUtil.getIPDetails(remoteAddr));
            logDao.insert(logInfo);
            return true;
        }
        throw new UnauthorizedException("请先登录");
    }


}
