package com.sparta.sixhat.dto;

import com.sparta.sixhat.model.ChatRoom;
import com.sparta.sixhat.model.Member;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter@Setter
public class ChatRoomResponseDto {

    private Long id;
    private Long memberCount;
    private String title;
    //private Member member;
    private Long timer;

//    public ChatRoomResponseDto(ChatRoom chatRoom, Member member) {
//        this.id = chatRoom.getId();
//        this.chatRoomTitle = chatRoom.getTitle();
//        this.member = member;
//    }

    public ChatRoomResponseDto(Long id, String title, Long memberCount, Long timer) {
        this.id = id;
        this.title = title;
        this.memberCount = memberCount;
        this.timer = timer;
    }
}
