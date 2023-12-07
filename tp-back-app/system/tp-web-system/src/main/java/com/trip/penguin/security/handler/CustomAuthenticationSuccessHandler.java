package com.trip.penguin.security.handler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import com.trip.penguin.account.model.PrincipalUser;
import com.trip.penguin.constant.CommonConstant;
import com.trip.penguin.constant.CommonUserRole;
import com.trip.penguin.jwt.JwtTokenUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	private final JwtTokenUtil jwtTokenUtil;

	private final String frontServer;

	public CustomAuthenticationSuccessHandler(JwtTokenUtil jwtTokenUtil, String frontServer) {
		this.jwtTokenUtil = jwtTokenUtil;
		this.frontServer = frontServer;
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
		Authentication authentication) throws IOException {

		Map<String, String> payload = new HashMap<>();

		String userEmail = (String)((PrincipalUser)((OAuth2AuthenticationToken)authentication).getPrincipal()).getAttributes()
			.get("email");

		payload.put("userEmail", userEmail);
		payload.put("authority", CommonUserRole.ROLE_USER.getUserRole());

		response.sendRedirect(
			frontServer + "/login?" + CommonConstant.ACCOUNT_TOKEN.getName() + "=" + jwtTokenUtil.generateToken(
				payload));
	}
}
