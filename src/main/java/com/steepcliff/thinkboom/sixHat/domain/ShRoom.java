package com.steepcliff.thinkboom.sixHat.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private String title;

    @Column
    private Integer headCount;

    @Column
    private Integer time;

    @Column
    private String subject;

    @Column
    private Long hostId;


    public ShRoom(String title, Integer headCount, Integer time) {
        this.title = title;
        this.headCount = headCount;
        this.time = time;
    }
}
