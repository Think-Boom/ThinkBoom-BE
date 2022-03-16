package com.steepcliff.thinkboom.brainWriting.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class BwRoom {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    @Column(name = "chat_room_id")
    private Long id;

    @Column
    private Integer headCount;

    @Column
    private Integer time;

    @Column
    private String subject;

    @Column
    private Long hostId;

    @Column
    private Integer presentVoted;

    @Column
    private Integer insertCount;

    public BwRoom(Integer headCount, Integer time, String subject, Long hostId) {
        this.headCount = headCount;
        this.time = time;
        this.subject = subject;
        this.hostId = hostId;
    }

    public  BwRoom(Integer headCount, Integer time) {
        this.headCount = headCount;
        this.time = time;
    }
}
