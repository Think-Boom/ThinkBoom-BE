package com.steepcliff.thinkboom.sixHat.dto.result;

import com.steepcliff.thinkboom.gallery.Gallery;
import com.steepcliff.thinkboom.sixHat.dto.result.ShResultResponseDto;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ShResultResponseContainer {

    private Gallery.RoomType category;

    private ShResultResponseDto data;
}
