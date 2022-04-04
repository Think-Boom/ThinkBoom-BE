package com.steepcliff.thinkboom.brainWriting.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
public class BwIdeaListDto {

    private Boolean isEndComment;

    private List<BwIdeaListItem> bwIdeaListItemList;

}
