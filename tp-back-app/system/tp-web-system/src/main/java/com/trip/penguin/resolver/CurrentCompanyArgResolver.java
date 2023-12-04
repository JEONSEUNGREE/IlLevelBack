package com.trip.penguin.resolver;

import com.trip.penguin.constant.CommonConstant;
import com.trip.penguin.resolver.annotation.CurrentCompany;
import com.trip.penguin.resolver.annotation.CurrentUser;
import com.trip.penguin.resolver.vo.LoginCompanyInfo;
import com.trip.penguin.resolver.vo.LoginUserInfo;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@RequiredArgsConstructor
public class CurrentCompanyArgResolver implements HandlerMethodArgumentResolver {

	// 어노테이션 존재 여부 확인
	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.hasParameterAnnotation(CurrentCompany.class);
	}

	// 실제 바인딩할 객체를 반환한다.
	@Override
	public Object resolveArgument(@NonNull MethodParameter parameter,
		ModelAndViewContainer mavContainer,
		@NonNull NativeWebRequest webRequest,
		WebDataBinderFactory binderFactory) {

		Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();

		return LoginCompanyInfo.builder()
				.comEmail(loggedInUser.getName())
				.role(loggedInUser.getAuthorities().stream().findFirst().orElseThrow().getAuthority())
				.build();
	}
}