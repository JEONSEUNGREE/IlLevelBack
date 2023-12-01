package com.trip.penguin.interceptor;

import com.trip.penguin.constant.CommonUserRole;
import com.trip.penguin.exception.NotCompanyUserException;
import com.trip.penguin.interceptor.annotation.LoginCompanyCheck;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class CompanyCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        /* 회사 여부 */
        LoginCompanyCheck loginCompanyCheck = ((HandlerMethod) handler).getMethodAnnotation(LoginCompanyCheck.class);

        if (loginCompanyCheck != null
                && SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal()
                .equals("anonymousUser")
                && !SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal()
                .equals(CommonUserRole.ROLE_COM.getUserRole())) {
            throw new NotCompanyUserException();
        }

        return true;
    }
}
