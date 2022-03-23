package com.steepcliff.thinkboom.gallery;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GalleryRepository extends JpaRepository<Gallery, Long> {

    Page<Gallery>findByIdLessThanOrderByIdDesc(Long id, PageRequest pageRequest);
}
