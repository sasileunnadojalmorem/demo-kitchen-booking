package com.oshi.ohsi_back.domain.user.application;

import com.oshi.ohsi_back.core.properties.ErrorCode;
import com.oshi.ohsi_back.domain.user.domain.entitiy.User;
import com.oshi.ohsi_back.domain.user.enums.Role;
import com.oshi.ohsi_back.domain.user.execption.UserException;
import com.oshi.ohsi_back.domain.user.infrastructure.UserRepository;
import com.oshi.ohsi_back.domain.user.jwt.service.JwtService;
import com.oshi.ohsi_back.domain.user.presentation.dto.request.SignInRequestDto;
import com.oshi.ohsi_back.domain.user.presentation.dto.request.SignUpRequestDto;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    /**
     * 회원가입
     */
    public void register(SignUpRequestDto signUpRequestDto, HttpServletResponse response) {
        if (userRepository.existsByEmail(signUpRequestDto.getEmail())) {
            throw new UserException(ErrorCode.DUPLICATE_EMAIL);
        }

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(signUpRequestDto.getPassword());

        // 암호화된 비밀번호를 포함한 User 엔터티 생성
        User newUser = User.builder()
            .email(signUpRequestDto.getEmail())
            .nickname(signUpRequestDto.getNickname())
            .password(encodedPassword)  // 암호화된 비밀번호 저장
            .role(Role.valueOf(signUpRequestDto.getRole()))
            .build();

        userRepository.save(newUser); // 유저 저장

        // JWT 토큰 발급
        String accessToken = jwtService.createAccessToken(newUser.getEmail());
        String refreshToken = jwtService.createRefreshToken();

        jwtService.updateSessionCache(refreshToken, newUser.getEmail());

        // 응답 헤더에 토큰 추가
        response.setHeader("Authorization", "Bearer " + accessToken);
        response.setHeader("Refresh-Token", refreshToken);
    }

    /**
     * 로그인
     */
    public void login(SignInRequestDto signInRequestDto, HttpServletResponse response) {
        // 이메일로 사용자 조회
        User user = userRepository.findByEmail(signInRequestDto.getEmail())
            .orElseThrow(() -> new UserException(ErrorCode.NOT_EXISTED_USER));

        // 비밀번호 검증 (비밀번호가 맞는지 확인)
        boolean isPasswordMatch = passwordEncoder.matches(signInRequestDto.getPassword(), user.getPassword());
        if (!isPasswordMatch) {
            throw new UserException(ErrorCode.SIGN_IN_FAILED); // 비밀번호가 틀리면 예외 발생
        }

        // JWT 토큰 발급
        String accessToken = jwtService.createAccessToken(user.getEmail());
        String refreshToken = jwtService.createRefreshToken();

        jwtService.updateSessionCache(refreshToken, user.getEmail());

        // 응답 헤더에 토큰 추가
        response.setHeader("Authorization", "Bearer " + accessToken);
        response.setHeader("Refresh-Token", refreshToken);
    }

    /**
     * 토큰 재발급
     */
    public void reissueTokens(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = jwtService.extractRefreshToken(request)
            .orElseThrow(() -> new UserException(ErrorCode.REFRESH_TOKEN_REQUIRED));

        jwtService.isTokenValid(refreshToken);

        jwtService.reissueAndSendTokens(request, response);
    }

    /**
     * 로그아웃
     */
    public void logout(Optional<String> accessToken, Optional<String> refreshToken) {
        String access = accessToken
            .orElseThrow(() -> new UserException(ErrorCode.SECURITY_INVALID_ACCESS_TOKEN));
        String refresh = refreshToken
            .orElseThrow(() -> new UserException(ErrorCode.REFRESH_TOKEN_REQUIRED));

        String email = jwtService.extractEmail(access)
            .orElseThrow(() -> new UserException(ErrorCode.EMAIL_NOT_EXTRACTED));

        jwtService.isTokenValid(refresh);
        jwtService.isTokenValid(access);

        jwtService.deleteSessionCache(refresh);
        jwtService.invalidAccessToken(access);
    }

    /**
     * 회원 탈퇴
     */
    public void withdraw(Long id, Optional<String> accessToken, Optional<String> refreshToken) {
        User user = findUserById(id);
        user.delete();  // User 엔터티의 delete() 메서드 호출

        String access = accessToken
            .orElseThrow(() -> new UserException(ErrorCode.SECURITY_INVALID_ACCESS_TOKEN));
        String refresh = refreshToken
            .orElseThrow(() -> new UserException(ErrorCode.REFRESH_TOKEN_REQUIRED));

        jwtService.isTokenValid(refresh);
        jwtService.isTokenValid(access);
        jwtService.deleteSessionCache(refresh);
        jwtService.invalidAccessToken(access);
    }

    /**
     * 사용자 정보 조회
     */
    public User findUserById(Long id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new UserException(ErrorCode.NOT_EXISTED_USER));
    }
}
