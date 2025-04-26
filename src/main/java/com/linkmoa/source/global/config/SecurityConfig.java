package com.linkmoa.source.global.config;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.linkmoa.source.auth.jwt.filter.JwtAuthorizationFilter;
import com.linkmoa.source.auth.oauth2.handler.CustomOauth2SuccessHandler;
import com.linkmoa.source.auth.oauth2.service.CustomOauth2UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Slf4j
public class SecurityConfig {

	private final CustomOauth2UserService customOauth2UserService;
	private final CustomOauth2SuccessHandler customOauth2SuccessHandler;
	private final JwtAuthorizationFilter jwtAuthorizationFilter;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		return
			http
				.cors(corsCustomizer -> corsCustomizer.configurationSource(corsConfigurationSource()))
				.csrf(AbstractHttpConfigurer::disable)
				.formLogin(AbstractHttpConfigurer::disable)
				.httpBasic(AbstractHttpConfigurer::disable)
				.authorizeHttpRequests((auth) -> auth
					.requestMatchers(new AntPathRequestMatcher("/error")).permitAll()
					.requestMatchers(new AntPathRequestMatcher("/login")).permitAll()
					.requestMatchers(new AntPathRequestMatcher("/api/members/**")).permitAll()
					.requestMatchers(new AntPathRequestMatcher("/v3/**")).permitAll()
					.requestMatchers(new AntPathRequestMatcher("/api/jwt/**")).permitAll()
					.requestMatchers(new AntPathRequestMatcher("/swagger-ui/**")).permitAll()
					.requestMatchers(new AntPathRequestMatcher("/oauth2/**")).permitAll() // OAuth 관련 경로 허용
					.requestMatchers(new AntPathRequestMatcher("/actuator/prometheus")).permitAll()
					.requestMatchers(new AntPathRequestMatcher("/actuator/health")).permitAll()
					.anyRequest().authenticated()
				)
				.oauth2Login((oauth2) -> oauth2
					.userInfoEndpoint(
						userInfoEndpointConfig -> userInfoEndpointConfig.userService(customOauth2UserService))
					.successHandler(customOauth2SuccessHandler)
					.failureHandler((request, response, exception) -> {
						log.error("🔴 OAuth2 로그인 실패: {}", exception.getMessage(), exception);
						response.sendRedirect("/login?error");
					}))
				//.addFilterAfter(customJsonUserPasswordAuthenticationFilter(), LogoutFilter.class)
				.addFilterBefore(jwtAuthorizationFilter, LogoutFilter.class)
				//.addFilterBefore(jwtExceptionHandlerFilter, JwtAuthorizationFilter.class)
				.sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
				.build();
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowedOriginPatterns(Collections.singletonList("*")); // 모든 origin 허용
		config.setAllowedMethods(Collections.singletonList("*"));        // 모든 HTTP 메서드 허용
		config.setAllowedHeaders(Collections.singletonList("*"));        // 모든 헤더 허용
		config.setExposedHeaders(Collections.singletonList("Authorization")); // Authorization 헤더 노출
		config.setAllowCredentials(
			true);                                 // 자격증명 허용 (주의: allowedOriginPatterns가 "*"일 경우 보안상 위험)
		config.setMaxAge(3600L); // 캐싱 시간 설정

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);

		return source;
	}
}
