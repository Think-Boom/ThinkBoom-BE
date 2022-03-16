package com.steepcliff.thinkboom.sixHat.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ShRoomResponseDto {

    private Long shRoomId;

    private String title;

    private Integer headCount;

    private Integer timer;


}
