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
<<<<<<< HEAD
        randomWord, brainwriting, sixHat
=======
        randomword, brainwriting, sixhat
>>>>>>> e3ecf966c16a767229a9405bc18c6cfccdcba3a3
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
