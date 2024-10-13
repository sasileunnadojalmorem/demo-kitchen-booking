package com.oshi.ohsi_back.domain.user.jwt.filter;

import com.oshi.ohsi_back.core.properties.ErrorCode;
import com.oshi.ohsi_back.domain.user.domain.entitiy.User;
import com.oshi.ohsi_back.domain.user.execption.UserException;
import com.oshi.ohsi_back.domain.user.infrastructure.UserRepository;
import com.oshi.ohsi_back.domain.user.jwt.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@RequiredArgsConstructor
public class JwtAuthenticationProcessingFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final CacheManager cacheManager;
    private final UserRepository userRepository;

    private GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        checkLogout(request); // 로그아웃한 사용자면 인증 처리 안함

        jwtService.extractAccessToken(request)
                .ifPresent(accessToken -> {
                    if (!jwtService.isTokenValid(accessToken)) { // Access Token이 만료되었을 때
                        throw new UserException(ErrorCode.SECURITY_INVALID_TOKEN);
                    }
                });
        checkAccessTokenAndSaveAuthentication(request, response, filterChain);
    }

    // 로그아웃된 사용자인지 확인
    private void checkLogout(HttpServletRequest request) {
        jwtService.extractAccessToken(request).ifPresent(accessToken -> {
            Optional<Object> cacheValue = Optional.ofNullable(cacheManager.getCache("sessionCache").get(accessToken, Object.class));
            if (cacheValue.isPresent() && "logout".equals(cacheValue.get())) {
                throw new UserException(ErrorCode.SECURITY_UNAUTHORIZED);
            }
        });
    }

    // Access Token을 검증하고, 인증 정보를 SecurityContext에 저장
    private void checkAccessTokenAndSaveAuthentication(HttpServletRequest request,
                                                       HttpServletResponse response, FilterChain filterChain) {
        jwtService.extractAccessToken(request)
                .flatMap(jwtService::extractEmail)
                .flatMap(userRepository::findByEmail).ifPresent(this::saveAuthentication);

        try {
            filterChain.doFilter(request, response);
        } catch (IOException | ServletException e) {
            throw new UserException(ErrorCode.DATABASE_ERROR);
        }
    }

    // 인증 정보를 SecurityContext에 저장
    private void saveAuthentication(User myUser) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(myUser, null,
                authoritiesMapper.mapAuthorities(myUser.getAuthorities()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
