package com.trip.penguin.config;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import com.trip.penguin.TpRdbDomainTest;

@Retention(RetentionPolicy.RUNTIME)
@ActiveProfiles("test")
@ContextConfiguration(classes = TpRdbDomainTest.class)
@EnableJpaRepositories(basePackages = {"com.trip.penguin"})
@EntityScan(basePackages = "com.trip.penguin")
@ComponentScan(value = "com.trip.penguin")
public @interface JpaDataConfig {
}
