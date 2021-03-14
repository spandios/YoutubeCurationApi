package com.youtube_timestamp.api.user.controller;

import com.youtube_timestamp.api.security.CurrentUser;
import com.youtube_timestamp.api.security.Jwt;
import com.youtube_timestamp.api.user.dto.UserResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/api/v1/user")
public class UserController {

    @GetMapping("/me")
    public ResponseEntity<UserResponse> me(@Jwt CurrentUser cu){
        return ResponseEntity.ok(new UserResponse(cu.toUser()));
    }

}
