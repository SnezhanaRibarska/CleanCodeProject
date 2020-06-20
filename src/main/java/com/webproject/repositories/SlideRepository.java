package com.webproject.repositories;

import com.webproject.models.Presentation;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webproject.models.Slide;

@Repository
public interface SlideRepository extends JpaRepository<Slide, Long> {

    List<Slide> findAllByPresentationEquals(Presentation p);
}
