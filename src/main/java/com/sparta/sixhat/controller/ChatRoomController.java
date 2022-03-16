package com.sparta.sixhat.controller;

import com.sparta.sixhat.dto.ChatRoomRequestDto;
import com.sparta.sixhat.dto.ChatRoomResponseDto;
import com.sparta.sixhat.service.ChatRoomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


//@CrossOrigin("*")
@Slf4j
@RestController
//@RequestMapping("/api/chat")
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    @Autowired
    public ChatRoomController(ChatRoomService chatRoomService) {
        this.chatRoomService = chatRoomService;
    }

    //@PostMapping("/shRoom")
    @PostMapping("/api/sixHat/room")
    public ChatRoomResponseDto chatRoomResponseDto (@RequestBody ChatRoomRequestDto requestDto) {
        return chatRoomService.createChatRoom(requestDto);
    }

//    @PostMapping("/api/sixHat/timer")
//    public ChatRoomResponseDto setTimer (@RequestBody ChatRoomRequestDto requestDto) {
//        return chatRoomService.setTimer(requestDto);
//    }


}
