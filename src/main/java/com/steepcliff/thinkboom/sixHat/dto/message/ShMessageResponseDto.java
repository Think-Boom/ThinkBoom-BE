package com.steepcliff.thinkboom.sixHat.dto.message;

import com.steepcliff.thinkboom.sixHat.domain.ShChatMessage;
import com.steepcliff.thinkboom.webSocket.chat.UserListItem;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ShMessageResponseDto {

    private ShChatMessage.MessageType type;

    private String roomId;

    private Long senderId;

    private String sender;

    private String hat;

    private String subject;

    private String message;

    private String createdAt;

    private Integer totalUser;

    private Integer currentUser;

    private Integer currentPage;

    private List<ShRandomHatItem> randomHat;

    private List<UserListItem> userList;
}
