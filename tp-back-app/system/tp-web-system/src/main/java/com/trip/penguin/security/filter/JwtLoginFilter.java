package com.trip.penguin.security.filter;

import static com.trip.penguin.constant.CommonUserRole.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import net.minidev.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trip.penguin.account.dto.LoginRequestDTO;
import com.trip.penguin.constant.CommonConstant;
import com.trip.penguin.constant.CommonUserRole;
import com.trip.penguin.exception.UserNotFoundException;
import com.trip.penguin.jwt.JwtTokenUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtLoginFilter extends UsernamePasswordAuthenticationFilter {
	private final JwtTokenUtil jwtTokenUtil;

	private final String frontServer;

	public JwtLoginFilter(AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil, String frontServer) {
		super(authenticationManager);
		this.jwtTokenUtil = jwtTokenUtil;
		this.frontServer = frontServer;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
		throws AuthenticationException {
		try {
			LoginRequestDTO loginDto = new ObjectMapper().readValue(request.getInputStream(), LoginRequestDTO.class);

			Authentication authentication = null;

			switch (CommonUserRole.valueOf(loginDto.getType().toUpperCase())) {
				case ROLE_USER -> authentication = getAuthenticationManager().authenticate(
					new UsernamePasswordAuthenticationToken(loginDto.getUserEmail(), loginDto.getUserPwd(),
						List.of(new SimpleGrantedAuthority(ROLE_USER.getUserRole()))));

				case ROLE_COM -> authentication = getAuthenticationManager().authenticate(
					new UsernamePasswordAuthenticationToken(loginDto.getUserEmail(), loginDto.getUserPwd(),
						List.of(new SimpleGrantedAuthority(ROLE_COM.getUserRole()))));

				case ROLE_ADMIN -> authentication = getAuthenticationManager().authenticate(
					new UsernamePasswordAuthenticationToken(loginDto.getUserEmail(), loginDto.getUserPwd(),
						List.of(new SimpleGrantedAuthority(ROLE_ADMIN.getUserRole()))));

				default -> throw new UserNotFoundException();
			}

			return authentication;

		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
		FilterChain chain, Authentication authentication) throws IOException {
		Map<String, String> payload = new HashMap<>();

		String userEmail = (String)authentication.getPrincipal();
		String authority = authentication.getAuthorities()
			.stream()
			.findFirst()
			.orElseThrow(UserNotFoundException::new)
			.getAuthority();

		// 추가 정보 claim에 넣는 부분
		payload.put("userEmail", userEmail);
		payload.put("authority", authority);

		JSONObject resJson = new JSONObject();
		resJson.put(CommonConstant.ACCOUNT_TOKEN.getName(), jwtTokenUtil.generateToken(payload));

		response.getWriter().write(resJson.toJSONString());
	}
}