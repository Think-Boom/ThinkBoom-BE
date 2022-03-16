package com.sparta.sixhat.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage {

    public enum MessageType {
        ENTER, TALK, QUIT, HAT
    }

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private MessageType type;

    @Column
    private String hat;

    @Column
    private String message;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "room_id")
    private ChatRoom room;

//    @Column
//    private String sender;
//

    @Column
    private String createdAt;



    @Builder
    public ChatMessage(MessageType type, Member member, ChatRoom room, String message, String hat) {
        this.type = type;
        this.member = member;
        this.room = room;
        this.message = message;
        this.hat = hat;

    }


//    @Builder
//    public ChatMessage(MessageType type, String roomId, Long memberId, String sender, String message, String createdAt) {
//        this.type = type;
//        this.roomId = roomId;
//        this.member = null;
//        this.memberId = memberId;
//        this.sender = sender;
//        this.message = message;
//        this.createdAt = createdAt;
//    }

//    @Builder
//    public ChatMessage(ChatMessageRequestDto chatMessageRequestDto) {
//        this.type = chatMessageRequestDto.getType();
//        this.roomId = chatMessageRequestDto.getRoomId();
//        this.member = null;
//        this.memberId = chatMessageRequestDto.getMemberId();
//        this.sender = chatMessageRequestDto.getSender();
//        this.message = chatMessageRequestDto.getMessage();
//        this.createdAt = chatMessageRequestDto.getCreatedAt();
//    }
//
//    @Builder
//    public ChatMessage(ChatMessageRequestDto chatMessageRequestDto, MemberService memberService) {
//        this.type = chatMessageRequestDto.getType();
//        this.roomId = chatMessageRequestDto.getRoomId();
//        this.member = memberService.findById(chatMessageRequestDto.getMemberId());
//        this.memberId = chatMessageRequestDto.getMemberId();
//        this.sender = chatMessageRequestDto.getSender();
//        this.message = chatMessageRequestDto.getMessage();
//        this.createdAt = chatMessageRequestDto.getCreatedAt();
//    }
}