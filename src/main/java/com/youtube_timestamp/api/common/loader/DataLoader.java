package com.youtube_timestamp.api.common.loader;

import com.youtube_timestamp.api.auth.dto.AuthDto;
import com.youtube_timestamp.api.auth.service.AuthService;
import com.youtube_timestamp.api.user.entity.SocialProvider;
import com.youtube_timestamp.api.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import static com.youtube_timestamp.api.auth.dto.AuthDto.LoginRequest;

@Service
public class DataLoader {
    private final AuthService authService;
    private final UserRepository userRepository;


    public DataLoader(AuthService authService, UserRepository userRepository) {
        this.authService = authService;
        this.userRepository = userRepository;
    }

    public void loader() {
        createAdmin();
        createUser();
    }

    private void createUser() {
        String email = "wndudpower@gmail.com";
        String providerId = "test_provider_id";
        String name = "허주영";
        LoginRequest loginDto = new LoginRequest();
        loginDto.provider = SocialProvider.GOOGLE;
        loginDto.email = email;
        loginDto.providerId = providerId;
        loginDto.name = name;
        authService.socialLogin(loginDto);
    }

    private void createAdmin() {
        String email = "admin@gmail.com";
        String name = "허주영";
        String password = "asdf4112";
        AuthDto.AdminJoinRequest loginDto = new AuthDto.AdminJoinRequest();
        loginDto.email = email;
        loginDto.password = password;
        loginDto.name = name;
        authService.createAdmin(loginDto);
    }
}
