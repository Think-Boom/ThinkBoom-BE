package com.steepcliff.thinkboom.brainWriting.domain;

import com.steepcliff.thinkboom.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class BwChatMessage {

    public enum MessageType {
        ENTER, TALK, QUIT, HAT, SUBJECT, NEXTPAGE
    }

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private MessageType type;

    @Column
    private String message;

    @Column
    private String createdAt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "room_id")
    private BwRoom room;

    @Builder
    public BwChatMessage(MessageType type, User user, BwRoom room, String message) {
        this.type = type;
        this.user = user;
        this.room = room;
        this.message = message;
    }
}
