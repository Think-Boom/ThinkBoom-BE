package com.sparta.sixhat.dto;

import com.sparta.sixhat.model.ChatMessage;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ChatMessageRequestDto {

    private ChatMessage.MessageType type;
    private String roomId;
    private Long senderId;
    private String sender;
    private String message;
    private String hat;

    @Builder
    public ChatMessageRequestDto(ChatMessage.MessageType type, String roomId, Long senderId, String sender, String message,String hat) {
        this.type = type;
        this.roomId = roomId;
        this.senderId = senderId;
        this.sender = sender;
        this.message = message;
        this.hat = hat;
    }

    @Builder
    public ChatMessageRequestDto(ChatMessage.MessageType type, String senderId, String sender, String message,String hat) {
        this.type = type;
        this.roomId = roomId;
        this.senderId = null;
        this.sender = sender;
        this.message = message;
        this.hat = hat;
    }

}
