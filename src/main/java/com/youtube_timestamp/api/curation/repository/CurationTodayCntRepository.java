package com.youtube_timestamp.api.curation.repository;

import com.youtube_timestamp.api.curation.entity.Curation;
import com.youtube_timestamp.api.curation.entity.CurationTodayCnt;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CurationTodayCntRepository extends JpaRepository<CurationTodayCnt, Long> {
    Optional<CurationTodayCnt> findByTodayAndCuration(LocalDate today, Curation curation);

    @EntityGraph(attributePaths = "curation")
    @Query("select ct from CurationTodayCnt ct where ct.today = ?1 and ct.active = true order by ct.cnt desc ")
    List<CurationTodayCnt> findTodayPopular(LocalDate today);
}
