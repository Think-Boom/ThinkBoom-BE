package com.steepcliff.thinkboom.brainWriting.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BwIdeaListItem {

    private Long viewUserId;

    private Long ideaId;

    private String idea;
}
