package com.linkmoa.source.auth.oauth2.handler;

import java.io.IOException;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import com.linkmoa.source.auth.jwt.provider.JwtCookieManager;
import com.linkmoa.source.auth.jwt.refresh.service.RefreshTokenService;
import com.linkmoa.source.auth.jwt.service.JwtService;
import com.linkmoa.source.auth.oauth2.principal.PrincipalDetails;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomOauth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
	//SimpleUrlAuthenticationSuccessHandler는 인증 성공 후 처리를 담당하는 핸들러

	private final JwtService jwtService;
	private final RefreshTokenService refreshTokenService;
	private final JwtCookieManager jwtCookieManager;
	@Value("${frontend.base-url}")
	private String frontendBaseUrl;

	/*	@Override
		public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
			PrincipalDetails oauth2User = (PrincipalDetails)authentication.getPrincipal();
			Collection<? extends GrantedAuthority> authorities = oauth2User.getAuthorities();

			String email = oauth2User.getEmail();
			String role = authorities.iterator().next().getAuthority();

			String refreshToken = jwtService.createRefreshToken();
			refreshTokenService.saveRefreshToken(refreshToken, email);
			response.addCookie(jwtService.createRefreshCookie(refreshToken));

			response.sendRedirect(frontendBaseUrl + "/reissue");

			// 테스트용으로 추가한 부분
			String accessToken = jwtService.createAccessToken(email, role);
			response.addCookie(jwtCookieManager.createCookie("refresh_token", refreshToken, 14 * 24 * 60 * 60));
			response.setHeader("Authorization", "Bearer " + accessToken);

			log.info("OAuth2 로그인에 성공 하였습니다. access Token : {}", accessToken);
			log.info("OAuth2 로그인에 성공하였습니다. 이메일 : {}", oauth2User.getEmail());
			log.info("OAuth2 로그인에 성공하였습니다. Refresh Token : {}", refreshToken);

		}*/
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
		Authentication authentication) throws IOException, ServletException {
		Object principal = authentication.getPrincipal();

		String email;
		Collection<? extends GrantedAuthority> authorities;

		if (principal instanceof OidcUser oidcUser) {
			email = oidcUser.getEmail();
			authorities = oidcUser.getAuthorities();
		} else if (principal instanceof PrincipalDetails principalDetails) {
			email = principalDetails.getEmail();
			authorities = principalDetails.getAuthorities();
		} else {
			throw new IllegalStateException("OAuth2 로그인 실패: 예상치 못한 principal 타입 " + principal.getClass().getName());
		}

		String role = authorities.iterator().next().getAuthority();

		String refreshToken = jwtService.createRefreshToken();
		refreshTokenService.saveRefreshToken(refreshToken, email);
		response.addCookie(jwtService.createRefreshCookie(refreshToken));

		response.sendRedirect(frontendBaseUrl + "/reissue");

		// 테스트용으로 추가한 부분
		String accessToken = jwtService.createAccessToken(email, role);
		response.addCookie(jwtCookieManager.createCookie("refresh_token", refreshToken, 14 * 24 * 60 * 60));
		response.setHeader("Authorization", "Bearer " + accessToken);

		log.info("OAuth2 로그인 성공! AccessToken: {}", accessToken);
		log.info("OAuth2 로그인 성공! 이메일: {}", email);
		log.info("OAuth2 로그인 성공! RefreshToken: {}", refreshToken);
	}

}
