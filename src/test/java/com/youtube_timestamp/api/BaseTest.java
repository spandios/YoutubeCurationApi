package com.youtube_timestamp.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.youtube_timestamp.api.auth.dto.AuthDto;
import com.youtube_timestamp.api.auth.service.AuthService;
import com.youtube_timestamp.api.auth.service.JwtService;
import com.youtube_timestamp.api.curation.repository.CurationRepository;
import com.youtube_timestamp.api.curation.service.CurationService;
import com.youtube_timestamp.api.user.entity.SocialProvider;
import com.youtube_timestamp.api.user.entity.UserEntity;
import com.youtube_timestamp.api.user.repository.UserRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.lang.reflect.Type;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
@Disabled
public class BaseTest {
    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected AuthService authService;

    @Autowired
    protected CurationService curationService;

    @Autowired
    protected CurationRepository curationRepository;


    @Autowired
    protected JwtService jwtService;

    @Autowired
    protected ObjectMapper objectMapper;



    @Autowired
    protected Gson gson;

    public String accessToken;

    public String refreshToken;

    UserEntity userEntity;

    @Autowired
    EntityManager entityManager;

    public void deleteAll(){
        curationRepository.deleteAll();
        userRepository.deleteAll();
    }
    public void loader() {
        createAdmin();
        createUser();
    }

    public void createUser() {
        String email = "wndudpower@gmail.com";
        String providerId = "test_provider_id";
        String name = "허주영";
        AuthDto.LoginRequest loginDto = new AuthDto.LoginRequest();
        loginDto.provider = SocialProvider.GOOGLE;
        loginDto.email = email;
        loginDto.providerId = providerId;
        loginDto.name = name;
        authService.socialLogin(loginDto);
        userEntity = userRepository.findByEmail(email).orElse(null);
        accessToken = jwtService.createAccessToken(UserEntity.builder().email(email).build());
        refreshToken = jwtService.createRefreshToken(UserEntity.builder().email(email).build());
    }

    public void createAdmin() {
        String email = "admin@gmail.com";
        String name = "허주영";
        String password = "asdf4112";
        AuthDto.AdminJoinRequest loginDto = new AuthDto.AdminJoinRequest();
        loginDto.email = email;
        loginDto.password = password;
        loginDto.name = name;
        authService.createAdmin(loginDto);
    }

    public <T> List<T> gsonList(String json) {
        Type token = new TypeToken<List<T>>() {
        }.getType();
        List<T> couponList = gson.fromJson(json, token);
        return couponList;
    }

    public MockHttpServletRequestBuilder getRequest(String url) throws Exception {
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get(url).accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).header("Authorization", "Bearer " + accessToken);
        if(accessToken != null){
            builder.header("Authorization", "Bearer " + accessToken);
        }
        return builder;
    }

    public MockHttpServletRequestBuilder postRequest(String url, Object body) throws Exception {
        MockHttpServletRequestBuilder builder = post(url).content(objectMapper.writeValueAsString(body)).accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);
        if(accessToken != null){
            builder.header("Authorization", "Bearer " + accessToken);
        }
        return builder;
    }

    public MockHttpServletRequestBuilder putRequest(String url,Object body) throws Exception {
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.put(url).content(objectMapper.writeValueAsString(body)).accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);
        if(accessToken != null){
            builder.header("Authorization", "Bearer " + accessToken);
        }
        return builder;

    }

    public MockHttpServletRequestBuilder deleteRequest(String url, String access_token) throws Exception {
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.delete(url).accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);
        if(access_token != null){
            builder.header("Authorization", "Bearer " + access_token);
        }
        return builder;
    }

}
