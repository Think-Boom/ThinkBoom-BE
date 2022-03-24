package com.steepcliff.thinkboom.randomWord.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
public class RandomWord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rwId;

    @Column(nullable = false)
    private String subject;

    @Column(nullable = false)
    private String uuId;

    @Column(nullable = false)
    private Boolean share=true;

    public void update() {
        this.share=false;
    }
}
