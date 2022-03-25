package com.steepcliff.thinkboom.sixHat.controller;

import com.steepcliff.thinkboom.sixHat.service.ShMessageService;
import com.steepcliff.thinkboom.sixHat.domain.ShChatMessage;
import com.steepcliff.thinkboom.sixHat.dto.message.ShMessageRequestDto;
import com.steepcliff.thinkboom.sixHat.dto.message.ShMessageResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

@Slf4j
@RestController
public class ShMessageController {

    private final ShMessageService shMessageService;

    @Autowired
    public ShMessageController(ShMessageService shMessageService) {
        this.shMessageService = shMessageService;
    }

    @MessageMapping("/api/sixHat/chat/message")
    public void shMessage(@RequestBody ShMessageRequestDto requestDto) {
        log.info("six hat message controller 시작");

        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm");
        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
        String dateResult = sdf.format(date);
        log.info("날짜 생성 완료");

        ShMessageResponseDto shMessageResponseDto = new ShMessageResponseDto();
        shMessageResponseDto.setType(requestDto.getType());
        shMessageResponseDto.setRoomId(requestDto.getRoomId());
        shMessageResponseDto.setMessage(requestDto.getMessage());
        shMessageResponseDto.setHat(requestDto.getHat());
        shMessageResponseDto.setSender(requestDto.getSender());
        shMessageResponseDto.setSenderId(requestDto.getSenderId());
        shMessageResponseDto.setSubject(requestDto.getSubject());
        shMessageResponseDto.setCreatedAt(dateResult);

        if(shMessageResponseDto.getType().equals(ShChatMessage.MessageType.RANDOMHAT)) {
            shMessageResponseDto.setRandomHat(requestDto.getRandomHat());
        }

        shMessageService.SendShChatMessage(shMessageResponseDto);

        log.info("sendBwChatMessage 완료 userId {}", shMessageResponseDto.getSenderId());
        if(shMessageResponseDto.getType().equals(ShChatMessage.MessageType.DEBATING)) {
            shMessageService.save(shMessageResponseDto);
        }
        log.info("six hat message controller 끝");
    }
}
