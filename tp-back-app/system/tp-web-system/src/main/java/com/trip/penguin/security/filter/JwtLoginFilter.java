package com.trip.penguin.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trip.penguin.constant.CommonConstant;
import com.trip.penguin.jwt.CookieUtil;
import com.trip.penguin.jwt.JwtTokenUtil;
import com.trip.penguin.oauth.service.CustomUserDetailsService;
import com.trip.penguin.security.dto.LoginRequestDTO;
import com.trip.penguin.user.domain.UserMS;
import com.trip.penguin.user.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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

            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.getUserEmail(), loginDto.getUserPwd(), new ArrayList<>()));

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authentication) throws IOException {
        Map<String, String> payload = new HashMap<>();

        String userEmail = (String)authentication.getPrincipal();

        // 추가 정보 claim에 넣는 부분
        payload.put("userEmail", userEmail);
        response.addHeader(CommonConstant.ACCOUNT_TOKEN.getName(), jwtTokenUtil.generateToken(payload));
        response.sendRedirect(frontServer);
    }
}