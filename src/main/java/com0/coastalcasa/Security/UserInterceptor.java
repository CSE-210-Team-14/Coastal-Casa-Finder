package com0.coastalcasa.Security;

import com0.coastalcasa.Utils.JWTUtil;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserInterceptor implements HandlerInterceptor {

    private String TOKEN = "token";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String token = request.getHeader(TOKEN);

        if (token == null || token.equals("")) {
            throw new Exception("Error");
        };

        //verify token
        boolean sub = JWTUtil.verity(token);
        if (!sub) {
            throw new Exception("Error");
        }
        return true;
    }
}
