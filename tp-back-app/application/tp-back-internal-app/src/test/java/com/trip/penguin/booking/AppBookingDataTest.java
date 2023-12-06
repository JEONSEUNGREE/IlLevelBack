package com.trip.penguin.booking;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import com.trip.penguin.TpBackInternalApp;
import com.trip.penguin.config.TestContainer;
import com.trip.penguin.user.controller.UserMyPageController;
import com.trip.penguin.user.service.UserMyPageService;

@ActiveProfiles("test")
@ContextConfiguration(classes = {TpBackInternalApp.class})
@DataJpaTest(properties = "classpath:application.yaml")
@ComponentScan(basePackages = {
	"com.trip.penguin.user",
	"com.trip.penguin.room",
	"com.trip.penguin.booking",
	"com.trip.penguin.company.service",
	"com.trip.penguin.company.repository",
	"com.trip.penguin.util",
}, excludeFilters = @ComponentScan.Filter(
	type = FilterType.ASSIGNABLE_TYPE,
	classes = {UserMyPageController.class, UserMyPageService.class}))
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@Import(TestContainer.class)
public class AppBookingDataTest {
}
