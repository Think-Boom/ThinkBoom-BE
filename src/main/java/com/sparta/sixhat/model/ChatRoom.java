package com.sparta.sixhat.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity
@NoArgsConstructor
public class ChatRoom extends Timestamped{

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    @Column(name = "chat_roomId")
    private Long id;

    @Column
    private String title;

    @Column
    private Long memberCount;

    @Column
    private Long timer;

//    @JoinColumn(name = "member_id")
//    @ManyToOne
//    private Member member;

    public ChatRoom(String title, Long memberCount, Long timer) {
        this.title = title;
        this.memberCount = memberCount;
        this.timer = timer;
    }
}
