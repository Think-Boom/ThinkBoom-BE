package com.steepcliff.thinkboom.webSocket.chat;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class EnterQuitMessageResponseDto {

    public enum MessageType {
        ENTER, TALK, QUIT
    }

    private MessageType type;

    private String roomId;

    private Long senderId;

    private String sender;

    private String message;

    private String createdAt;

    private Integer totalUser;

    private Integer currentUser;

    private String subject;

    private List<UserListItem> userList;

    @Builder
    public EnterQuitMessageResponseDto(MessageType type, String roomId, Long senderId, String sender, String message, String createdAt, Integer totalUser, Integer currentUser, String subject, List<UserListItem> userList) {
        this.type = type;
        this.roomId = roomId;
        this.senderId = senderId;
        this.sender = sender;
        this.message = message;
        this.createdAt = createdAt;
        this.totalUser = totalUser;
        this.currentUser = currentUser;
        this.subject = subject;
        this.userList = userList;
    }
}
