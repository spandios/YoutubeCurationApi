package com.youtube_timestamp.api.curation.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.youtube_timestamp.api.common.base.AbstractEntity;
import com.youtube_timestamp.api.user.entity.UserEntity;
import com.youtube_timestamp.api.youtube.entity.Youtube;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "curation")
@ToString(exclude = {"timestamp", "user","youtube"})
public class Curation extends AbstractEntity {
    @Id
    @GeneratedValue()
    Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    UserEntity user;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    Youtube youtube;

    @OneToMany(mappedBy = "curation", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.LAZY)
    List<Timestamp> timestamp = new ArrayList<>();

    @Column(length = 20)
    public String title;

    @Column()
    public int viewCnt;

    @Column()
    public int likeCnt;

    @Column()
    public int commentCnt;

    @Column(columnDefinition = "boolean default 0")
    public boolean is_private;


    @Builder
    Curation(String title, Youtube youtube, List<Timestamp> timestamp, UserEntity user) {
        this.title = title;
        this.youtube = youtube;
        this.timestamp = timestamp;
        this.user = user;
    }
}
