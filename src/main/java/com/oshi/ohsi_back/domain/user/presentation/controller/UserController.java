package com.oshi.ohsi_back.domain.user.presentation.controller;

import com.oshi.ohsi_back.domain.user.application.UserService;
import com.oshi.ohsi_back.domain.user.domain.entitiy.User;
import com.oshi.ohsi_back.domain.user.presentation.dto.request.SignInRequestDto;
import com.oshi.ohsi_back.domain.user.presentation.dto.request.SignUpRequestDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    /**
     * 회원가입
     */
    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody SignUpRequestDto signUpRequestDto, HttpServletResponse response) {
        userService.register(signUpRequestDto, response);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 로그인
     */
    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody SignInRequestDto signInRequestDto, HttpServletResponse response) {
        userService.login(signInRequestDto, response);
        return ResponseEntity.ok().build();
    }

    /**
     * 토큰 재발급
     */
    @PostMapping("/reissue")
    public ResponseEntity<Void> reissueTokens(HttpServletRequest request, HttpServletResponse response) {
        userService.reissueTokens(request, response);
        return ResponseEntity.ok().build();
    }

    /**
     * 로그아웃
     */
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestHeader("Authorization") Optional<String> accessToken,
                                       @RequestHeader("Refresh-Token") Optional<String> refreshToken) {
        userService.logout(accessToken, refreshToken);
        return ResponseEntity.ok().build();
    }

    /**
     * 회원 탈퇴
     */
    @DeleteMapping("/withdraw/{id}")
    public ResponseEntity<Void> withdraw(@PathVariable Long id,
                                         @RequestHeader("Authorization") Optional<String> accessToken,
                                         @RequestHeader("Refresh-Token") Optional<String> refreshToken) {
        userService.withdraw(id, accessToken, refreshToken);
        return ResponseEntity.ok().build();
    }

    /**
     * 사용자 정보 조회
     */
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.findUserById(id);
        return ResponseEntity.ok(user);
    }
}
