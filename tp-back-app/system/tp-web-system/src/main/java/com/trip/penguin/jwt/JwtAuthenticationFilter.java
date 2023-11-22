package com.trip.penguin.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenUtil jwtTokenUtil;

    private final CookieUtil cookieUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {

        String token = null;

        String account_token = "AccountToken";

        Cookie accountTokenCookie = cookieUtil.getCookie(request, "AccountToken");

        if (accountTokenCookie != null && account_token.equals(accountTokenCookie.getName())) {
            token = accountTokenCookie.getValue();
        }

        if(token != null && !jwtTokenUtil.isTokenExpired(token)) {
            try {
                String userId = jwtTokenUtil.getUserIdFromToken(token);
                SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(userId, "account_token"));
            } catch(Exception e) {
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