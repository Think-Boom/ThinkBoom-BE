package com.steepcliff.thinkboom.sixHat.dto.message;


import com.steepcliff.thinkboom.sixHat.domain.ShChatMessage;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ShMessageRequestDto {

    private ShChatMessage.MessageType type;

    private String roomId;

    private Long senderId;

    private String subject;

    private String hat;

    private String sender;

    private String message;

    private Integer currentPage;

    private List<ShRandomHatItem> randomHat;
}
