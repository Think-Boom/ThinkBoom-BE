package com.steepcliff.thinkboom.randomWord.dto;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class RwRequestDto {
    List<WordDto> wordList;
}