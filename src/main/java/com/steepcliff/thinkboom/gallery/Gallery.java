package com.steepcliff.thinkboom.gallery;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class Gallery {

    public enum RoomType {
        RW, BW, SH
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String roomId;

    @Column
    private RoomType type;

    @Column
    private String title;

    @Column
    private String subject;

    public Gallery(GallerySaveRequestDto gallerySaveRequestDto){
        this.roomId = gallerySaveRequestDto.getRoomId();
        this.type = gallerySaveRequestDto.getType();
        this.title = gallerySaveRequestDto.getTitle();
        this.subject = gallerySaveRequestDto.getSubject();
    }

}
