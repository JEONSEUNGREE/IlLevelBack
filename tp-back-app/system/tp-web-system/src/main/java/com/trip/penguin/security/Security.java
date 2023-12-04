package com.trip.penguin.security;

import com.trip.penguin.jwt.JwtTokenUtil;
import com.trip.penguin.account.service.CustomCompanyDetailsService;
import com.trip.penguin.security.handler.CustomAuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.trip.penguin.constant.SecurityConstant;
import com.trip.penguin.jwt.CookieUtil;
import com.trip.penguin.security.filter.JwtAuthenticationFilter;
import com.trip.penguin.account.service.CustomOAuth2UserService;
import com.trip.penguin.account.service.CustomOidcUserService;
import com.trip.penguin.account.service.CustomUserDetailsService;
import com.trip.penguin.security.filter.JwtLoginFilter;
import com.trip.penguin.security.provider.JwtAuthenticationProvider;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class Security {

	private final CustomOAuth2UserService customOAuth2UserService;

	private final CustomOidcUserService customOidcUserService;

	private final CustomUserDetailsService customUserDetailsService;

	private final CustomCompanyDetailsService customCompanyDetailsService;

	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	@Value("${front.nodeServer}")
	private String frontServer;

	public JwtAuthenticationFilter jwtAuthenticationFilter(JwtTokenUtil jwtTokenUtil, CookieUtil cookieUtil) {
		return new JwtAuthenticationFilter(authenticationManager(), jwtTokenUtil, cookieUtil);
	}

	public JwtLoginFilter jwtLoginFilter(JwtTokenUtil jwtProvider, CustomUserDetailsService customUserDetailsService, String frontServer) {
		return new JwtLoginFilter(authenticationManager(), jwtProvider, frontServer);
	}

	@Bean
	public AuthenticationManager authenticationManager() {
		return new ProviderManager(new JwtAuthenticationProvider(customUserDetailsService, customCompanyDetailsService, bCryptPasswordEncoder));
	}

	/**
	 * static 파일 허용
	 */
	@Bean
	public WebSecurityCustomizer configure() {
		return (web) -> web.ignoring().requestMatchers(SecurityConstant.resourceArray);
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtTokenUtil jwtTokenUtil,
		CookieUtil cookieUtil, CustomUserDetailsService customUserDetailsService) throws Exception {

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
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.sessionFixation().none();

		http
			.formLogin().disable();

		http
			.oauth2Login(oauth2 -> oauth2
				.userInfoEndpoint(userInfoEndpointConfig -> userInfoEndpointConfig
					.userService(customOAuth2UserService)
					.oidcUserService(customOidcUserService))); // openid connect 방식 서비스 커스텀

		http
			.addFilterAt(jwtLoginFilter(jwtTokenUtil, customUserDetailsService, frontServer),
				UsernamePasswordAuthenticationFilter.class)
			.addFilterBefore(jwtAuthenticationFilter(jwtTokenUtil, cookieUtil),
				UsernamePasswordAuthenticationFilter.class);

		http
			.oauth2Login()
			.successHandler(new CustomAuthenticationSuccessHandler(jwtTokenUtil, frontServer));
		// .oauth2Login()
		// .successHandler()
		// .logout().logoutSuccessHandler(new SimpleUrlLogoutSuccessHandler()); // 로그아웃 원하는 작업시 커스텀

		http
			.exceptionHandling().authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login"));

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
