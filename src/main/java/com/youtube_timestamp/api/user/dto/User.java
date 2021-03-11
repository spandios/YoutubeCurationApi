package com.youtube_timestamp.api.user.dto;

import com.youtube_timestamp.api.user.entity.UserEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class User {
    public User(UserEntity userEntity){
        this.id = userEntity.getId();
        this.email = userEntity.getName();
        this.name = userEntity.getName();
    }
    Long id;
    String email;
    String name;
}
