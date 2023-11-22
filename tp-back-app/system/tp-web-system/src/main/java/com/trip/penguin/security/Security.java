package com.trip.penguin.security;

import com.trip.penguin.jwt.CookieUtil;
import com.trip.penguin.jwt.JwtAuthenticationFilter;
import com.trip.penguin.jwt.JwtTokenUtil;
import com.trip.penguin.oauth.service.CustomOAuth2UserService;
import com.trip.penguin.oauth.service.CustomOidcUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.trip.penguin.constant.SecurityConstant;

@Configuration
@RequiredArgsConstructor
public class Security {

	private final CustomOAuth2UserService customOAuth2UserService;

	private final CustomOidcUserService customOidcUserService;

	private final CookieUtil cookieUtil;

	private JwtAuthenticationFilter jwtAuthenticationFilter(JwtTokenUtil jwtTokenUtil, CookieUtil cookieUtil) {
		return new JwtAuthenticationFilter(jwtTokenUtil, cookieUtil);
	}

	/**
	 * static 파일 허용
	 */
	@Bean
	public WebSecurityCustomizer configure() {
		return (web) -> web.ignoring().requestMatchers(SecurityConstant.resourceArray);
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtTokenUtil jwtTokenUtil) throws Exception {

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
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);


		http
//				.formLogin().loginPage("/login").loginProcessingUrl("/loginProc").defaultSuccessUrl("/").permitAll();
				.formLogin().disable()
				.addFilterBefore(new JwtAuthenticationFilter(jwtTokenUtil, cookieUtil), UsernamePasswordAuthenticationFilter.class);


		http
				.oauth2Login(oauth2 -> oauth2
						.userInfoEndpoint(userInfoEndpointConfig -> userInfoEndpointConfig
								.userService(customOAuth2UserService) // 기본적인 authorization_code 방식 서비스 커스텀
								.oidcUserService(customOidcUserService))); // openid connect 방식 서비스 커스텀

		http
				.logout().logoutSuccessHandler(new SimpleUrlLogoutSuccessHandler()); // 로그아웃 원하는 작업시 커스텀

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

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
