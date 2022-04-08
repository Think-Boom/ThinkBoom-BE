package com.steepcliff.thinkboom.webSocket.subscriber;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.steepcliff.thinkboom.sixHat.dto.message.ShMessageResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ShRedisSubscriber {

    private final ObjectMapper objectMapper;
    private final SimpMessageSendingOperations messagingTemplate;

    @Autowired
    public ShRedisSubscriber(ObjectMapper objectMapper, SimpMessageSendingOperations messagingTemplate) {
        this.objectMapper = objectMapper;
        this.messagingTemplate = messagingTemplate;
    }


//     SixHat 보낼 메시지 메서드
    public void ShSendMessage(String publishMessage) {
        try {
            log.info("ShSendMessage");
            ShMessageResponseDto shMessageResponseDto = objectMapper.readValue(publishMessage, ShMessageResponseDto.class);
            messagingTemplate.convertAndSend("/subSH/api/sixHat/rooms/" + shMessageResponseDto.getRoomId(), shMessageResponseDto);
        } catch (Exception e) {
            log.error("Exception {}", e);
        }
    }
}
