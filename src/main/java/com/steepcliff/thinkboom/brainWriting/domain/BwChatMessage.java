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
public class BwChatMessage {

    public enum MessageType {
        ENTER, TALK, QUIT, HAT, SUBJECT, NEXTPAGE, DEBATING
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private BwRoom room;

}
