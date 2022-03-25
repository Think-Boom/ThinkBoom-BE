package com.steepcliff.thinkboom.sixHat.service;

import com.steepcliff.thinkboom.sixHat.domain.ShChatMessage;
import com.steepcliff.thinkboom.sixHat.domain.ShRoom;
import com.steepcliff.thinkboom.sixHat.dto.message.ShMessageResponseDto;
import com.steepcliff.thinkboom.sixHat.dto.ShResultMessageItem;
import com.steepcliff.thinkboom.sixHat.dto.ShResultResponseDto;
import com.steepcliff.thinkboom.sixHat.repository.ShMessageRepository;
import com.steepcliff.thinkboom.sixHat.repository.ShRoomRepository;
import com.steepcliff.thinkboom.user.User;
import com.steepcliff.thinkboom.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class ShMessageService {

    private final ShMessageRepository shMessageRepository;
    private final ChannelTopic channelTopic;
    private final RedisTemplate redisTemplate;
    private final UserService userService;
    private final ShRoomRepository shRoomRepository;
    private final ShService shService;

    public ShMessageService(ShMessageRepository shMessageRepository, ChannelTopic channelTopic, RedisTemplate redisTemplate, UserService userService, ShRoomRepository shRoomRepository, ShService shService) {
        this.shMessageRepository = shMessageRepository;
        this.channelTopic = channelTopic;
        this.redisTemplate = redisTemplate;
        this.userService = userService;
        this.shRoomRepository = shRoomRepository;
        this.shService = shService;
    }

    // six hat 메시지 보내기
    public void SendShChatMessage(ShMessageResponseDto shMessageResponseDto) {
        log.info("SendShChatMessage 시작");
        if(ShChatMessage.MessageType.HAT.equals(shMessageResponseDto.getType())) {
            String message = shMessageResponseDto.getSender() + "의 모자색이 " + shMessageResponseDto.getHat() + "으로 변경되었습니다.";
            shMessageResponseDto.setMessage(message);
        } else if(ShChatMessage.MessageType.SUBJECT.equals(shMessageResponseDto.getType())) {
            shService.saveSubject(shMessageResponseDto.getRoomId(), shMessageResponseDto.getSubject());
            shMessageResponseDto.setMessage("[알림]주제가" + shMessageResponseDto.getSubject() + "로 변경되었습니다.");

        } else if(ShChatMessage.MessageType.RANDOMHAT.equals(shMessageResponseDto.getType())) {
            shMessageResponseDto.setMessage("[알림] 모자가 랜덤으로 설정되었습니다.");
        }
        redisTemplate.convertAndSend(channelTopic.getTopic(), shMessageResponseDto);
    }

    // 메시지 저장.
    public void save(ShMessageResponseDto shMessageResponseDto) {
        User user = userService.findById(shMessageResponseDto.getSenderId());

        ShRoom shRoom = shRoomRepository.findById(shMessageResponseDto.getRoomId()).orElseThrow(
                () -> new NullPointerException()
        );

        ShChatMessage message = new ShChatMessage();

        message.setType(shMessageResponseDto.getType());
        message.setMessage(shMessageResponseDto.getMessage());
        message.setCreatedAt(shMessageResponseDto.getCreatedAt());
        message.setHat(shMessageResponseDto.getHat());
        message.setUser(user);
        message.setShRoom(shRoom);
        message.setCreatedAt(shMessageResponseDto.getCreatedAt());

        shMessageRepository.save(message);

    }

    // 결과 데이터 얻기
    // 채팅 목록 반환하기
    public ShResultResponseDto getResult(String shRoomId) {
        ShRoom shRoom = shRoomRepository.findById(shRoomId).orElseThrow(
                () -> new NullPointerException("해당 방이 존재하지 않습니다.")
        );

        ShResultResponseDto shResultResponseDto = new ShResultResponseDto();
        shResultResponseDto.setSubject(shRoom.getSubject());

        List<ShChatMessage> shChatMessageList = shMessageRepository.findAllByShRoom(shRoom);

        List<ShResultMessageItem> shResultMessageItemList = new ArrayList<>();

        for(ShChatMessage shChatMessage:shChatMessageList) {
            ShResultMessageItem shResultMessageItem = new ShResultMessageItem();

            shResultMessageItem.setSenderId(shChatMessage.getUser().getId());
            shResultMessageItem.setSender(shChatMessage.getUser().getNickname());
            shResultMessageItem.setHat(shChatMessage.getHat());
            shResultMessageItem.setMessage(shChatMessage.getMessage());

            shResultMessageItemList.add(shResultMessageItem);
        }
        shResultResponseDto.setMessageList(shResultMessageItemList);

        return shResultResponseDto;
    }
}
