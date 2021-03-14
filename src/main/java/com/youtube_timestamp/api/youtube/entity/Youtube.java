package com.youtube_timestamp.api.youtube.entity;

import com.youtube_timestamp.api.common.base.AbstractEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Youtube extends AbstractEntity {
    @Id
    String id;

    @Column
    String youtubeTitle;

    @Column
    String tags;

    @Column
    int categoryId;

    @Column
    String url;

    @Column
    String thumbnail;

    @Column
    String channelTitle;


    public Youtube(String youtubeId, String youtubeTitle, String tags, int categoryId, String url, String thumbnail, String channelTitle) {
        this.id = youtubeId;
        this.youtubeTitle = youtubeTitle;
        this.tags = tags;
        this.categoryId = categoryId;
        this.url = url;
        this.thumbnail = thumbnail;
        this.channelTitle = channelTitle;
    }
}
