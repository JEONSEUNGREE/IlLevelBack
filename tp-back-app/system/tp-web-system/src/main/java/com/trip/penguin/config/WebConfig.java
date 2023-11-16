package com.trip.penguin.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;



@Configuration
public class WebConfig implements WebMvcConfigurer {

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

}
