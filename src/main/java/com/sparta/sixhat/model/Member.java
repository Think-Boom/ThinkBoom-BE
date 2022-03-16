package com.sparta.sixhat.model;

import lombok.*;

import javax.persistence.*;


@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Member extends Timestamped {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(nullable = false)
    private String nickname;



//    @JoinColumn(name = "SHChatroom_id")
//    @ManyToOne
//    private SHChatRoom shChatRoom;


    //    public Member(String nickname, String hat, String nick, boolean role) {
//
//        this.nickname = nickname;
//        this.hat = hat;
//        this.role = role;
//    }
    public Member(String nickname) {

        this.nickname = nickname;

    }
}
