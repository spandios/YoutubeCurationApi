package com.youtube_timestamp.api.curation.dto;

import com.youtube_timestamp.api.curation.entity.Curation;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class CurationResponse {
    String title;
    String youtubeUrl;
    int viewCnt;
    int likeCnt;
    int commentCnt;
    List<TimestampResponse> timestamp;

    public CurationResponse(Curation curation) {
        this.title = curation.title;
        this.youtubeUrl = curation.youtubeUrl;
        this.viewCnt = curation.viewCnt;
        this.likeCnt = curation.likeCnt;
        this.commentCnt = curation.commentCnt;
        this.timestamp = curation.getTimestamp().stream().map(TimestampResponse::new).collect(Collectors.toList());
    }
}
