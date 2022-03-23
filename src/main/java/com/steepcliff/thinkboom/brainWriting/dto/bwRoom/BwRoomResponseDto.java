package com.steepcliff.thinkboom.brainWriting.dto.bwRoom;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class BwRoomResponseDto {

    private String roomId;

    private Integer headCount;

    private Long time;

    public BwRoomResponseDto(String roomId,Integer headCount, Long time) {
        this.roomId = roomId;
        this.headCount = headCount;
        this.time = time;
    }
}
