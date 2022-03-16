package com.steepcliff.thinkboom.brainWriting.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BwRoomResponseDto {

    private Long roomId;

    private Integer headCount;

    private Integer time;

    public BwRoomResponseDto(Long roomId, Integer headCount, Integer time) {
        this.roomId = roomId;
        this.headCount = headCount;
        this.time = time;
    }
}
