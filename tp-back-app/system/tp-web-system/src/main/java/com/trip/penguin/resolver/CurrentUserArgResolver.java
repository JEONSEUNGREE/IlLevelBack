package com.trip.penguin.resolver;

import com.trip.penguin.constant.CommonConstant;
import com.trip.penguin.resolver.annotation.CurrentUser;
import com.trip.penguin.resolver.vo.LoginInfo;
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
public class CurrentUserArgResolver implements HandlerMethodArgumentResolver {

    // 어노테이션 존재 여부 확인
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(CurrentUser.class);
    }

    // 실제 바인딩할 객체를 반환한다.
    @Override
    public Object resolveArgument(@NonNull MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  @NonNull NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) {
        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();

        return LoginInfo.builder()
                .userId(loggedInUser.getName())
                .jwtToken(webRequest.getHeader(CommonConstant.ACCOUNT_TOKEN.getName()))
                .build();
    }
}