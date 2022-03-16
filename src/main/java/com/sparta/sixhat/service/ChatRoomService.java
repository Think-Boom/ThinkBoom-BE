package com.sparta.sixhat.service;

import com.sparta.sixhat.dto.ChatRoomRequestDto;
import com.sparta.sixhat.dto.ChatRoomResponseDto;
import com.sparta.sixhat.model.ChatRoom;
import com.sparta.sixhat.repository.ChatRoomRepository;
import com.sparta.sixhat.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

@Service
public class ChatRoomService {

    //레디스 저장소 사용
    //key hashKey value 구조
    @Resource(name = "redisTemplate")
    private HashOperations<String, String, String> hashOpsEnterInfo;

    private final ChatRoomRepository chatRoomRepository;
    private final MemberService memberService;
    private final MemberRepository memberRepository;


    @Autowired
    public ChatRoomService(ChatRoomRepository chatRoomRepository, MemberService memberService, MemberRepository memberRepository) {
        this.chatRoomRepository = chatRoomRepository;
        this.memberService = memberService;
        this.memberRepository = memberRepository;
    }

    // 채팅룸에 입장한 클라이언트의 sessionId 와 채팅룸 id 를 맵핑한 정보 저장
    public static final String ENTER_INFO = "ENTER_INFO"; // 채팅룸에 입장한 클라이언트의 sessionId 와 채팅룸 id 를 맵핑한 정보

    // 채팅방 제목, 수 ,시간 받고 세이브/리턴값으로 방id 인원수, 시간 제목
    public ChatRoomResponseDto createChatRoom(ChatRoomRequestDto requestDto) {
        ChatRoom chatRoom = new ChatRoom(requestDto.getTitle(),requestDto.getMemberCount(), requestDto.getTimer());
        chatRoomRepository.save(chatRoom);

        return new ChatRoomResponseDto(chatRoom.getId(),chatRoom.getTitle(), chatRoom.getMemberCount(), chatRoom.getTimer());
    }

    // 유저가 입장한 채팅방 ID 와 유저 세션 ID 맵핑 정보 저장
    //Enter라는 곳에 sessionId와 roomId를 맵핑시켜놓음
    public void setMemberEnterInfo(String sessionId, String roomId) {
        hashOpsEnterInfo.put(ENTER_INFO, sessionId, roomId);
    }

    // 개별 채팅방 조회
    public ChatRoom getEachChatRoom(Long id) {
        return chatRoomRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("찾는 채팅방이 존재하지 않습니다.")
        );
    }

    // 유저 세션으로 입장해 있는 채팅방 ID 조회
    public String getMemberEnterRoomId(String sessionId) {

        return hashOpsEnterInfo.get(ENTER_INFO, sessionId);
    }

    // 유저 세션정보와 맵핑된 채팅방 ID 삭제
    //한 유저는 하나의 룸 아이디에만 맵핑
    // 실시간으로 보는 방은 하나이기 떄문이다.
    public void removeMemberEnterInfo(String sessionId) {
        hashOpsEnterInfo.delete(ENTER_INFO, sessionId);
    }

    // test 용
    public ChatRoom getRoom(Long id) {
        Optional<ChatRoom> chatRoom =  chatRoomRepository.findById(id);
        return chatRoom.orElseThrow(NullPointerException::new);
    }
}
