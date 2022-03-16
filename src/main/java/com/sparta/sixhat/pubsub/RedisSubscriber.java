package com.sparta.sixhat.pubsub;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.sixhat.dto.ChatMessageResponseDto;
import com.sparta.sixhat.repository.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;


@Slf4j
//@RequiredArgsConstructor
@Service
public class RedisSubscriber {

    private final ObjectMapper objectMapper;
    private final SimpMessageSendingOperations messagingTemplate;
    //private final ChatMessageRepository chatMessageRepository;

//    @Autowired
//    public RedisSubscriber(ObjectMapper objectMapper, SimpMessageSendingOperations messagingTemplate, ChatMessageRepository chatMessageRepository) {
//        this.objectMapper = objectMapper;
//        this.messagingTemplate = messagingTemplate;
//        this.chatMessageRepository = chatMessageRepository;
//    }

    @Autowired
    public RedisSubscriber(ObjectMapper objectMapper, SimpMessageSendingOperations messagingTemplate) {
        this.objectMapper = objectMapper;
        this.messagingTemplate = messagingTemplate;

    }

    //convetAndSend 로 데이터를 보내면 여기서 잡아서 보낸다.
    // Redis 에서 메시지가 발행(publish)되면 대기하고 있던 Redis Subscriber 가 해당 메시지를 받아 처리한다.
    public void sendMessage(String publishMessage) {
        log.info("데이터 send publishMessage={}", publishMessage);
        try {
            // ChatMessage 객채로 맵핑
            //ChatMessage chatMessage = objectMapper.readValue(publishMessage, ChatMessage.class);
            ChatMessageResponseDto chatMessageResponseDto = objectMapper.readValue(publishMessage, ChatMessageResponseDto.class);
            log.info("ChatMessageResponseDto {}", chatMessageResponseDto.getMessage());
            // 채팅방을 구독한 클라이언트에게 메시지 발송
            //messagingTemplate.convertAndSend("/sub/api/chat/rooms/" + chatMessageResponseDto.getRoomId(), chatMessageResponseDto);
            messagingTemplate.convertAndSend("/sub/api/sixHat/rooms/" + chatMessageResponseDto.getRoomId(), chatMessageResponseDto);
        } catch (Exception e) {
            log.error("Exception {}", e);
        }
    }


}
