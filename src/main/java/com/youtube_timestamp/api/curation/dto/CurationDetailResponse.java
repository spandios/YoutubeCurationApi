package com.youtube_timestamp.api.curation.dto;

import com.youtube_timestamp.api.curation.entity.Curation;
import com.youtube_timestamp.api.youtube.entity.Youtube;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class CurationDetailResponse {
    Long id;
    String title;
    int viewCnt;
    int likeCnt;
    int commentCnt;
    Youtube youtube;
    List<TimestampResponse> timestamp = new ArrayList<>();
    boolean yours = false;
    LocalDateTime createdAt;

    public CurationDetailResponse(Curation curation) {
        this.id = curation.getId();
        this.title = curation.title;
        this.youtube = curation.getYoutube();
        this.viewCnt = curation.viewCnt;
        this.likeCnt = curation.likeCnt;
        this.commentCnt = curation.commentCnt;
        if(curation.getTimestamp() != null)
        this.timestamp = curation.getTimestamp().stream().map(TimestampResponse::new).collect(Collectors.toList());
        this.createdAt = curation.getCreatedAt();
    }
}
