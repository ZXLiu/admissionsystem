package org.enroll.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Component
public class LoginInterceptor implements HandlerInterceptor {//定义拦截器


    @Resource(name = "globalStorage")
    Map<String, Object> storage;

    //在业务处理器处理请求之前被调用
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        if(request.getSession().equals(storage.get("authSession")))
//            return true;
//        response.setCharacterEncoding("UTF-8");
//        response.setContentType("application/json");
//        response.getWriter().println("{\"code\":\"010\",\"data\":null,\"message\":\"未登录\"}");
//        return false;
        return true;
    }
}
