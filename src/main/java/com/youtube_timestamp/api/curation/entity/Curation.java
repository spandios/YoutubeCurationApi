package com.youtube_timestamp.api.curation.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.youtube_timestamp.api.common.base.AbstractEntity;
import com.youtube_timestamp.api.user.entity.UserEntity;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "curation")
@ToString(exclude = {"timestamp", "user"})
public class Curation extends AbstractEntity {
    @Id
    @GeneratedValue()
    Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    UserEntity user;

    @Column(length = 20)
    public String title;

    @Column(length = 255)
    public String youtubeUrl;

    @Column()
    public int viewCnt;

    @Column()
    public int likeCnt;

    @Column()
    public int commentCnt;

    @Column(columnDefinition = "boolean default 0")
    public boolean is_private;

    @OneToMany(mappedBy = "curation", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.LAZY)
    List<Timestamp> timestamp = new ArrayList<>();

    @Builder
    Curation(String title, String youtubeUrl, List<Timestamp> timestamp, UserEntity user) {
        this.title = title;
        this.youtubeUrl = youtubeUrl;
        this.timestamp = timestamp;
        this.user = user;
    }
}
