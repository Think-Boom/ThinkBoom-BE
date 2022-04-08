package com.steepcliff.thinkboom.brainWriting.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BwTimersResponseDto {

    private Long timers;

    public BwTimersResponseDto(Long remainingSec) {
        this.timers = remainingSec;
    }
}
