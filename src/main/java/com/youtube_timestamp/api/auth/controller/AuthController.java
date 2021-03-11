package com.youtube_timestamp.api.auth.controller;

import com.youtube_timestamp.api.auth.dto.AuthDto;
import com.youtube_timestamp.api.auth.service.AuthService;
import com.youtube_timestamp.api.common.exception.UnAuthorizedException;
import io.netty.util.internal.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Arrays;


@Slf4j
@RequestMapping("/api/v1/auth")
@RestController
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping("/login")
    public ResponseEntity login(@Valid @RequestBody AuthDto.LoginRequest login, HttpServletResponse response, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(errors);
        }
        AuthDto.JwtResponse jwtResponse = authService.socialLogin(login);
        Cookie accessToken = new Cookie("accessToken", jwtResponse.accessToken);
        Cookie refreshToken = new Cookie("refreshToken", jwtResponse.refreshToken);
        refreshToken.setHttpOnly(true);
        response.addCookie(accessToken);
        response.addCookie(refreshToken);

        return ResponseEntity.ok(authService.socialLogin(login));
    }

    @PostMapping(value = "/refresh_token")
    public ResponseEntity refresh(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = Arrays.stream(request.getCookies()).filter(cookie -> cookie.getName().equals("refreshToken")).findFirst().orElseThrow(() -> new UnAuthorizedException("재발급 토큰이 없습니다.")).getValue();
        if (StringUtil.isNullOrEmpty(refreshToken)) {
            throw new UnAuthorizedException("재발급 토큰이 없습니다.");
        }
        String accessToken = authService.refreshToken(refreshToken);
        Cookie cookie = new Cookie("accessToken", accessToken);
        response.addCookie(cookie);
        AuthDto.JwtResponse jwtResponse = new AuthDto.JwtResponse(accessToken);
        return ResponseEntity.ok(jwtResponse);
    }

}
