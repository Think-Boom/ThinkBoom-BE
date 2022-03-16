package com.sparta.sixhat.handler;


import com.sparta.sixhat.dto.ChatMessageResponseDto;
import com.sparta.sixhat.model.ChatMessage;
import com.sparta.sixhat.model.Member;
import com.sparta.sixhat.repository.MemberRepository;
import com.sparta.sixhat.service.ChatMessageService;
import com.sparta.sixhat.service.ChatRoomService;
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

    private final ChatRoomService chatRoomService;
    private final ChatMessageService chatMessageService;
    private final MemberRepository memberRepository;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {

        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        if (StompCommand.CONNECT == accessor.getCommand()) { // websocket 연결요청

            log.info("CONNECT");

            String senderId = Optional.ofNullable(accessor.getFirstNativeHeader("senderId")).orElse("UnknownUser");
            log.info("sessionId={}", senderId);

        }

        else if(StompCommand.SUBSCRIBE == accessor.getCommand()) {
            log.info(" subscribe 시작");

            String destination = Optional.ofNullable((String) message.getHeaders().get("simpDestination")).orElse("InvalidRoomId");
            log.info(destination);
            String roomId = chatMessageService.getRoomId(destination);

            String sessionId = (String) message.getHeaders().get("simpSessionId");
            log.info(sessionId);
            chatRoomService.setMemberEnterInfo(sessionId, roomId);

            String senderId = Optional.ofNullable(accessor.getFirstNativeHeader("senderId")).orElse("UnknownUser");
            log.info("senderId={}", senderId);
            Member member = memberRepository.findById(Long.parseLong(senderId)).orElseThrow(
                    NullPointerException::new
            );

//            String senderId = Optional.ofNullable(accessor.getFirstNativeHeader("sessionId")).orElse("UnknownMember");
//
//            Member member = memberRepository.findById(Long.parseLong(senderId)).orElseThrow(
//                    NullPointerException::new
//            );

            chatMessageService.sendChatMessage(ChatMessageResponseDto
                    .builder()
                    .type(ChatMessage.MessageType.ENTER)
                    .roomId(roomId)
                    .sender(member.getNickname())
                    .build());
            log.info("SUBSCRIBED {}, {}", member.getNickname(), roomId);
        }

        else if (StompCommand.DISCONNECT == accessor.getCommand()) {
            String sessionId = (String) message.getHeaders().get("simpSessionId");

            String roomId = chatRoomService.getMemberEnterRoomId(sessionId);

            chatRoomService.removeMemberEnterInfo(sessionId);

            log.info("DISCONNECT {}, {}", sessionId, roomId);
        }

        return message;
    }
}
