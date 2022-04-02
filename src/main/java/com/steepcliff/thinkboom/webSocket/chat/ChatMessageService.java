package com.steepcliff.thinkboom.webSocket.chat;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ChatMessageService {

    private final PatternTopic bwPatternTopic;
    private final PatternTopic shPatternTopic;
    private final RedisTemplate redisTemplate;

    public ChatMessageService(PatternTopic bwPatternTopic, PatternTopic shPatternTopic, RedisTemplate redisTemplate) {
        this.bwPatternTopic = bwPatternTopic;
        this.shPatternTopic = shPatternTopic;
        this.redisTemplate = redisTemplate;
    }


    // destination 정보에서 roomId 추출
    public String getRoomId(String destination) {
        int lastIndex = destination.lastIndexOf('/');
        if (lastIndex != -1)
            return destination.substring(lastIndex + 1);
        else
            return "";
    }

    public void EnterQuitChatMessage(EnterQuitMessageResponseDto enterMessageResponseDto) {
        if(EnterQuitMessageResponseDto.MessageType.ENTER.equals(enterMessageResponseDto.getType())) {

            enterMessageResponseDto.setMessage("[알림] "+ enterMessageResponseDto.getSender() + "님이 방에 입장했습니다.");

            log.info("ENTER 데이터 {}", enterMessageResponseDto.getMessage());
            log.info("ENTER 데이터 {}", enterMessageResponseDto.getTotalUser());
            log.info("ENTER 데이터 {}", enterMessageResponseDto.getCurrentUser());
        }
        else if (EnterQuitMessageResponseDto.MessageType.QUIT.equals(enterMessageResponseDto.getType())) {

            enterMessageResponseDto.setMessage("[알림] " + enterMessageResponseDto.getSender() + "님이 방에서 나갔습니다.");

            log.info("QUIT 데이터 {}", enterMessageResponseDto.getMessage());
        }
        log.info("ChatMessageService userList {}", enterMessageResponseDto.getUserList());
        redisTemplate.convertAndSend(bwPatternTopic.getTopic(), enterMessageResponseDto);
        redisTemplate.convertAndSend(shPatternTopic.getTopic(), enterMessageResponseDto);
    }
}
