package com.webproject.repositories;

import com.webproject.models.Presentation;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PresentationRepository extends JpaRepository<Presentation, Long> {

    Page<Presentation> findByTagsContaining(String x, Pageable page);
}
