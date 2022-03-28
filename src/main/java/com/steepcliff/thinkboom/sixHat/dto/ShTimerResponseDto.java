package com.steepcliff.thinkboom.sixHat.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShTimerResponseDto {

    private Long timers;


    public ShTimerResponseDto(Long remainingSec) {
        this.timers = remainingSec;
    }
}
