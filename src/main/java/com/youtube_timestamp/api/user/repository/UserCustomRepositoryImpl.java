package com.youtube_timestamp.api.user.repository;

import com.youtube_timestamp.api.user.entity.QUserEntity;
import com.youtube_timestamp.api.user.entity.SocialProvider;
import com.youtube_timestamp.api.user.entity.UserEntity;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.Optional;

public class UserCustomRepositoryImpl extends QuerydslRepositorySupport implements UserCustomRepository {
    QUserEntity qUserEntity = QUserEntity.userEntity;

    public UserCustomRepositoryImpl() {
        super(UserEntity.class);
    }

    @Override
    public Optional<UserEntity> findBySocial(SocialProvider provider, String providerId) {
        return Optional.ofNullable(from(qUserEntity).where(qUserEntity.provider.eq(provider)).where(qUserEntity.providerId.eq(providerId)).fetchOne());
    }
}
