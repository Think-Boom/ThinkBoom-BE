package com.steepcliff.thinkboom.gallery;

import com.steepcliff.thinkboom.brainWriting.dto.bwResult.BwResultResponseDto;
import com.steepcliff.thinkboom.brainWriting.service.BwService;
import com.steepcliff.thinkboom.randomWord.dto.RwResponseDto;
import com.steepcliff.thinkboom.randomWord.service.RandomWordService;
import com.steepcliff.thinkboom.sixHat.service.ShMessageService;
import com.steepcliff.thinkboom.sixHat.dto.ShResultResponseDto;
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

    @GetMapping("")
    public List<Gallery> getGallerypages(@RequestParam Long lastGalleryId, @RequestParam int size) {
        return galleryService.getGalleryMain(lastGalleryId, size);
    }

    //랜덤워드 결과에 관한 상세 조회 요청
    @GetMapping("/randomWord/{rwId}")
    public RwResponseDto getRwGallery(@PathVariable String rwId) {

        return randomWordService.getRwGallery(rwId);
    }

    // 식스햇 결과 데이터 넘겨주기
    @GetMapping("/sixhat/{shroomid}")
    public ShResultResponseDto getShGallery(@PathVariable String shroomid) {
        return shMessageService.getResult(shroomid);
    }

    // 브레인 라이팅 결과 데이터 넘겨주기
    @GetMapping("/brainwriting/{bwroomid}")
    public BwResultResponseDto getBwGallery(@PathVariable String bwroomid) {
        return bwService.getResult(bwroomid);
    }


    @GetMapping("/randomWord/filter/{lastGalleryId}")
    public List<Gallery> getRwGallerypages(@PathVariable Long lastGalleryId, @RequestParam("type") Gallery.RoomType type, @RequestParam("size") int size) {
        return galleryService.getGalleryFilter(lastGalleryId, type, size);
    }

}
