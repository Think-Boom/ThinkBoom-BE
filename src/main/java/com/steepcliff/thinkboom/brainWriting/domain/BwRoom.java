package com.steepcliff.thinkboom.brainWriting.domain;

import lombok.*;
import net.bytebuddy.implementation.bind.annotation.Default;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.GenericGenerators;

import javax.persistence.*;

@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class BwRoom {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @Column
    private Integer headCount;

    @Column
    private Integer timer;

    @Column
    private String title;

    @Column
    private String subject;

    @Column
    private Long hostId;

    @Column
    private Boolean sharing;

    @Column
    private Integer presentVoted;

    @Column
    private Integer insertCount;

    @Column
    private Integer currentUsers;

    public BwRoom(Integer headCount, Integer timer, String subject, Long hostId) {
        this.headCount = headCount;
        this.timer = timer;
        this.subject = subject;
        this.hostId = hostId;
    }

    public  BwRoom(String title ,Integer headCount, Integer timer, Integer currentUsers, Boolean sharing) {
        this.title = title;
        this.headCount = headCount;
        this.timer = timer;
        this.currentUsers = currentUsers;
        this.sharing = sharing;
    }
}
