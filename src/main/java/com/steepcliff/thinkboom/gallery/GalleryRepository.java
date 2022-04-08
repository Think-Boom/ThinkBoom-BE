package com.steepcliff.thinkboom.gallery;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface GalleryRepository extends JpaRepository<Gallery, Long> {

//    Page<Gallery>findByIdLessThanOrderByIdDesc(Long id, PageRequest pageRequest);

    Page<Gallery> findAllByOrderByIdDesc(Pageable pageable);
<<<<<<< HEAD
    Page<Gallery>findByIdLessThanAndTypeIn(Long id, Collection<Gallery.RoomType> type, Pageable pageable);
=======
    Page<Gallery>findByIdLessThanAndCategoryIn(Long id, Collection<Gallery.RoomType> type, Pageable pageable);
>>>>>>> e3ecf966c16a767229a9405bc18c6cfccdcba3a3

    void deleteByRoomId(String roomId);
}
