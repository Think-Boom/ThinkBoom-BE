package com.steepcliff.thinkboom.brainWriting.domain;

import lombok.*;
import net.bytebuddy.implementation.bind.annotation.Default;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.GenericGenerators;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

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
    private Integer times;

    @Column
    private LocalDateTime timer;

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

    public BwRoom(Integer headCount, LocalDateTime timer, String subject, Long hostId) {
        this.headCount = headCount;
        this.timer = timer;
        this.subject = subject;
        this.hostId = hostId;
    }

    public  BwRoom(String title ,Integer headCount, Integer times, Integer currentUsers, Boolean sharing, Integer presentVoted) {
        this.title = title;
        this.headCount = headCount;
        this.times = times;
        this.currentUsers = currentUsers;
        this.sharing = sharing;
        this.presentVoted = presentVoted;
    }
}
