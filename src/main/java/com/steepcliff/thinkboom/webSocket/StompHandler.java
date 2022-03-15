package com.steepcliff.thinkboom.webSocket;

import com.steepcliff.thinkboom.chat.ChatMessageResponseDto;
import com.steepcliff.thinkboom.chat.ChatRoomService;
import com.steepcliff.thinkboom.chat.ChatMessageService;
import com.steepcliff.thinkboom.user.User;
import com.steepcliff.thinkboom.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Component
public class StompHandler implements ChannelInterceptor {

    private final ChatMessageService chatMessageService;
    private final ChatRoomService chatRoomService;
    private final UserRepository userRepository;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {

        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        if (StompCommand.CONNECT == accessor.getCommand()) {
            log.info("CONNECT");
        }

        else if (StompCommand.SUBSCRIBE == accessor.getCommand()) {
            log.info("SUBSCRIBE");

            String destination = Optional.ofNullable((String) message.getHeaders().get("simpDestination")).orElse("InvalidRoomId");

            String roomId = chatMessageService.getRoomId(destination);

            String sessionId = (String) message.getHeaders().get("simpSessionId");

            chatRoomService.setUserEnterInfo(sessionId, roomId);

            String senderId = Optional.ofNullable(accessor.getFirstNativeHeader("senderId")).orElse("UnknownUser");

            User user = userRepository.findById(Long.parseLong(senderId)).orElseThrow(
                    NullPointerException::new
            );

            chatMessageService.EnterChatMessage(ChatMessageResponseDto
                    .builder()
                    .type(ChatMessageResponseDto.MessageType.ENTER)
                    .roomId(roomId)
                    .sender(user.getNickname())
                    .build());
            log.info("SUBSCRIBED {}, {}", user.getNickname(), roomId);
        }

        else if (StompCommand.DISCONNECT == accessor.getCommand()) {
            String sessionId = (String) message.getHeaders().get("simpSessionId");

            chatRoomService.removeUserEnterInfo(sessionId);
            log.info("DISCONNECT");
        }

        return message;
    }



}
