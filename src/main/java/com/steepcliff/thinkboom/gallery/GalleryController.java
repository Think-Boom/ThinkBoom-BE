package com.steepcliff.thinkboom.gallery;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/gallery")
public class GalleryController {

    private final GalleryService galleryService;

    public GalleryController(GalleryService galleryService) {
        this.galleryService = galleryService;
    }

    @GetMapping("")
    public List<Gallery> getGallerypages(@RequestParam Long lastGalleryId, @RequestParam int size) {
        return galleryService.getGalleryMain(lastGalleryId, size);
    }


}
