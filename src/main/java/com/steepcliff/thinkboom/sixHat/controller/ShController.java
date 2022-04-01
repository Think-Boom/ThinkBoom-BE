package com.steepcliff.thinkboom.sixHat.controller;

import com.steepcliff.thinkboom.sixHat.dto.*;
import com.steepcliff.thinkboom.sixHat.service.ShService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/sixHat")
public class ShController {

    private final ShService shService;

    // 식스햇 방 생성.
    @PostMapping("/rooms")
    public ShRoomResponseDto createRoom(@RequestBody ShRoomRequestDto requestDto) {
        log.info("방 생성");
        return shService.createRoom(requestDto);
    }

    // 식스햇 닉네임 입력
    @PostMapping("/user/nickname")
    public ShNickResponseDto createNick(@RequestBody ShNickRequestDto requestDto) {
        log.info("닉네임 입력");
        return shService.createNickname(requestDto);
    }

    // 식스햇 공유 여부 변경 true->false
    @PostMapping("/sharing/{shRoomId}")
    public void sharingCheck(@PathVariable String shRoomId) {
        shService.shSharingCheck(shRoomId);
    }

    // 남은 시간 주기
    @GetMapping("/timer/{shRoomId}")
    public ShTimerResponseDto remainingTime(@PathVariable String shRoomId) {
        return shService.getTime(shRoomId);
    }

    // 갤러리에 저장하기
    @GetMapping("/save/gallery/{shRoomId}")
    public void saveGallery(@PathVariable String shRoomId) {
        shService.shSaveGallery(shRoomId);
    }
}
