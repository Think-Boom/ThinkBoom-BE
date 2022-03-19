package com.steepcliff.thinkboom.brainWriting.domain;


import com.steepcliff.thinkboom.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class BwUserRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room")
    private BwRoom bwroom;

    public BwUserRoom(User user, BwRoom bwroom) {
        this.user = user;
        this.bwroom = bwroom;
    }
}
