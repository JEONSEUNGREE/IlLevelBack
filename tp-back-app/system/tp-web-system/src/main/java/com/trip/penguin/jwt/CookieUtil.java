package com.trip.penguin.jwt;

import org.springframework.stereotype.Component;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class CookieUtil {
	public Cookie getCookie(HttpServletRequest req, String name) {

		if (req.getCookies() == null) {
			return null;
		}

		for (Cookie cookie : req.getCookies()) {
			if (name.equals(cookie.getName())) {
				return cookie;
			}
		}

		return null;
	}
}
