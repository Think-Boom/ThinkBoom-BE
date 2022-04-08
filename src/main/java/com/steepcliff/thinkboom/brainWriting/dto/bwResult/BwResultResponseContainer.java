package com.steepcliff.thinkboom.brainWriting.dto.bwResult;

import com.steepcliff.thinkboom.gallery.Gallery;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BwResultResponseContainer {

    private Gallery.RoomType category;

    private BwResultResponseDto data;


}
