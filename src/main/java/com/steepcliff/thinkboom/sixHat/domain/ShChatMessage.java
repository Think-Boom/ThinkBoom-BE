package com.steepcliff.thinkboom.sixHat.domain;

import com.steepcliff.thinkboom.user.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
public class ShChatMessage {

    public enum MessageType {
        ENTER, TALK, QUIT, HAT
    }

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private MessageType type;

    @Column
    private String hat;

    @Column
    private String message;

    @Column
    private String createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shroom_id")
    private ShRoom shRoom;

}
