package com.steepcliff.thinkboom.sixHat.dto.result;

import com.steepcliff.thinkboom.sixHat.dto.result.ShResultMessageItem;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ShResultResponseDto {

    private String subject;

    private List<ShResultMessageItem> messageList;
}
