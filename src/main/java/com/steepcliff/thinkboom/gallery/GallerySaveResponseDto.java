package com.steepcliff.thinkboom.gallery;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GallerySaveResponseDto {

    private String roomId;

    private Gallery.RoomType type;

    private String title;

    private String subject;
}
