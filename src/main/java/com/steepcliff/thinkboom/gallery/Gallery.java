package com.steepcliff.thinkboom.gallery;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Gallery {

    public enum RoomType {
        randomword, brainwriting, sixhat
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String roomId;

    @Column
    private RoomType category;

    @Column
    private String title;

    @Column
    private String subject;

    public Gallery(GallerySaveResponseDto gallerySaveResponseDto){
        this.roomId = gallerySaveResponseDto.getRoomId();
        this.category = gallerySaveResponseDto.getCategory();
        this.title = gallerySaveResponseDto.getTitle();
        this.subject = gallerySaveResponseDto.getSubject();
    }
}
