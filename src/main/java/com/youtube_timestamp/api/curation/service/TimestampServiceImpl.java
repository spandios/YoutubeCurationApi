package com.youtube_timestamp.api.curation.service;

import com.youtube_timestamp.api.common.exception.BadRequestException;
import com.youtube_timestamp.api.curation.dto.TimestampCreateDTO;
import com.youtube_timestamp.api.curation.dto.TimestampUpdateDTO;
import com.youtube_timestamp.api.curation.entity.Timestamp;
import com.youtube_timestamp.api.curation.repository.TimestampRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TimestampServiceImpl {

    @Autowired
    private TimestampRepository repository;

    public void deleteTimestamp(Long id) {

    }

    public Timestamp findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new BadRequestException("타임스탬프를 찾을 수 없습니다."));
    }

    public Timestamp updateTimestamp(TimestampUpdateDTO dto) {
        Timestamp byId = findById(dto.id);
        byId.setSecond(dto.second);
        byId.setTitle(dto.title);
        byId.setTimestamp(dto.timestamp);
        return repository.save(byId);
    }

    public void addTimestamp(TimestampCreateDTO dto) {

    }
}
