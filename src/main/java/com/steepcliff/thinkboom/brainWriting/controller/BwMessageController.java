package com.steepcliff.thinkboom.brainWriting.controller;

import com.steepcliff.thinkboom.brainWriting.dto.bwMessage.BwMessageRequestDto;
import com.steepcliff.thinkboom.brainWriting.dto.bwMessage.BwMessageResponseDto;
import com.steepcliff.thinkboom.brainWriting.service.BwMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

@Slf4j
@RestController
public class BwMessageController {

    private final BwMessageService bwMessageService;

    public BwMessageController(BwMessageService bwMessageService) {
        this.bwMessageService = bwMessageService;
    }

    @MessageMapping("/api/brainWriting/chat/message")
    public void bwMessage(@RequestBody BwMessageRequestDto requestDto) {
        log.info("message controller 시작");
        log.info(requestDto.toString());

        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm");
        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
        String dateResult = sdf.format(date);
        log.info("날짜 생성 완료");

        BwMessageResponseDto bwChatMessageResponseDto = new BwMessageResponseDto();
        bwChatMessageResponseDto.setType(requestDto.getType());
        bwChatMessageResponseDto.setRoomId(requestDto.getRoomId());
        bwChatMessageResponseDto.setMessage(requestDto.getMessage());
        bwChatMessageResponseDto.setSender(requestDto.getSender());
        bwChatMessageResponseDto.setSenderId(requestDto.getSenderId());
        bwChatMessageResponseDto.setCreatedAt(dateResult);
        log.info("값 담기 완료");
        bwMessageService.SendBwChatMessage(bwChatMessageResponseDto);

        log.info("sendBwChatMessage 완료 userId {}", bwChatMessageResponseDto.getSenderId());

        bwMessageService.save(bwChatMessageResponseDto);
        log.info("message controller 끝");
    }
}
