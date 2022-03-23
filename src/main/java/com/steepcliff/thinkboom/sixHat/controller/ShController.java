package com.steepcliff.thinkboom.sixHat.controller;

import com.steepcliff.thinkboom.sixHat.ShService;
import com.steepcliff.thinkboom.sixHat.dto.ShNickRequestDto;
import com.steepcliff.thinkboom.sixHat.dto.ShNickResponseDto;
import com.steepcliff.thinkboom.sixHat.dto.ShRoomRequestDto;
import com.steepcliff.thinkboom.sixHat.dto.ShRoomResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
