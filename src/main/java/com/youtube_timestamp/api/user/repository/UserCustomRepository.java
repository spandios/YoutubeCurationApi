package com.youtube_timestamp.api.user.repository;

import com.youtube_timestamp.api.user.entity.SocialProvider;
import com.youtube_timestamp.api.user.entity.UserEntity;

import java.util.Optional;

public interface UserCustomRepository {
    Optional<UserEntity> findBySocial(SocialProvider provider, String providerId);
}
