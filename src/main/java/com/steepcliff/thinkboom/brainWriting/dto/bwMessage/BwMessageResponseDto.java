package com.steepcliff.thinkboom.brainWriting.dto.bwMessage;

import com.steepcliff.thinkboom.brainWriting.domain.BwChatMessage;
import com.steepcliff.thinkboom.brainWriting.domain.BwRoom;
import com.steepcliff.thinkboom.user.User;
import com.steepcliff.thinkboom.webSocket.chat.UserListItem;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class BwMessageResponseDto {

    private BwChatMessage.MessageType type;

    private String roomId;

    private Long senderId;

    private String sender;

    private String subject;

    private String message;

    private String createdAt;

    private Integer totalUser;

    private Integer currentUser;

    private Integer currentPage;

    private List<UserListItem> userList;

    @Builder
    public BwMessageResponseDto(BwChatMessage.MessageType type, String roomId, Long senderId, String sender, String message, String createdAt) {
        this.type = type;
        this.roomId = roomId;
        this.senderId = senderId;
        this.sender = sender;
        this.message = message;
        this.createdAt = createdAt;
    }
}
