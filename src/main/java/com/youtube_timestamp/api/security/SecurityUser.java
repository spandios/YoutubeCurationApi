package com.youtube_timestamp.api.security;


import com.youtube_timestamp.api.user.entity.UserEntity;
import lombok.Getter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.Set;

/**
 * 시큐리티에서 사용되어지는 User 도메인
 */


@ToString
@Getter
public class SecurityUser extends org.springframework.security.core.userdetails.User implements Serializable {
    private Long id;
    private String email;
    private String name;

    public SecurityUser(Long id,
                        String email,
                        String password,
                        String name,
                        Set<GrantedAuthority> authorities) {

        super(email, password, authorities);
        this.id = id;
        this.email = email;
        this.name = name;
    }

    public com.youtube_timestamp.api.user.entity.UserEntity toUser(){
        return com.youtube_timestamp.api.user.entity.UserEntity.builder().id(this.id).email(this.email).name(this.name).build();
    }
}
