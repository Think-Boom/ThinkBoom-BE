package com.steepcliff.thinkboom.brainWriting.service;

import com.steepcliff.thinkboom.brainWriting.domain.BwChatMessage;
import com.steepcliff.thinkboom.brainWriting.domain.BwRoom;
import com.steepcliff.thinkboom.brainWriting.dto.bwMessage.BwMessageResponseDto;
import com.steepcliff.thinkboom.brainWriting.repository.BwMessageRepository;
import com.steepcliff.thinkboom.user.User;
import com.steepcliff.thinkboom.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class BwMessageService {

    private final BwMessageRepository bwMessageRepository;
    private final PatternTopic patternTopic;
    private final RedisTemplate redisTemplate;
    private final UserService userService;
    private final BwService bwService;

    public BwMessageService(BwMessageRepository bwMessageRepository, @Qualifier("bwPatternTopic") PatternTopic patternTopic, RedisTemplate redisTemplate, UserService userService, BwService bwService) {
        this.bwMessageRepository = bwMessageRepository;
        this.patternTopic = patternTopic;
        this.redisTemplate = redisTemplate;
        this.userService = userService;
        this.bwService = bwService;
    }

    public void SendBwChatMessage(BwMessageResponseDto bwMessageResponseDto) {
        if(BwChatMessage.MessageType.SUBJECT.equals(bwMessageResponseDto.getType())) {
            bwService.saveSubject(bwMessageResponseDto.getRoomId(), bwMessageResponseDto.getSubject());
            bwMessageResponseDto.setMessage("[알림] 주제가" + bwMessageResponseDto.getSubject() + "로 변경되었습니다.");
        }

        redisTemplate.convertAndSend(patternTopic.getTopic(), bwMessageResponseDto);
    }

    public void save(BwMessageResponseDto bwMessageResponseDto) {

        User user = userService.findById(bwMessageResponseDto.getSenderId());

        BwRoom bwRoom = bwService.findBwRoom(bwMessageResponseDto.getRoomId());

        BwChatMessage message = new BwChatMessage();

        message.setType(bwMessageResponseDto.getType());
        message.setMessage(bwMessageResponseDto.getMessage());
        message.setCreatedAt(bwMessageResponseDto.getCreatedAt());
        message.setUser(user);
        message.setRoom(bwRoom);
        message.setCreatedAt(bwMessageResponseDto.getCreatedAt());

        bwMessageRepository.save(message);
    }
}
