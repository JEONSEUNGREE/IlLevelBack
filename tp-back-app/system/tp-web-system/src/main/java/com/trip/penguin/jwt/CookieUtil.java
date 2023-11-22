package com.trip.penguin.jwt;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class CookieUtil {
    public Cookie getCookie(HttpServletRequest req, String name) {

        return Arrays.stream(req.getCookies()).filter(item -> item.getName().equals(name)).findFirst().get();
    }
}
