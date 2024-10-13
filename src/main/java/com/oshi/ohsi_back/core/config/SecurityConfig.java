package com.oshi.ohsi_back.core.config;

import com.oshi.ohsi_back.domain.user.jwt.filter.ExceptionHandlerFilter;
import com.oshi.ohsi_back.domain.user.jwt.filter.JwtAuthenticationProcessingFilter;
import com.oshi.ohsi_back.domain.user.jwt.handler.CustomAccessDeniedHandler;
import com.oshi.ohsi_back.domain.user.jwt.entrypoint.CustomAuthenticationEntryPoint;
import com.oshi.ohsi_back.domain.user.infrastructure.UserRepository;
import com.oshi.ohsi_back.domain.user.jwt.service.JwtService;

import lombok.RequiredArgsConstructor;

import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final CacheManager cacheManager;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .httpBasic(AbstractHttpConfigurer::disable)
            .csrf(AbstractHttpConfigurer::disable)
            .formLogin(AbstractHttpConfigurer::disable)
            .headers(headersConfigure -> headersConfigure
                .frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
            .sessionManagement(httpSecuritySessionManagementConfigurer ->
                httpSecuritySessionManagementConfigurer.sessionCreationPolicy(
                    SessionCreationPolicy.STATELESS)) // 세션을 상태 없이 사용
                    .authorizeHttpRequests(authorize -> authorize
                    .requestMatchers(HttpMethod.POST, "/api/v1/user/register").permitAll() // 회원가입은 인증 없이 허용
                    .requestMatchers(HttpMethod.POST, "/api/v1/user/login").permitAll()    // 로그인은 인증 없이 허용
                    .requestMatchers(HttpMethod.GET, "/api/v1/public/**").permitAll()      // 다른 public 엔드포인트 허용
                    .anyRequest().authenticated())          
            .exceptionHandling(customizer -> customizer
                .authenticationEntryPoint(customAuthenticationEntryPoint()) // 인증 실패 시 처리
                .accessDeniedHandler(customAccessDeniedHandler())) // 권한 없음 처리
            .addFilterAfter(jwtAuthenticationProcessFilter(), LogoutFilter.class) // JWT 필터 추가
            .addFilterBefore(exceptionHandlerFilter(), JwtAuthenticationProcessingFilter.class); // 예외 처리 필터 추가

        return http.build();
    }

    @Bean
    public JwtAuthenticationProcessingFilter jwtAuthenticationProcessFilter() {
        return new JwtAuthenticationProcessingFilter(jwtService, cacheManager, userRepository);
    }

    @Bean
    public ExceptionHandlerFilter exceptionHandlerFilter() {
        return new ExceptionHandlerFilter();
    }

    @Bean
    public CustomAuthenticationEntryPoint customAuthenticationEntryPoint() {
        return new CustomAuthenticationEntryPoint();
    }

    @Bean
    public CustomAccessDeniedHandler customAccessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  // BCryptPasswordEncoder를 빈으로 등록
    }
}
