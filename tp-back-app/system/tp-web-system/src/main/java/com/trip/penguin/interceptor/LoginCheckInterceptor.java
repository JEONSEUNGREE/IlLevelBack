package com.trip.penguin.interceptor;

import com.trip.penguin.constant.CommonUserRole;
import com.trip.penguin.exception.NotDefaultUserException;
import com.trip.penguin.exception.UserNotFoundException;
import com.trip.penguin.response.JsonResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.trip.penguin.interceptor.annotation.LoginUserCheck;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class LoginCheckInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

		if (!(handler instanceof HandlerMethod)) {
			return true;
		}
		// 어노테이션 타입 확인
		HandlerMethod handlerMethod = (HandlerMethod)handler;

		/* 로그인 여부 */
		LoginUserCheck loginUserCheck = handlerMethod.getMethodAnnotation(LoginUserCheck.class);

		if (loginUserCheck != null
				&& SecurityContextHolder.getContext()
				.getAuthentication()
				.getPrincipal()
				.equals("anonymousUser")
				&& !CommonUserRole.ROLE_USER.getUserRole().equals(
				SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
						.findFirst().orElseThrow(UserNotFoundException::new).getAuthority().toUpperCase())) {
			throw new RuntimeException("LOGIN PLEASE");
		}


		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
		ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
		Exception ex) throws Exception {
	}

}