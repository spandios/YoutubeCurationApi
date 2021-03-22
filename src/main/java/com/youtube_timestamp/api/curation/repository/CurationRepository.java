package com.youtube_timestamp.api.curation.repository;

import com.youtube_timestamp.api.curation.entity.Curation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CurationRepository extends JpaRepository<Curation, Long>, CurationCustomRepository {
    @EntityGraph(attributePaths = {"timestamp","youtube","user"})
    @Query("select c from Curation c where c.id = ?1 and c.active = true")
    Optional<Curation> findByIdWithJoin(Long id);

    @EntityGraph(attributePaths = {"timestamp"})
    @Query("select c from Curation c where c.id = ?1 and c.active = true")
    Optional<Curation> findByIdWithTimestamp(Long id);

    @Query("select c from Curation c where c.active = true and c.is_private = false")
    Page<Curation> pageable(Pageable pageable);
}
