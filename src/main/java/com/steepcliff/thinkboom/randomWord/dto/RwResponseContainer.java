package com.steepcliff.thinkboom.randomWord.dto;

import com.steepcliff.thinkboom.gallery.Gallery;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RwResponseContainer {

    private Gallery.RoomType category;

    private RwResponseDto data;
}
