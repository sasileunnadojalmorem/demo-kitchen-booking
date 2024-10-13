package com.oshi.ohsi_back.domain.user.presentation.dto.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequestDto {

    @Email
    @NotBlank(message = "이메일은 필수 항목입니다.")
    private String email;

    @NotBlank(message = "닉네임은 필수 항목입니다.")
    @Size(max = 10, message = "닉네임은 최대 10글자까지 가능합니다.")
    private String nickname;

    @NotBlank(message = "비밀번호는 필수 항목입니다.")
    @Size(min = 8, message = "비밀번호는 최소 8자리 이상이어야 합니다.")
    private String password;

    @NotBlank(message = "역할은 필수 항목입니다.")
    private String role;  // 사용자의 역할 (예: USER, ADMIN 등)
}