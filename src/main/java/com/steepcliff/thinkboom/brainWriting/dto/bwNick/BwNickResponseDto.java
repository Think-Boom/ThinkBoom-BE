package com.steepcliff.thinkboom.brainWriting.dto.bwNick;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BwNickResponseDto {

    private Long userId;

    private String nickname;

    public BwNickResponseDto(Long userId, String nickname) {
        this.userId = userId;
        this.nickname = nickname;
    }
}
