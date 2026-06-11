package com.hyome.hire_radar_backend.domain.repository;

import com.hyome.hire_radar_backend.domain.model.Posting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostingRepository extends JpaRepository<Posting, Long> {
    Optional<Posting> findByUrl(String url);
    boolean existsByUrl(String url);
    List<Posting> findByStatusOrderByCreatedAtDesc(String status);
    List<Posting> findAllByOrderByCreatedAtDesc();
}
