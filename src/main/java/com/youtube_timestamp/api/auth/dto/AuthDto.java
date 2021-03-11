package com.youtube_timestamp.api.auth.dto;

import com.youtube_timestamp.api.user.entity.SocialProvider;

import javax.validation.constraints.NotEmpty;

public class AuthDto {

    private AuthDto() {
    }

    public static class LoginRequest {
        @NotEmpty(message = "소셜 아이디가 없습니다")
        public String providerId;
        public SocialProvider provider;
        @NotEmpty(message = "이메일을 입력해주세요")
        public String email;
        public String name;
    }

    public static class AdminJoinRequest {
        @NotEmpty()
        public String name;
        @NotEmpty()
        public String email;
        @NotEmpty()
        public String password;
    }

    public static class AdminLoginRequest {
        @NotEmpty()
        public String name;
        @NotEmpty()
        public String email;
        @NotEmpty()
        public String password;
    }

    public static class JwtResponse {
        public String accessToken;
        public String refreshToken;

        public JwtResponse(String accessToken, String refreshToken) {
            this.accessToken = accessToken;
            this.refreshToken = refreshToken;
        }

        public JwtResponse(String accessToken) {
            this.accessToken = accessToken;
        }
    }

    public static class RefreshTokenRequest {
        public String accessToken;
    }
}
