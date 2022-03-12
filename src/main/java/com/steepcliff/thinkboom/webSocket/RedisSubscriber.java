package com.steepcliff.thinkboom.webSocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

//@Slf4j
//@Service
//public class RedisSubscriber {
//
//    private final ObjectMapper objectMapper;
//    private final SimpMessageSendingOperations messagingTemplate;
//
//    @Autowired
//    public RedisSubscriber(ObjectMapper objectMapper, SimpMessageSendingOperations messagingTemplate) {
//        this.objectMapper = objectMapper;
//        this.messagingTemplate = messagingTemplate;
//    }
//
//    public void sendMessage(String publishMessage) {
//        log.info("sendMessage에 들어온 데이터 publishMessage={}", publishMessage);
//        try {
//            // ChatMessage 객채로 맵핑
////            ChatMessage chatMessage = objectMapper.readValue(publishMessage, ChatMessage.class);
//            BwChatMessageResponseDto bwChatMessageResponseDto = objectMapper.readValue(publishMessage, BwChatMessageResponseDto.class);
//            log.info("bwChatMessageResponseDto {}", bwChatMessageResponseDto.getMessage());
//            // 채팅방을 구독한 클라이언트에게 메시지 발송
//            messagingTemplate.convertAndSend("/sub/api/chat/rooms/" + bwChatMessageResponseDto.getRoomId(), bwChatMessageResponseDto);
//        } catch (Exception e) {
//            log.error("Exception {}", e);
//        }
//    }//chat response를 매핑해야함
//}