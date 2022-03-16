package com.sparta.sixhat.controller;

import com.sparta.sixhat.dto.ChatMessageRequestDto;
import com.sparta.sixhat.dto.ChatMessageResponseDto;
import com.sparta.sixhat.service.ChatMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;


//@CrossOrigin("*")
@Slf4j
@RestController
public class ChatMessageController {

    private final ChatMessageService chatMessageService;

    @Autowired
    public ChatMessageController(ChatMessageService chatMessageService) {
        this.chatMessageService = chatMessageService;
    }


//    @MessageMapping("/pub/api/sixHat/chat/message")
//    //public ChatMessageResponseDto chatMessageResponseDto(@RequestBody ChatMessageRequestDto requestDto) {
//    public void message(@RequestBody ChatMessageRequestDto requestDto) {
//        log.info("message controller 시작");
//        log.info(requestDto.toString());
//        // 메시지 생성 시간 삽입
//        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm");
//        Calendar cal = Calendar.getInstance();
//        Date date = cal.getTime();
//        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
//        String dateResult = sdf.format(date);
//        log.info("날짜 생성 완료");
//        ChatMessageResponseDto chatMessageResponseDto = new ChatMessageResponseDto();
//        log.info("ChatMessageResponseDto 객체 생성완료");
//        chatMessageResponseDto.setType(requestDto.getType());
//        chatMessageResponseDto.setRoomId(requestDto.getRoomId());
//        chatMessageResponseDto.setMessage(requestDto.getMessage());
//        chatMessageResponseDto.setHat(requestDto.getHat());
//        chatMessageResponseDto.setSender(requestDto.getSender());
//        chatMessageResponseDto.setSenderId(requestDto.getSenderId());
//        chatMessageResponseDto.setCreatedAt(dateResult);
//        log.info("값 담기 완료");
//        chatMessageService.sendChatMessage(chatMessageResponseDto);
//        log.info("sendChatMessage 완료");
//        log.info("memberId {}", chatMessageResponseDto.getSenderId());
//
//
//
//        chatMessageService.save(chatMessageResponseDto);
//        log.info("message controller 끝");
//
//        //return chatMessageResponseDto;
//    }

    @MessageMapping("/api/sixHat/chat/message")
    //public ChatMessageResponseDto chatMessageResponseDto(@RequestBody ChatMessageRequestDto requestDto) {
    public void message(@RequestBody ChatMessageRequestDto requestDto) {
        log.info("message controller 시작");
        log.info(requestDto.toString());
        // 메시지 생성 시간 삽입
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm");
        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
        String dateResult = sdf.format(date);
        log.info("날짜 생성 완료");
        ChatMessageResponseDto chatMessageResponseDto = new ChatMessageResponseDto();
        log.info("ChatMessageResponseDto 객체 생성완료");
        chatMessageResponseDto.setType(requestDto.getType());
        chatMessageResponseDto.setRoomId(requestDto.getRoomId());
        chatMessageResponseDto.setMessage(requestDto.getMessage());
        chatMessageResponseDto.setHat(requestDto.getHat());
        chatMessageResponseDto.setSender(requestDto.getSender());
        chatMessageResponseDto.setSenderId(requestDto.getSenderId());
        //chatMessageResponseDto.setSenderId(senderId);
        chatMessageResponseDto.setCreatedAt(dateResult);
        log.info("값 담기 완료");
        chatMessageService.sendChatMessage(chatMessageResponseDto);
        log.info("sendChatMessage 완료");
        log.info("memberId {}", chatMessageResponseDto.getSenderId());



        chatMessageService.save(chatMessageResponseDto);
        log.info("message controller 끝");

        //return chatMessageResponseDto;
    }


}
