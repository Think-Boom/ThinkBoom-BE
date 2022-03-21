package com.steepcliff.thinkboom.gallery;

import com.steepcliff.thinkboom.brainWriting.service.BwService;
import com.steepcliff.thinkboom.randomWord.service.RandomWordService;
import com.steepcliff.thinkboom.sixHat.ShService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GalleryService {

    private final RandomWordService randomWordService;
    private final BwService bwService;
    private final ShService shService;
    private final GalleryRepository galleryRepository;


    public GalleryService(RandomWordService randomWordService, BwService bwService, ShService shService, GalleryRepository galleryRepository) {
        this.randomWordService = randomWordService;
        this.bwService = bwService;
        this.shService = shService;
        this.galleryRepository = galleryRepository;
    }

    public List<Gallery> getGalleryMain(Long lastGalleryId, int size) {
        Page<Gallery> galleries = fetchPages(lastGalleryId, size);

        return galleries.getContent();
    }

    public Page<Gallery> fetchPages(Long lastGalleryId, int size) {
        PageRequest pageRequest = PageRequest.of(0, size); // 페이지네이션을 위한 PageRequest, 페이지는 0으로 고전한다.
        return galleryRepository.findByIdLessThanOrderByIdDesc(lastGalleryId, pageRequest); // JPA 쿼리 메소드
    }
}
