package com.steepcliff.thinkboom.sixHat.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShNickResponseDto {

    private String roomId;

    private Long userId;

    private String nickname;

    public ShNickResponseDto(String roomId, Long userId, String nickname) {
        this.roomId = roomId;
        this.userId = userId;
        this.nickname = nickname;
    }
}
