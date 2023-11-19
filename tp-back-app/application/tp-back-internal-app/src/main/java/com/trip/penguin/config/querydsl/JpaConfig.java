package com.trip.penguin.config.querydsl;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaAuditing // JPA Auditing 활성화
@EntityScan(basePackages = "com.trip.penguin")
@EnableJpaRepositories(basePackages = "com.trip.penguin")
public class JpaConfig {

}
