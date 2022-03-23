package com.steepcliff.thinkboom.brainWriting.domain;

import lombok.*;
import net.bytebuddy.implementation.bind.annotation.Default;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.GenericGenerators;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

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
    private LocalDateTime localDateTime;

    @Column
    private String title;

    @Column
    private String subject;

    @Column
    private Long hostId;

    @Column
    private Integer presentVoted;

    @Column
    private Integer insertCount;

    @Column
    private Integer currentUsers;

    public BwRoom(Integer headCount, LocalDateTime localDateTime, String subject, Long hostId) {
        this.headCount = headCount;
        this.localDateTime = localDateTime;
        this.subject = subject;
        this.hostId = hostId;
    }

    public  BwRoom(Integer headCount, String title, LocalDateTime localDateTime, Integer currentUsers) {
        this.headCount = headCount;
        this.title = title;
        this.localDateTime = localDateTime;
        this.currentUsers = currentUsers;
    }
}
