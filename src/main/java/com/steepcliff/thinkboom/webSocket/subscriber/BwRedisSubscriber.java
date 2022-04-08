package com.steepcliff.thinkboom.webSocket.subscriber;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.steepcliff.thinkboom.brainWriting.dto.bwMessage.BwMessageResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class BwRedisSubscriber {

    private final ObjectMapper objectMapper;
    private final SimpMessageSendingOperations messagingTemplate;

    public BwRedisSubscriber(ObjectMapper objectMapper, SimpMessageSendingOperations messagingTemplate) {
        this.objectMapper = objectMapper;
        this.messagingTemplate = messagingTemplate;
    }

    public void BwSendMessage(String publishMessage) {
        try {
            log.info("BwSendMessage 시작");
            BwMessageResponseDto bwMessageResponseDto = objectMapper.readValue(publishMessage, BwMessageResponseDto.class);

            messagingTemplate.convertAndSend("/sub/api/brainwriting/rooms/" + bwMessageResponseDto.getRoomId(), bwMessageResponseDto);

        } catch (Exception e) {
            log.error("Exception {}", e);
        }
    }
}
