package com.steepcliff.thinkboom.webSocket;

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

//    private final ChatRoomService chatRoomService;
//    private final ChatMessageService chatMessageService;
//    private final UserRepository userRepository;
//
//    @Override
//    public Message<?> preSend(Message<?> message, MessageChannel channel) {
//
//        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
//
//        if (StompCommand.CONNECT == accessor.getCommand()) { // websocket 연결요청
//
//            log.info("CONNECT");
//
//            String senderId = Optional.ofNullable(accessor.getFirstNativeHeader("senderId")).orElse("UnknownUser");
//            log.info("sessionId={}", senderId);
//
//        }
//
//        else if(StompCommand.SUBSCRIBE == accessor.getCommand()) {
//            log.info(" subscibe 시작");
//
//            String destination = Optional.ofNullable((String) message.getHeaders().get("simpDestination")).orElse("InvalidRoomId");
//
//            log.info(destination);
//            String roomId = chatMessageService.getRoomId(destination);
//
//            String sessionId = (String) message.getHeaders().get("simpSessionId");
//            log.info("sessionId={}", sessionId);
//            chatRoomService.setUserEnterInfo(sessionId, roomId);
//
//            String senderId = Optional.ofNullable(accessor.getFirstNativeHeader("senderId")).orElse("UnknownUser");
//            log.info("senderId={}", senderId);
//            User user = userRepository.findById(Long.parseLong(senderId)).orElseThrow(
//                    NullPointerException::new
//            );
//
//            chatMessageService.sendChatMessage(BwChatMessageResponseDto
//                    .builder()
//                    .type(BwChatMessage.MessageType.ENTER)
//                    .roomId(roomId)
//                    .sender(user.getNickname())
//                    .build());
//            log.info("SUBSCRIBED {}, {}", user.getNickname(), roomId);
//        }
//
//        else if (StompCommand.DISCONNECT == accessor.getCommand()) {
//            String sessionId = (String) message.getHeaders().get("simpSessionId");
//
//            String roomId = chatRoomService.getUserEnterRoomId(sessionId);
//
//            chatRoomService.removeUserEnterInfo(sessionId);
//
//            log.info("DISCONNECT {}, {}", sessionId, roomId);
//        }
//
//        return message;
//    }
}
