package com.trip.penguin.security.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import net.minidev.json.JSONObject;

import com.trip.penguin.constant.CommonConstant;
import com.trip.penguin.jwt.CookieUtil;
import com.trip.penguin.jwt.JwtTokenUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final AuthenticationManager authenticationManager;

	private final JwtTokenUtil jwtTokenUtil;

	private final CookieUtil cookieUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws
		ServletException,
		IOException {

		String token = null;

		Cookie accountTokenCookie = cookieUtil.getCookie(request, CommonConstant.ACCOUNT_TOKEN.getName());

		if (accountTokenCookie != null && CommonConstant.ACCOUNT_TOKEN.getName().equals(accountTokenCookie.getName())) {
			token = accountTokenCookie.getValue();
		}

		if (token != null && !jwtTokenUtil.isTokenExpired(token)) {
			try {
				// 로그인 하면 인증된 것이기 때문에 재 인증은 필요가 없다.
				Authentication authenticate = new UsernamePasswordAuthenticationToken(jwtTokenUtil.getUserEmailFromToken(token),
						CommonConstant.ACCOUNT_TOKEN.getName(), List.of(new SimpleGrantedAuthority(jwtTokenUtil.getUserAuthority(token))));
				SecurityContextHolder.getContext().setAuthentication(authenticate);
			} catch (Exception e) {
				onError(request, response);
			}
		}

		chain.doFilter(request, response);
	}

	private void onError(HttpServletRequest req, HttpServletResponse res) throws IOException {
		res.setStatus(HttpServletResponse.SC_FORBIDDEN);
		res.setContentType("application/json");
		res.setCharacterEncoding("UTF-8");

		JSONObject resJson = new JSONObject();
		resJson.put("code", HttpStatus.FORBIDDEN);
		resJson.put("message", "LOGIN PLEASE");

		res.getWriter().write(resJson.toString());
	}
}