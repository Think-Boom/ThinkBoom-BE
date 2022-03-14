package com.steepcliff.thinkboom.randomWord.controller;


import com.steepcliff.thinkboom.randomWord.dto.RwRequestDto;
import com.steepcliff.thinkboom.randomWord.dto.RwResponseDto;
import com.steepcliff.thinkboom.randomWord.dto.WordDto;
import com.steepcliff.thinkboom.randomWord.service.RandomWordService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
public class RandomWordController {
    private final RandomWordService wordService;

    @GetMapping("/randomWord")
    public List<WordDto> getWordList() {
        return wordService.getWord();
    }

    @PostMapping("/randomWord")
    public RwResponseDto saveWord(@RequestBody RwRequestDto requestDto) {
        return wordService.saveWord(requestDto);
    }

    @PatchMapping("/randomWord/share/{rwId}")
    public String shareCheck(@PathVariable String rwId) {
        return wordService.shareCheck(rwId);
    }

    @GetMapping("/gallery/randomWord/{rwId}")
    public RwResponseDto getRwGallery(@PathVariable String rwId) {
        return wordService.getRwGallery(rwId);
    }
}