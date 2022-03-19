package com.steepcliff.thinkboom.brainWriting.dto.bwComment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BwCommentResponseDto {

    private Long ideaId;

    private Long userId;

    private String comment;

}
