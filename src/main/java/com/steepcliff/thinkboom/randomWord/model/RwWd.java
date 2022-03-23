package com.steepcliff.thinkboom.randomWord.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Entity
@Getter
public class RwWd {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rwWdId;

    @ManyToOne
    @JoinColumn(name="rwId")
    private RandomWord randomWord;

    @ManyToOne
    @JoinColumn(name="wordId")
    private Word word;

}
