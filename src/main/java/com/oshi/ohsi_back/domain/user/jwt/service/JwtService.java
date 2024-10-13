package com.oshi.ohsi_back.domain.user.jwt.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import java.util.Date;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.oshi.ohsi_back.core.properties.ErrorCode;
import com.oshi.ohsi_back.domain.user.execption.UserException;
import com.oshi.ohsi_back.domain.user.infrastructure.UserRepository;

@Service
@RequiredArgsConstructor
public class JwtService {

    private static final String ACCESS_TOKEN_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";
    private static final long ACCESS_TOKEN_EXPIRATION_TIME = 1000 * 60 * 30; // 30분
    private static final long REFRESH_TOKEN_EXPIRATION_TIME = 1000 * 60 * 60 * 24 * 7; // 7일
    private static final String EMAIL_CLAIM = "email";
    private static final String ACCESS_TOKEN_SUBJECT = "AccessToken";
    private final String SECRET_KEY = "YourSecretKey"; // 실제 프로젝트에서는 환경 변수로 관리하는 것을 추천
    
    private final UserRepository userRepository;
    private final CacheManager cacheManager;

    // Access Token 생성
    public String createAccessToken(String email) {
        Date now = new Date();
        return JWT.create()
            .withSubject(ACCESS_TOKEN_SUBJECT)
            .withExpiresAt(new Date(now.getTime() + ACCESS_TOKEN_EXPIRATION_TIME))
            .withClaim(EMAIL_CLAIM, email)
            .sign(Algorithm.HMAC512(SECRET_KEY));
    }

    // Refresh Token 생성
    public String createRefreshToken() {
        Date now = new Date();
        return JWT.create()
            .withExpiresAt(new Date(now.getTime() + REFRESH_TOKEN_EXPIRATION_TIME))
            .sign(Algorithm.HMAC512(SECRET_KEY));
    }

    // Access Token에서 이메일 추출
    public Optional<String> extractEmail(String accessToken) {
        try {
            DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(SECRET_KEY))
                .build()
                .verify(accessToken);
            return Optional.ofNullable(decodedJWT.getClaim(EMAIL_CLAIM).asString());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    // Access Token 추출
    public Optional<String> extractAccessToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(ACCESS_TOKEN_HEADER);
        if (bearerToken != null && bearerToken.startsWith(BEARER_PREFIX)) {
            return Optional.of(bearerToken.substring(BEARER_PREFIX.length()));
        }
        return Optional.empty();
    }

    // Access Token 유효성 검사
    public boolean isTokenValid(String token) {
        try {
            JWT.require(Algorithm.HMAC512(SECRET_KEY)).build().verify(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // 세션 정보 캐시에 저장
    @Cacheable(value = "sessionCache", key = "#refreshToken")
    public void updateSessionCache(String refreshToken, String email) {
        cacheManager.getCache("sessionCache").put(refreshToken, email);
    }

    // 세션 정보 캐시에서 제거
    public void deleteSessionCache(String refreshToken) {
        cacheManager.getCache("sessionCache").evict(refreshToken);
    
    }
    
    public void invalidAccessToken(String accessToken) {
        // Access Token을 "logout" 상태로 캐시에 저장
        cacheManager.getCache("sessionCache").put(accessToken, "logout");
    }
    
    // 세션 갱신 및 새로운 토큰 발급
    public void reissueAndSendTokens(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = extractRefreshToken(request)
            .orElseThrow(() -> new UserException(ErrorCode.SECURITY_UNAUTHORIZED));
        Optional<String> email = Optional.ofNullable(
            (String) cacheManager.getCache("sessionCache").get(refreshToken).get());
        if (email.isEmpty()) {
            throw new UserException(ErrorCode.SECURITY_UNAUTHORIZED);
        }

        String newAccessToken = createAccessToken(email.get());
        String newRefreshToken = createRefreshToken();

        updateSessionCache(newRefreshToken, email.get());
        deleteSessionCache(refreshToken);

        response.setHeader("Authorization", BEARER_PREFIX + newAccessToken);
        response.setHeader("Refresh-Token", newRefreshToken);
    }

    // Refresh Token 추출
    public Optional<String> extractRefreshToken(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader("Refresh-Token"));
    }
}
