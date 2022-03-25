package com.steepcliff.thinkboom.gallery;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class GalleryService {

    private final GalleryRepository galleryRepository;

    public GalleryService(GalleryRepository galleryRepository) {
        this.galleryRepository = galleryRepository;
    }

    // GalleryRepository에 저장
    public void saveGallery(GallerySaveResponseDto gallerySaveResponseDto) {

        Gallery gallery = new Gallery(gallerySaveResponseDto);

        galleryRepository.save(gallery);

    }

    // GalleryRepository에서 삭제
    public void deleteGallery(String roomId) {
        galleryRepository.deleteByRoomId(roomId);
    }

    // 갤러리 메인 페이지 데이터 전달
    public List<Gallery> getGalleryMain(Long lastGalleryId, int size) {
        Page<Gallery> galleries = fetchPages(lastGalleryId, size);

        return galleries.getContent();
    }
    // 갤러리 메인페이지 페이지네이션
    public Page<Gallery> fetchPages(Long lastGalleryId, int size) {
        PageRequest pageRequest = PageRequest.of(0, size); // 페이지네이션을 위한 PageRequest, 페이지는 0으로 고전한다.
        return galleryRepository.findByIdLessThanOrderByIdDesc(lastGalleryId, pageRequest); // JPA 쿼리 메소드
    }


    public List<Gallery> getGalleryFilter(Long lastGalleryId, Gallery.RoomType type, int size) {
        List<Gallery> galleryList = new ArrayList<>();
        if(Gallery.RoomType.RW.equals(type)) {
            Page<Gallery> galleries = rwFetchPages(lastGalleryId, size);
            galleryList = galleries.getContent();
        } else if(Gallery.RoomType.BW.equals(type)) {
            Page<Gallery> galleries = bwFetchPages(lastGalleryId, size);
            galleryList = galleries.getContent();
        } else if(Gallery.RoomType.SH.equals(type)) {
            Page<Gallery> galleries = shFetchPages(lastGalleryId, size);
            galleryList = galleries.getContent();
        }

        return galleryList;
    }


    public Page<Gallery> rwFetchPages(Long lastGalleryId, int size) {
        PageRequest pageRequest = PageRequest.of(0, size);
        return galleryRepository.findByIdLessThanAndTypeIn(lastGalleryId, Collections.singleton(Gallery.RoomType.RW), pageRequest);
    }

    public Page<Gallery> bwFetchPages(Long lastGalleryId, int size) {
        PageRequest pageRequest = PageRequest.of(0, size);
        return galleryRepository.findByIdLessThanAndTypeIn(lastGalleryId, Collections.singleton(Gallery.RoomType.BW), pageRequest);
    }

    public Page<Gallery> shFetchPages(Long lastGalleryId, int size) {
        PageRequest pageRequest = PageRequest.of(0, size);
        return galleryRepository.findByIdLessThanAndTypeIn(lastGalleryId, Collections.singleton(Gallery.RoomType.SH), pageRequest);
    }



}
