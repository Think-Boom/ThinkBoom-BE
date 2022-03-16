package com.sparta.sixhat.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ChatRoomRequestDto {

    private String title;
    private Long memberCount;
    private Long timer;
    //private Long userId;

}
