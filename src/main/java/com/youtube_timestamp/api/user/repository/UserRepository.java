package com.youtube_timestamp.api.user.repository;

import com.youtube_timestamp.api.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<UserEntity,Long>, UserCustomRepository {
    Optional<UserEntity> findByEmailAndActiveTrue(String email);
}
