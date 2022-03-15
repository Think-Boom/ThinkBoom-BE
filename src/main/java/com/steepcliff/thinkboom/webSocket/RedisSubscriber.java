package com.steepcliff.thinkboom.webSocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.steepcliff.thinkboom.brainWriting.dto.BwMessageResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RedisSubscriber {

    private final ObjectMapper objectMapper;
    private final SimpMessageSendingOperations messagingTemplate;

    @Autowired
    public RedisSubscriber(ObjectMapper objectMapper, SimpMessageSendingOperations messagingTemplate) {
        this.objectMapper = objectMapper;
        this.messagingTemplate = messagingTemplate;
    }

    public void BwSendMessage(String publishMessage) {
        try {
            BwMessageResponseDto bwMessageResponseDto = objectMapper.readValue(publishMessage, BwMessageResponseDto.class);

            messagingTemplate.convertAndSend("/sub/api/brainWriting/rooms/" + bwMessageResponseDto.getRoomId(), bwMessageResponseDto);
        } catch (Exception e) {
            log.error("Exception {}", e);
        }
    }
    // SixHat 보낼 메시지 메서드
//    public void ShSendMessage(String publishMessage) {
//        try {
//            BwMessageResponseDto bwMessageResponseDto = objectMapper.readValue(publishMessage, BwMessageResponseDto.class);
//
//            messagingTemplate.convertAndSend("/sub/api/brainWriting/room/"+bwMessageResponseDto.getRoomId(), bwMessageResponseDto);
//        } catch (Exception e) {
//            log.error("Exception {}", e);
//        }
//    }
}
