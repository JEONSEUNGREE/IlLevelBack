package com.trip.penguin.jwt;

import java.io.IOException;

import org.springframework.web.filter.OncePerRequestFilter;

import net.minidev.json.JSONObject;

import com.trip.penguin.security.filter.JwtTokenUtil;

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

	private final JwtTokenUtil jwtTokenUtil;

	private final CookieUtil cookieUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws
		ServletException,
		IOException {

		String token = null;

		System.out.println("호출 횟수 체크중 ");
		String account_token = "AccountToken";

		Cookie accountTokenCookie = cookieUtil.getCookie(request, "AccountToken");

		if (accountTokenCookie != null && account_token.equals(accountTokenCookie.getName())) {
			token = accountTokenCookie.getValue();
		}

		if (token != null && !jwtTokenUtil.isTokenExpired(token)) {
			try {
				// Authentication authenticate = authenticationManager.
				// 	authenticate(new UsernamePasswordAuthenticationToken(jwtTokenUtil.getUserIdFromToken(token),
				// 		"account_token"));
				// SecurityContextHolder.getContext().setAuthentication(authenticate);
			} catch (Exception e) {
				onError(request, response);
			}
		}

		chain.doFilter(request, response);
	}

	private void onError(HttpServletRequest req, HttpServletResponse res) throws IOException {
		res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		res.setContentType("application/json");
		res.setCharacterEncoding("UTF-8");

		JSONObject resJson = new JSONObject();
		resJson.put("code", 401);
		resJson.put("message", "LOGIN PLEASE");

		res.getWriter().write(resJson.toString());
	}
}