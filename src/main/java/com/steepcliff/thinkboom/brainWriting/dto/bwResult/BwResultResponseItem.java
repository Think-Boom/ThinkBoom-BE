package com.steepcliff.thinkboom.brainWriting.dto.bwResult;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BwResultResponseItem {

    private Long ideaId;

    private String nickname;

    private String idea;

    private Integer voteCount;

    private List<BwResultResponseComments> commentsList;
}
