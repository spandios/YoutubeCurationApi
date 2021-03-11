package com.youtube_timestamp.api.curation.dto;

import com.youtube_timestamp.api.curation.entity.Timestamp;
import lombok.Data;

@Data
public class TimestampResponse {
    Long id;
    String title;
    String timestamp;

    public TimestampResponse(Timestamp timestamp){
        this.id= timestamp.getId();
        this.timestamp= timestamp.getTimestamp();
        this.title= timestamp.getTitle();
    }
}
