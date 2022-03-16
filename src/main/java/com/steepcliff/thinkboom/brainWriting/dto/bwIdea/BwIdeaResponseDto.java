package com.steepcliff.thinkboom.brainWriting.dto.bwIdea;

import com.steepcliff.thinkboom.brainWriting.domain.BwIdea;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BwIdeaResponseDto {

    private Long userId;

    private String idea;


}
