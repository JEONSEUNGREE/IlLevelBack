package com.trip.penguin.config.querydsl;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

// 통합 테스트용으로 설정 했었는데 추후에 에러시 다시 한번 확인
@Configuration
//@EnableJpaAuditing // JPA Auditing 활성화
//@EntityScan(basePackages = "com.trip.penguin")
//@EnableJpaRepositories(basePackages = "com.trip.penguin")
public class JpaConfig {

}
