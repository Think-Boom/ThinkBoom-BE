package com.steepcliff.thinkboom.sixHat.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShNickResponseDto {

    private String roomId;

    private Long userId;

    private String nickname;

    private Boolean isDuplicated;

    public ShNickResponseDto(String roomId, Long userId, String nickname, Boolean isDuplicated) {
        this.roomId = roomId;
        this.userId = userId;
        this.nickname = nickname;
        this.isDuplicated = isDuplicated;
    }

    public ShNickResponseDto(String roomId, String nickname, Boolean isDuplicated) {
        this.roomId = roomId;
        this.userId = null;
        this.nickname = nickname;
        this.isDuplicated = isDuplicated;
    }
}
