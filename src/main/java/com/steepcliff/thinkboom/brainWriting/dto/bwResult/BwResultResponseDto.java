package com.steepcliff.thinkboom.brainWriting.dto.bwResult;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BwResultResponseDto {

    private String subject;

    private List<BwResultResponseItem> result;
}
