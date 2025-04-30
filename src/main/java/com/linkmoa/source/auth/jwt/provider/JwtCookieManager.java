package com.linkmoa.source.auth.jwt.provider;

import java.time.Duration;

import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import com.linkmoa.source.global.exception.CookieNotFoundException;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtCookieManager {

	public Cookie createCookie(String key, String value, int maxAge) {
		Cookie cookie = new Cookie(key, value);
		cookie.setMaxAge(maxAge);
		cookie.setPath("/");
		cookie.setHttpOnly(true);
		cookie.setSecure(true);
		return cookie;
	}

	/**
	 * SameSite = None
	 * Secure 설정 쿠키
	 * @param response
	 * @param value
	 * @param maxAge
	 */
	public void addRefreshTokenCookie(HttpServletResponse response, String value, int maxAge) {
		ResponseCookie cookie = ResponseCookie.from("refresh_token", value)
			.httpOnly(true)
			.secure(true)
			.path("/")
			.sameSite("None")
			.maxAge(Duration.ofSeconds(maxAge))
			.build();

		response.addHeader("Set-Cookie", cookie.toString());
	}

	public String getRefreshTokenFromCookies(Cookie[] cookies) {
		if (cookies == null) {
			throw new CookieNotFoundException("쿠키가 존재하지 않습니다.");
		}

		for (Cookie cookie : cookies) {
			if ("refresh_token".equals(cookie.getName())) {
				return cookie.getValue();
			}
		}

		throw new CookieNotFoundException("리프레시 토큰 쿠키가 없습니다.");
	}
}