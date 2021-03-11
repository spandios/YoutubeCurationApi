package com.youtube_timestamp.api.curation.service;

import com.youtube_timestamp.api.curation.dto.TimestampCreateDTO;
import com.youtube_timestamp.api.curation.dto.TimestampUpdateDTO;
import com.youtube_timestamp.api.curation.entity.Timestamp;

public interface TimestampService {
    void deleteTimestamp(Long id);
    Timestamp updateTimestamp(TimestampUpdateDTO dto);
    void addTimestamp(TimestampCreateDTO dto);
}
