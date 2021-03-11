package com.youtube_timestamp.api.curation.repository;

import com.youtube_timestamp.api.curation.entity.Curation;
import com.youtube_timestamp.api.user.entity.UserEntity;

import java.util.List;

public interface CurationCustomRepository {
    List<Curation> myList(UserEntity user);
}
