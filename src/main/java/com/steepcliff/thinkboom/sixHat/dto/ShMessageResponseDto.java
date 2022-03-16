package com.steepcliff.thinkboom.sixHat.dto;

import com.steepcliff.thinkboom.sixHat.domain.ShChatMessage;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShMessageResponseDto {

    private ShChatMessage.MessageType type;

    private String roomId;

    private Long senderId;

    private String sender;

    private String hat;

    private String message;

    private String createdAt;
}
