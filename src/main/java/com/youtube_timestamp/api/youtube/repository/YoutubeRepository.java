package com.youtube_timestamp.api.youtube.repository;

import com.youtube_timestamp.api.youtube.entity.Youtube;
import org.springframework.data.jpa.repository.JpaRepository;

public interface YoutubeRepository extends JpaRepository<Youtube, String>, YoutubeCustomRepository {
}
