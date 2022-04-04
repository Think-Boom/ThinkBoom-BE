package com.steepcliff.thinkboom.sixHat.dto.room;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ShRoomResponseDto {

    private String shRoomId;

    private String title;

    private Integer headCount;

    private Integer timer;

}
