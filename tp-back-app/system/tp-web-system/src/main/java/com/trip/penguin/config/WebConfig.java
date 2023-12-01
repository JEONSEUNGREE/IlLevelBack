package com.trip.penguin.config;

import com.trip.penguin.interceptor.CompanyCheckInterceptor;
import com.trip.penguin.resolver.CurrentCompanyArgResolver;
import com.trip.penguin.resolver.CurrentUserArgResolver;
import com.trip.penguin.interceptor.LoginCheckInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {


	@Autowired
	private LoginCheckInterceptor loginCheckInterceptor;

	@Autowired
	private CurrentUserArgResolver currentUserArgResolver;

	@Autowired
	private CompanyCheckInterceptor companyCheckInterceptor;

	@Autowired
	private CurrentCompanyArgResolver currentCompanyArgResolver;

	@Value("${file.request.room-path}")
	private String roomResourcePath;

	@Value("${file.resource.room-path}")
	private String roomStaticPath;

	@Value("${file.request.user-path}")
	private String userResourcePath;

	@Value("${file.resource.user-path}")
	private String userStaticPath;

	@Value("${file.request.company-path}")
	private String companyResourcePath;

	@Value("${file.resource.company-path}")
	private String companyStaticPath;

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		/**
		 * 방 관련 static 데이
		 */
		// 다음 요청에 대한 매핑 /pImage/**
		registry.addResourceHandler(roomStaticPath)
			// 위 요청에 대한 서버 정적파일 위치
			.addResourceLocations(roomResourcePath)
			.setCachePeriod(20);

		/**
		 * 유저 관련 static 데이터
		 */
		registry.addResourceHandler(userStaticPath)
			.addResourceLocations(userResourcePath)
			.setCachePeriod(20);

		/**
		 * 회사 관련 static 데이터
		 */
		registry.addResourceHandler(companyStaticPath)
			.addResourceLocations(companyResourcePath)
			.setCachePeriod(20);
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(loginCheckInterceptor);
		registry.addInterceptor(companyCheckInterceptor);
	}

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(currentCompanyArgResolver);
		resolvers.add(currentUserArgResolver);
	}

}
