package com.steepcliff.thinkboom.brainWriting.dto.bwVoteView;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class BwVoteViewResponseDto {

    private String subject;

    private List<BwVoteViewCardsItem> bwVoteViewCardsItems;


}