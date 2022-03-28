package com.steepcliff.thinkboom.sixHat.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShRoom {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @Column
    private String title;

    @Column
    private Integer headCount;

    @Column
    private LocalDateTime shTimer;

    @Column
    private String subject;

    @Column
    private Boolean sharing;

    @Column
    private Long hostId;

    @Column
    private Integer currentUsers;


    public ShRoom(String title, Integer headCount, LocalDateTime shTimer, Integer currentUsers, Boolean sharing) {
        this.title = title;
        this.headCount = headCount;
        this.shTimer = shTimer;
        this.currentUsers = currentUsers;
        this.sharing = sharing;
    }
}
