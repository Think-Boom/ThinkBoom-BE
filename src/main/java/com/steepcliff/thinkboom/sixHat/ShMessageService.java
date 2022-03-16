package com.steepcliff.thinkboom.sixHat;

import com.steepcliff.thinkboom.brainWriting.domain.BwRoom;
import com.steepcliff.thinkboom.chat.EnterMessageResponseDto;
import com.steepcliff.thinkboom.sixHat.domain.ShChatMessage;
import com.steepcliff.thinkboom.sixHat.domain.ShRoom;
import com.steepcliff.thinkboom.sixHat.dto.ShMessageResponseDto;
import com.steepcliff.thinkboom.sixHat.repository.ShMessageRepository;
import com.steepcliff.thinkboom.sixHat.repository.ShRoomRepository;
import com.steepcliff.thinkboom.user.User;
import com.steepcliff.thinkboom.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ShMessageService {

    private final ShMessageRepository shMessageRepository;
    private final ChannelTopic channelTopic;
    private final RedisTemplate redisTemplate;
    private final UserService userService;
    private final ShRoomRepository shRoomRepository;

    public ShMessageService(ShMessageRepository shMessageRepository, ChannelTopic channelTopic, RedisTemplate redisTemplate, UserService userService, ShRoomRepository shRoomRepository) {
        this.shMessageRepository = shMessageRepository;
        this.channelTopic = channelTopic;
        this.redisTemplate = redisTemplate;
        this.userService = userService;
        this.shRoomRepository = shRoomRepository;
    }

    // six hat 메시지 보내기
    public void SendShChatMessage(ShMessageResponseDto shMessageResponseDto) {
        log.info("SendShChatMessage 시작");
        if(ShChatMessage.MessageType.ENTER.equals(shMessageResponseDto.getType())) {
            String message = shMessageResponseDto.getSender() + "의 모자색이 " + shMessageResponseDto.getHat() + "으로 변경되었습니다.";
            shMessageResponseDto.setMessage(message);
        }
        redisTemplate.convertAndSend(channelTopic.getTopic(), shMessageResponseDto);
    }

    // 메시지 저장.
    public void save(ShMessageResponseDto shMessageResponseDto) {
        User user = userService.findById(shMessageResponseDto.getSenderId());

        ShRoom shRoom = shRoomRepository.findById(Long.valueOf(shMessageResponseDto.getRoomId())).orElseThrow(
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

}
