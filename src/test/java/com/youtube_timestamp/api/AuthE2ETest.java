package com.youtube_timestamp.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.youtube_timestamp.api.auth.dto.AuthDto;
import com.youtube_timestamp.api.auth.service.AuthService;
import com.youtube_timestamp.api.user.entity.SocialProvider;
import com.youtube_timestamp.api.user.repository.UserRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import javax.servlet.http.Cookie;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)

public class AuthE2ETest extends BaseTest {

    private String domain = "/api/v1/auth";
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    AuthService authService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;

    @BeforeAll
    void beforeAll() {
        deleteAll();
        loader();
    }


    @Test
    public void 로그인_가입() throws Exception {
        AuthDto.LoginRequest loginRequest = new AuthDto.LoginRequest();
        loginRequest.name = "허주영";
        loginRequest.provider = SocialProvider.GOOGLE;
        loginRequest.email = "test";
        loginRequest.providerId = "testprovider";
        mockMvc.perform(postRequest(domain + "/login", loginRequest)).andExpect(status().isOk()).andDo(print());
    }

    @Test
    public void 가입된_정보로_로그인() throws Exception {
        AuthDto.LoginRequest loginRequest = new AuthDto.LoginRequest();
        loginRequest.name = "허주영";
        loginRequest.provider = SocialProvider.GOOGLE;
        loginRequest.email = "test";
        loginRequest.providerId = "testprovider";
        mockMvc.perform(postRequest(domain + "/login", loginRequest)).andExpect(status().isOk()).andDo(print())
                .andExpect(jsonPath("$.accessToken").exists())
                .andExpect(jsonPath("$.refreshToken").exists()).andExpect(status().isOk());
    }


    @Test
    public void 토큰재발급() throws Exception {
        Cookie cookie = new Cookie("refreshToken", refreshToken);
        mockMvc.perform(postRequest(domain + "/refresh_token", null).cookie(cookie)).andExpect(status().isOk()).andDo(print());
    }

    @AfterAll
    void afterAll() throws Exception {

    }
}
