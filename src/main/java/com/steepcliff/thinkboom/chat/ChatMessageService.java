package com.steepcliff.thinkboom.chat;

import com.steepcliff.thinkboom.brainWriting.dto.BwMessageResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatMessageService {

    private final ChannelTopic channelTopic;
    private final RedisTemplate redisTemplate;


    // destination 정보에서 roomId 추출
    public String getRoomId(String destination) {
        int lastIndex = destination.lastIndexOf('/');
        if (lastIndex != -1)
            return destination.substring(lastIndex + 1);
        else
            return "";
    }

    public void EnterChatMessage(EnterMessageResponseDto enterMessageResponseDto) {
        if(EnterMessageResponseDto.MessageType.ENTER.equals(enterMessageResponseDto.getType())) {

            enterMessageResponseDto.setMessage(enterMessageResponseDto.getSender() + "님이 방에 입장했습니다.");
            enterMessageResponseDto.setSender("[알림]");

            log.info("ENTER 데이터 {}", enterMessageResponseDto.getMessage());
        }
        else if (EnterMessageResponseDto.MessageType.QUIT.equals(enterMessageResponseDto.getType())) {

            enterMessageResponseDto.setMessage(enterMessageResponseDto.getSender() + "님이 방에서 나갔습니다.");
            enterMessageResponseDto.setSender("[알림]");

            log.info("QUIT 데이터 {}", enterMessageResponseDto.getMessage());
        }

        redisTemplate.convertAndSend(channelTopic.getTopic(), enterMessageResponseDto);
    }

//    public void SendChatMessage(BwMessageResponseDto bwMessageResponseDto) {
//        log.info("SendChatMessage 시작");
//        redisTemplate.convertAndSend(channelTopic.getTopic(), bwMessageResponseDto);
//    }
//
//    public void SendChatMessage2(BwMessageResponseDto bwMessageResponseDto) {
//        log.info("SendChatMessage2 시작");
//        redisTemplate.convertAndSend(channelTopic.getTopic(), bwMessageResponseDto);
//    }
}
