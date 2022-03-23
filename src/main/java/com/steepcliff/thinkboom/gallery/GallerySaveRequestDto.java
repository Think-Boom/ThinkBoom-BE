package com.steepcliff.thinkboom.gallery;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GallerySaveRequestDto {

    private String roomId;

    private Gallery.RoomType type;

    private String title;

    private String subject;
}
