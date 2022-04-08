package com.steepcliff.thinkboom.brainWriting.dto.bwVoteView;

import com.steepcliff.thinkboom.brainWriting.domain.BwComments;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BwVoteViewCardsItem {

    private Long ideaId;

    private String idea;

    private List<String> commentList;
}
