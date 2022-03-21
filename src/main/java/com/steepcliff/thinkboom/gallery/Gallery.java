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
    private Long roomId;

    @Column
    private RoomType type;

    @Column
    private String title;

    @Column
    private String subject;

}
