package com.steepcliff.thinkboom.chat;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ChatMessageResponseDto {

    public enum MessageType {
        ENTER, TALK, QUIT
    }

    private MessageType type;

    private String roomId;

    private Long senderId;

    private String sender;

    private String message;

    private String createdAt;

    @Builder
    public ChatMessageResponseDto(MessageType type, String roomId, Long senderId, String sender, String message, String createdAt) {
        this.type = type;
        this.roomId = roomId;
        this.senderId = senderId;
        this.sender = sender;
        this.message = message;
        this.createdAt = createdAt;
    }
}
