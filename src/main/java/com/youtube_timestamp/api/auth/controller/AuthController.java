package com.youtube_timestamp.api.auth.controller;

import com.youtube_timestamp.api.auth.dto.AuthDto;
import com.youtube_timestamp.api.auth.service.AuthService;
import com.youtube_timestamp.api.common.exception.UnAuthorizedException;
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
        Cookie accessCookie = authService.createAccessCookie(jwtResponse.accessToken);
        Cookie refreshCookie = authService.createRefreshCookie(jwtResponse.refreshToken);
        response.addCookie(accessCookie);
        response.addCookie(refreshCookie);
        return ResponseEntity.ok(jwtResponse);
    }

    @PostMapping(value = "/refresh_token")
    public ResponseEntity refresh(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = Arrays.stream(request.getCookies()).filter(cookie -> cookie.getName().equals("refresh_token")).findFirst().orElseThrow(() -> new UnAuthorizedException("재발급 토큰이 없습니다.")).getValue();
        String accessToken = authService.refreshToken(refreshToken);
        Cookie accessCookie = authService.createAccessCookie(accessToken);
        response.addCookie(accessCookie);
        AuthDto.JwtResponse jwtResponse = new AuthDto.JwtResponse(accessToken);
        return ResponseEntity.ok(jwtResponse);
    }

}
