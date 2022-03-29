package com.steepcliff.thinkboom.brainWriting.dto.bwMessage;

import com.steepcliff.thinkboom.brainWriting.domain.BwChatMessage;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BwMessageRequestDto {

    private BwChatMessage.MessageType type;
    private String roomId;
    private Long senderId;
    private String sender;
    private String message;

    private String subject;

    private Integer currentPage;

    @Builder
    public BwMessageRequestDto(BwChatMessage.MessageType type, String roomId, Long senderId, String sender, String message) {
        this.type = type;
        this.roomId = roomId;
        this.senderId = senderId;
        this.sender = sender;
        this.message = message;

    }

    @Builder
    public BwMessageRequestDto(BwChatMessage.MessageType type, String senderId, String sender, String message) {
        this.type = type;
        this.roomId = roomId;
        this.senderId = null;
        this.sender = sender;
        this.message = message;
    }
}
