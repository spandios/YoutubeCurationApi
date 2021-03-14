package com.youtube_timestamp.api.user.service;

import com.youtube_timestamp.api.user.entity.UserEntity;
import com.youtube_timestamp.api.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserEntity createUser(UserEntity userEntity) {
        return this.userRepository.save(userEntity);
    }

    public UserEntity findByEmail(String email) {
        return this.userRepository.findByEmailAndActiveTrue(email).orElse(null);
    }
}
