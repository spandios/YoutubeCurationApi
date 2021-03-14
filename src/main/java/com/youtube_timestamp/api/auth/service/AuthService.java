package com.youtube_timestamp.api.auth.service;

import com.youtube_timestamp.api.auth.dto.AuthDto;
import com.youtube_timestamp.api.common.exception.BadRequestException;
import com.youtube_timestamp.api.common.exception.UnAuthorizedException;
import com.youtube_timestamp.api.common.message.ExceptionMessage;
import com.youtube_timestamp.api.user.entity.UserEntity;
import com.youtube_timestamp.api.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthService {

    UserService userService;

    JwtService jwtService;

    PasswordEncoder passwordEncoder;

    public AuthService(UserService userService, JwtService jwtService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }


    public AuthDto.JwtResponse socialLogin(AuthDto.LoginRequest loginDto) {
        if (loginDto.provider == null) {
            throw new BadRequestException("소셜 제공자를 입력해주세요");
        }
        UserEntity existUser = userService.findByEmail(loginDto.email);
        if (existUser != null) {
            if (passwordEncoder.matches(loginDto.providerId, existUser.getProviderId())) {
                return jwtService.createToken(existUser);
            } else {
                throw new UnAuthorizedException(ExceptionMessage.AuthPasswordFail);
            }
        } else {
            if (loginDto.name == null) {
                throw new BadRequestException("이름을 입력해주세요");
            }
            UserEntity userEntity = UserEntity.builder()
                    .email(loginDto.email)
                    .name(loginDto.name)
                    .provider(loginDto.provider)
                    .providerId(passwordEncoder.encode(loginDto.providerId))
                    .build();
            UserEntity createdUser = userService.createUser(userEntity);
            System.out.printf(createdUser.getName());
            return jwtService.createToken(createdUser);
        }
    }

    public String refreshToken(String refreshToken) {
        String email = jwtService.getEmailFromToken(refreshToken);
        if (email == null)
            throw new UnAuthorizedException();
        if (!jwtService.validateToken(refreshToken, email))
            throw new UnAuthorizedException();

        return jwtService.createAccessToken(UserEntity.builder().email(email).build());
    }


    /**
     * 관리자 생성
     *
     * @param dto
     */
    public AuthDto.JwtResponse createAdmin(AuthDto.AdminJoinRequest dto) {
        UserEntity userEntity = UserEntity.builder()
                .email(dto.email)
                .name(dto.name)
                .providerId(passwordEncoder.encode(dto.password))
                .build();
        UserEntity createdUser = userService.createUser(userEntity);
        return jwtService.createToken(createdUser);
    }

    public AuthDto.JwtResponse loginAdmin(AuthDto.AdminLoginRequest dto) {
        UserEntity byEmail = userService.findByEmail(dto.email);
        if (byEmail == null) {
            throw new BadRequestException("유저를 찾을 수 없습니다.");
        }
        if (passwordEncoder.matches(dto.password, byEmail.getProviderId())) {
            return jwtService.createToken(byEmail);
        } else {
            throw new BadRequestException("패스워드가 틀렸습니다.");
        }
    }

}
