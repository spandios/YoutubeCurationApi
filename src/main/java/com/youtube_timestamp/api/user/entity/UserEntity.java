package com.youtube_timestamp.api.user.entity;

import com.youtube_timestamp.api.common.base.AbstractEntity;
import lombok.*;

import javax.persistence.*;

@Entity(name = "Users")
@Getter
@Setter
@NoArgsConstructor()
public class UserEntity extends AbstractEntity {
    @GeneratedValue()
    @Id
    Long id;

    @Column(unique = true)
    String email;

    @Column()
    String providerId;

    @Column(length = 20)
    SocialProvider provider;

    @Column(length = 20)
    String name;

    @Enumerated(EnumType.STRING)
    UserRole role;

    @Builder
    public UserEntity(Long id, String email, String providerId, SocialProvider provider, String name, UserRole role) {
        this.id = id;
        this.email = email;
        this.providerId = providerId;
        this.provider = provider;
        this.name = name;
        this.role = role != null ? role : UserRole.USER;
    }
}
