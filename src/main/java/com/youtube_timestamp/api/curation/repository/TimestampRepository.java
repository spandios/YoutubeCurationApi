package com.youtube_timestamp.api.curation.repository;

import com.youtube_timestamp.api.curation.entity.Timestamp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimestampRepository extends JpaRepository<Timestamp, Long> {
}
