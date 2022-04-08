package com.steepcliff.thinkboom.brainWriting.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BwIdeaViewResponseDto {

    private Boolean isFirstComment = false;

    private Boolean isLastComment = false;

    private Long viewUserId;

    private Long ideaId;

    private String idea;


}
