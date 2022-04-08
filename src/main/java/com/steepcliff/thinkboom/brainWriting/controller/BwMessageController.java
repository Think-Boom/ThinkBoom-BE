package com.steepcliff.thinkboom.brainWriting.controller;

import com.steepcliff.thinkboom.brainWriting.domain.BwChatMessage;
import com.steepcliff.thinkboom.brainWriting.dto.bwMessage.BwMessageRequestDto;
import com.steepcliff.thinkboom.brainWriting.dto.bwMessage.BwMessageResponseDto;
import com.steepcliff.thinkboom.brainWriting.service.BwMessageService;
import com.steepcliff.thinkboom.sixHat.domain.ShChatMessage;
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

    @MessageMapping("/api/brainwriting/chat/message")
    public void bwMessage(@RequestBody BwMessageRequestDto requestDto) {
        log.info("message controller 시작");
        log.info(requestDto.toString());

        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm");
        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
        String dateResult = sdf.format(date);
        log.info("날짜 생성 완료");

        BwMessageResponseDto bwMessageResponseDto = new BwMessageResponseDto();
        bwMessageResponseDto.setType(requestDto.getType());
        bwMessageResponseDto.setRoomId(requestDto.getRoomId());
        bwMessageResponseDto.setMessage(requestDto.getMessage());
        bwMessageResponseDto.setSender(requestDto.getSender());
        bwMessageResponseDto.setSenderId(requestDto.getSenderId());
        bwMessageResponseDto.setCreatedAt(dateResult);

        if(bwMessageResponseDto.getType().equals(BwChatMessage.MessageType.NEXTPAGE)) {
            log.info("현재 페이지 {}", requestDto.getCurrentPage());
            bwMessageResponseDto.setCurrentPage(requestDto.getCurrentPage());
        } else if(bwMessageResponseDto.getType().equals(BwChatMessage.MessageType.SUBJECT)) {
            log.info("브레인라이팅 주제: {}", requestDto.getSubject());
            bwMessageResponseDto.setSubject(requestDto.getSubject());
        }

        log.info("값 담기 완료");
        bwMessageService.SendBwChatMessage(bwMessageResponseDto);

        log.info("sendBwChatMessage 완료 userId {}", bwMessageResponseDto.getSenderId());

        bwMessageService.save(bwMessageResponseDto);
        log.info("message controller 끝");
    }
}
