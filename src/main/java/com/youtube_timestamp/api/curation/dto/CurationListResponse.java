package com.youtube_timestamp.api.curation.dto;

import com.youtube_timestamp.api.curation.entity.Curation;
import com.youtube_timestamp.api.youtube.entity.Youtube;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CurationListResponse {
    Long id;
    String title;
    String youtubeUrl;
    int viewCnt;
    int likeCnt;
    int commentCnt;
    Youtube youtube;
    LocalDateTime createdAt;

    public CurationListResponse(Curation curation) {
        this.id = curation.getId();
        this.title = curation.title;
        this.youtube = curation.getYoutube();
        this.viewCnt = curation.viewCnt;
        this.likeCnt = curation.likeCnt;
        this.commentCnt = curation.commentCnt;
        this.createdAt = curation.getCreatedAt();
    }
}
