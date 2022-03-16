package com.steepcliff.thinkboom.sixHat.dto;


import com.steepcliff.thinkboom.sixHat.domain.ShChatMessage;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShMessageRequestDto {

    private ShChatMessage.MessageType type;
    private String roomId;
    private Long senderId;
    private String hat;
    private String sender;
    private String message;
}
