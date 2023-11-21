package com.trip.penguin.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.trip.penguin.constant.SecurityConstant;

@Configuration
public class Security {

	/**
	 * static 파일 허용
	 */
	@Bean
	public WebSecurityCustomizer configure() {
		return (web) -> web.ignoring().requestMatchers(SecurityConstant.resourceArray);
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http
			.authorizeHttpRequests()
			.requestMatchers("/**")
			.permitAll();

		http
			.csrf().disable();

		http
			.cors()
			.configurationSource(corsConfigurationSource());

		http
			.formLogin()
			.loginPage("/login")
			.loginProcessingUrl("/loginProc")
			.defaultSuccessUrl("/")
			.permitAll();

		return http.build();
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {

		CorsConfiguration corsConfiguration = new CorsConfiguration();
		corsConfiguration.addAllowedOrigin("*");
		corsConfiguration.addAllowedMethod("*");
		corsConfiguration.addAllowedHeader("*");
		//        corsConfiguration.setAllowCredentials(true);
		corsConfiguration.setMaxAge(3600L);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", corsConfiguration);

		return source;
	}
}
