package com.steepcliff.thinkboom.webSocket;

import com.steepcliff.thinkboom.brainWriting.domain.BwRoom;
import com.steepcliff.thinkboom.brainWriting.service.BwService;
import com.steepcliff.thinkboom.webSocket.chat.EnterQuitMessageResponseDto;
import com.steepcliff.thinkboom.webSocket.chat.ChatRoomService;
import com.steepcliff.thinkboom.webSocket.chat.ChatMessageService;
import com.steepcliff.thinkboom.sixHat.ShService;
import com.steepcliff.thinkboom.sixHat.domain.ShRoom;
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
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
public class StompHandler implements ChannelInterceptor {

    private final ChatMessageService chatMessageService;
    private final ChatRoomService chatRoomService;
    private final UserRepository userRepository;
    private final BwService bwService;
    private final ShService shService;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {

        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        if (StompCommand.CONNECT == accessor.getCommand()) {
            log.info("CONNECT");

        }

        else if (StompCommand.SUBSCRIBE == accessor.getCommand()) {
            log.info("SUBSCRIBE");

            String category = accessor.getFirstNativeHeader("category");

            String destination = Optional.ofNullable((String) message.getHeaders().get("simpDestination")).orElse("InvalidRoomId");

            String roomId = chatMessageService.getRoomId(destination);

            String sessionId = (String) message.getHeaders().get("simpSessionId");

            chatRoomService.setUserEnterInfo(sessionId, roomId);

            String senderId = Optional.ofNullable(accessor.getFirstNativeHeader("senderId")).orElse("UnknownUser");

            User user = userRepository.findById(Long.parseLong(senderId)).orElseThrow(
                    NullPointerException::new
            );

            if(category.equals("BW")) {
                bwService.plusUserCount(roomId);
                BwRoom room = bwService.findBwRoom(roomId);

                chatMessageService.EnterQuitChatMessage(EnterQuitMessageResponseDto
                        .builder()
                        .type(EnterQuitMessageResponseDto.MessageType.ENTER)
                        .roomId(roomId)
                        .sender(user.getNickname())
                        .totalUser(room.getHeadCount())
                        .currentUser(room.getCurrentUsers())
                        .build());
                log.info("SUBSCRIBED {}, {}", user.getNickname(), roomId);

            } else if(category.equals("SH")) {
                shService.plusUserCount(roomId);
                ShRoom room = shService.findShRoom(roomId);

                chatMessageService.EnterQuitChatMessage(EnterQuitMessageResponseDto
                        .builder()
                        .type(EnterQuitMessageResponseDto.MessageType.ENTER)
                        .roomId(roomId)
                        .sender(user.getNickname())
                        .totalUser(room.getHeadCount())
                        .currentUser(room.getCurrentUsers())
                        .build());
                log.info("SUBSCRIBED {}, {}", user.getNickname(), roomId);
            }

        }

        else if (StompCommand.DISCONNECT == accessor.getCommand()) {
            String sessionId = (String) message.getHeaders().get("simpSessionId");

            String destination = Optional.ofNullable((String) message.getHeaders().get("simpDestination")).orElse("InvalidRoomId");

            String roomId = chatMessageService.getRoomId(destination);

            String category = accessor.getFirstNativeHeader("category");



            chatRoomService.removeUserEnterInfo(sessionId);

            String senderId = Optional.ofNullable(accessor.getFirstNativeHeader("senderId")).orElse("UnknownUser");
            User user = userRepository.findById(Long.parseLong(senderId)).orElseThrow(
                    NullPointerException::new
            );

            if(category.equals("BW")) {

                bwService.minusUserCount(roomId);
                BwRoom room = bwService.findBwRoom(roomId);
                chatMessageService.EnterQuitChatMessage(EnterQuitMessageResponseDto
                        .builder()
                        .type(EnterQuitMessageResponseDto.MessageType.QUIT)
                        .roomId(roomId)
                        .sender(user.getNickname())
                        .totalUser(room.getHeadCount())
                        .currentUser(room.getCurrentUsers())
                        .build());

            } else if(category.equals("SH")) {

                shService.minusUserCount(roomId);
                ShRoom room = shService.findShRoom(roomId);
                chatMessageService.EnterQuitChatMessage(EnterQuitMessageResponseDto
                        .builder()
                        .type(EnterQuitMessageResponseDto.MessageType.QUIT)
                        .roomId(roomId)
                        .sender(user.getNickname())
                        .totalUser(room.getHeadCount())
                        .currentUser(room.getCurrentUsers())
                        .build());
            }
            log.info("DISCONNECT");
        }

        return message;
    }



}
