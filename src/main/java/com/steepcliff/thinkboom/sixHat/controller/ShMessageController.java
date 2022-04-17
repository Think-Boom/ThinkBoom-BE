package com.steepcliff.thinkboom.sixHat.controller;

import com.steepcliff.thinkboom.sixHat.service.ShMessageService;
import com.steepcliff.thinkboom.sixHat.domain.ShChatMessage;
import com.steepcliff.thinkboom.sixHat.dto.message.ShMessageRequestDto;
import com.steepcliff.thinkboom.sixHat.dto.message.ShMessageResponseDto;
import com.steepcliff.thinkboom.user.UserService;
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
    private final UserService userService;

    @Autowired
    public ShMessageController(ShMessageService shMessageService, UserService userService) {
        this.shMessageService = shMessageService;
        this.userService = userService;
    }

    @MessageMapping("/api/sixHat/chat/message")
    public void shMessage(@RequestBody ShMessageRequestDto requestDto) {

        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm");
        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
        String dateResult = sdf.format(date);

        ShMessageResponseDto shMessageResponseDto = new ShMessageResponseDto();
        shMessageResponseDto.setType(requestDto.getType());
        shMessageResponseDto.setRoomId(requestDto.getRoomId());
        shMessageResponseDto.setMessage(requestDto.getMessage());
        shMessageResponseDto.setHat(requestDto.getHat());
        shMessageResponseDto.setSender(requestDto.getSender());
        shMessageResponseDto.setSenderId(requestDto.getSenderId());
        shMessageResponseDto.setCreatedAt(dateResult);

        if(shMessageResponseDto.getType().equals(ShChatMessage.MessageType.SUBJECT)) {
            shMessageResponseDto.setSubject(requestDto.getSubject());
        } else if(shMessageResponseDto.getType().equals(ShChatMessage.MessageType.RANDOMHAT)) {
            shMessageResponseDto.setRandomHat(requestDto.getRandomHat());
        } else if(shMessageResponseDto.getType().equals(ShChatMessage.MessageType.NEXTPAGE)) {
            shMessageResponseDto.setCurrentPage(requestDto.getCurrentPage());
        } else if(shMessageResponseDto.getType().equals(ShChatMessage.MessageType.HAT)) {
            userService.saveHat(requestDto.getSenderId(), requestDto.getHat());
        }

        shMessageService.SendShChatMessage(shMessageResponseDto);

        if(shMessageResponseDto.getType().equals(ShChatMessage.MessageType.DEBATING)) {
            shMessageService.save(shMessageResponseDto);
        }
    }
}
