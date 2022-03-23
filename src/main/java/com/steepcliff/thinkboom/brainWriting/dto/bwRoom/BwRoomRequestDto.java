package com.steepcliff.thinkboom.brainWriting.dto.bwRoom;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
public class BwRoomRequestDto {

    private Integer headCount;

    private String title;

    private Long time;

}
