package com.sparta.sixhat.service;

import com.sparta.sixhat.dto.ChatMessageRequestDto;
import com.sparta.sixhat.dto.ChatMessageResponseDto;
import com.sparta.sixhat.model.ChatMessage;
import com.sparta.sixhat.model.ChatRoom;
import com.sparta.sixhat.model.Member;
import com.sparta.sixhat.service.MemberService;
import com.sparta.sixhat.repository.ChatMessageRepository;
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
    private final ChatMessageRepository chatMessageRepository;
    private final MemberService memberService;
    private final ChatRoomService chatRoomService;

    // destination 정보에서 roomId 추출
    public String getRoomId(String destination) {
        int lastIndex = destination.lastIndexOf('/');
        if (lastIndex != -1)
            return destination.substring(lastIndex + 1);
        else
            return "";
    }

//    // 채팅방에 메시지 발송
//    public ChatMessageResponseDto sendChatMessage(ChatMessageResponseDto chatMessageResponseDto) {
//        if (ChatMessage.MessageType.ENTER.equals(chatMessageResponseDto.getType())) {
//            chatMessageResponseDto.setSender("[알림]");
//            chatMessageResponseDto.setMessage(chatMessageResponseDto.getSender() + "님이 방에 입장했습니다.");
//            log.info("ENTER 데이터 ");
//        }else if (ChatMessage.MessageType.HAT.equals(chatMessageResponseDto.getType())) {
//            chatMessageResponseDto.setSender("[알림]");
//            chatMessageResponseDto.setMessage(chatMessageResponseDto.getSender() +chatMessageResponseDto.getSenderId() + "님이"+chatMessageResponseDto.getHat()+"색 모자를 골랐습니다.");
//            log.info("HAT 데이터 ");
//        }else if (ChatMessage.MessageType.QUIT.equals(chatMessageResponseDto.getType())) {
//            chatMessageResponseDto.setSender("[알림]");
//            chatMessageResponseDto.setMessage(chatMessageResponseDto.getSender() +chatMessageResponseDto.getSenderId() + "님이 방에서 나갔습니다.");
//            log.info("QUIT 데이터 ");
//        }
//        //topic은 chatroom이다
//        //conver
//        log.info(chatMessageResponseDto.getMessage());
//        redisTemplate.convertAndSend(channelTopic.getTopic(), chatMessageResponseDto);
//
//        return chatMessageResponseDto;
//    }
    // 채팅방에 메시지 발송
    public void sendChatMessage(ChatMessageResponseDto chatMessageResponseDto) {
        if (ChatMessage.MessageType.ENTER.equals(chatMessageResponseDto.getType())) {
            //chatMessageResponseDto.setSender("[알림]");
            chatMessageResponseDto.setSenderId(chatMessageResponseDto.getSenderId());
            chatMessageResponseDto.setMessage("[알림]"+chatMessageResponseDto.getSender()+chatMessageResponseDto.getSenderId() + "님이 방에 입장했습니다.");
            log.info("ENTER 데이터 ");
//        }else if (ChatMessage.MessageType.TALK.equals(chatMessageResponseDto.getType())) {
//            chatMessageResponseDto.setSender("[알림]");
//            chatMessageResponseDto.setMessage();
//            log.info("채팅 send");
        }else if (ChatMessage.MessageType.HAT.equals(chatMessageResponseDto.getType())) {
            chatMessageResponseDto.setSender("[알림]");
            chatMessageResponseDto.setMessage(chatMessageResponseDto.getSender() +chatMessageResponseDto.getSenderId() + "님이"+chatMessageResponseDto.getHat()+"색 모자를 골랐습니다.");
            log.info("HAT 데이터 ");
        }else if (ChatMessage.MessageType.QUIT.equals(chatMessageResponseDto.getType())) {
            chatMessageResponseDto.setSender("[알림]");
            chatMessageResponseDto.setMessage(chatMessageResponseDto.getSender() +chatMessageResponseDto.getSenderId() + "님이 방에서 나갔습니다.");
            log.info("QUIT 데이터 ");
        }
        //topic은 chatroom이다
        //conver
        log.info(chatMessageResponseDto.getMessage());
        redisTemplate.convertAndSend(channelTopic.getTopic(), chatMessageResponseDto);


    }



    public void save(ChatMessageResponseDto chatMessageResponseDto) {
        log.info("save 시작 {}", chatMessageResponseDto.getSenderId());
        Member member = memberService.findById(chatMessageResponseDto.getSenderId());
        log.info("member 찾기 완료 {}", member.getNickname());
        ChatRoom room = chatRoomService.getEachChatRoom(Long.parseLong(chatMessageResponseDto.getRoomId()));
        log.info("방 찾기 완료 {}", room.getId());
        ChatMessage chatmessage = new ChatMessage();

        chatmessage.setType(chatMessageResponseDto.getType());
        chatmessage.setMessage(chatMessageResponseDto.getMessage());
        chatmessage.setMember(member);
        chatmessage.setRoom(room);
        chatmessage.setCreatedAt(chatMessageResponseDto.getCreatedAt());

        log.info(String.valueOf(chatmessage.getType()));
        log.info(String.valueOf(chatmessage.getMessage()));
        log.info(String.valueOf(chatmessage.getCreatedAt()));

        log.info("저장 직전");
        chatMessageRepository.save(chatmessage);
        log.info("저장 직후");
    }

    public void enternickname(ChatMessageRequestDto ChatMessageRequestDto) {
        String sender = ChatMessageRequestDto.getSender();
        log.info(sender);


    }
}
