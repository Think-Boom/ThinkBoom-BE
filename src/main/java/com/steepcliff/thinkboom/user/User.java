package com.steepcliff.thinkboom.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String nickname;

    @Column
    private Boolean isVote = false;

    @Column
    private Integer bwIndex = 1;

    @Column
    private String hat = "none";

    public User(String nickname) {
        this.nickname = nickname;
    }

}