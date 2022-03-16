package com.steepcliff.thinkboom.sixHat.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShNickResponseDto {

    private Long roomId;

    private Long userId;

    private String nickname;

    public ShNickResponseDto(Long roomId, Long userId, String nickname) {
        this.roomId = roomId;
        this.userId = userId;
        this.nickname = nickname;
    }
}
