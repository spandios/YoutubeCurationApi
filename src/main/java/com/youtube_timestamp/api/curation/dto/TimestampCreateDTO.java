package com.youtube_timestamp.api.curation.dto;

import lombok.Data;

@Data
public class TimestampCreateDTO {
    public Long curationId;
    public String title;
    public String timestamp;
    public String second;
}
