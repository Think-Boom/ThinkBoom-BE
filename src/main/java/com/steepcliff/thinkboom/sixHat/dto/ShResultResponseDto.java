package com.steepcliff.thinkboom.sixHat.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ShResultResponseDto {

    private String subject;

    private List<ShResultMessageItem> messageList;
}
