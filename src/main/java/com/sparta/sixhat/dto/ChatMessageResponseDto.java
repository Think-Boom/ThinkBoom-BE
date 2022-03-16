package com.sparta.sixhat.dto;

import com.sparta.sixhat.model.ChatMessage;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@NoArgsConstructor
public class ChatMessageResponseDto {

    private ChatMessage.MessageType type;

    private String roomId;

    private Long senderId;

    private String sender;

    private String message;

    private String createdAt;

    private String hat;

    @Builder
    public ChatMessageResponseDto(ChatMessage.MessageType type, String roomId, Long senderId, String sender, String message, String createdAt,String hat) {
        this.type = type;
        this.roomId = roomId;
        this.senderId = senderId;
        this.sender = sender;
        this.message = message;
        this.createdAt = createdAt;
        this.hat = hat;
    }
}
