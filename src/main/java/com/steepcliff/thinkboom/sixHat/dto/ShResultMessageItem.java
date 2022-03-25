package com.steepcliff.thinkboom.sixHat.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ShResultMessageItem {

    private Long senderId;

    private String sender;

    private String hat;

    private String message;
}
