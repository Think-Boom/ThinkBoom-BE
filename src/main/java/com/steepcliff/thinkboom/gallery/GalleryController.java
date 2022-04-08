package com.steepcliff.thinkboom.gallery;

import com.steepcliff.thinkboom.brainWriting.dto.bwResult.BwResultResponseContainer;
import com.steepcliff.thinkboom.brainWriting.service.BwService;
import com.steepcliff.thinkboom.randomWord.dto.RwResponseContainer;
import com.steepcliff.thinkboom.randomWord.service.RandomWordService;
import com.steepcliff.thinkboom.sixHat.dto.result.ShResultResponseContainer;
import com.steepcliff.thinkboom.sixHat.service.ShMessageService;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/gallery")
public class GalleryController {

    private final GalleryService galleryService;
    private final RandomWordService randomWordService;
    private final ShMessageService shMessageService;
    private final BwService bwService;

    public GalleryController(GalleryService galleryService, RandomWordService randomWordService, ShMessageService shMessageService, BwService bwService) {
        this.galleryService = galleryService;
        this.randomWordService = randomWordService;
        this.shMessageService = shMessageService;
        this.bwService = bwService;
    }

    // 갤러리에 처음으로 나올 데이터 넘겨주기


//    // 갤러리 메인페이지 데이터 넘겨주기(페이징처리)
//    @GetMapping("")
//    public List<Gallery> getGallerypages(@RequestParam Long lastGalleryId, @RequestParam int size) {
//        return galleryService.getGalleryMain(lastGalleryId, size);
//    }

    // 갤러리 메인페이지 데이터 넘겨주기(페이징처리)
    @GetMapping("")
    public List<Gallery> getCalleryPages(Pageable pageable) {
        return galleryService.galleriesMain(pageable);
    }



    //랜덤워드 결과 데이터 넘겨주기
<<<<<<< HEAD
    @GetMapping("/randomWord/{rwId}")
=======
    @GetMapping("/randomword/{rwId}")
>>>>>>> e3ecf966c16a767229a9405bc18c6cfccdcba3a3
    public RwResponseContainer getRwGallery(@PathVariable String rwId) {

        return randomWordService.getRwGallery(rwId);
    }

    // 식스햇 결과 데이터 넘겨주기
    @GetMapping("/sixhat/{shroomid}")
    public ShResultResponseContainer getShGallery(@PathVariable String shroomid) {
        return shMessageService.getResult(shroomid);
    }

    // 브레인 라이팅 결과 데이터 넘겨주기
    @GetMapping("/brainwriting/{bwroomid}")
    public BwResultResponseContainer getBwGallery(@PathVariable String bwroomid) {
        return bwService.getResult(bwroomid);
    }

    // 방 종류에 따른 필터
    @GetMapping("/gallery/filter/{lastGalleryId}")
    public List<Gallery> getRwGallerypages(@PathVariable Long lastGalleryId, @RequestParam("type") Gallery.RoomType type, @RequestParam("size") int size) {
        return galleryService.getGalleryFilter(lastGalleryId, type, size);
    }



}
