package com.steepcliff.thinkboom.brainWriting.domain;

import com.steepcliff.thinkboom.user.User;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class BwIdea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @Lob
    private String idea;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "bwRoom_id")
    private BwRoom bwRoom;

//    @OneToMany(mappedBy = "bwIdea")
//    @JoinColumn(name = "bw_comments")
//    private BwComments bwComments;

    @Column
    private String bwSequence;

    @Column
    @ColumnDefault("1")
    private Integer bwIndex;

    @Column
    @ColumnDefault("0")
    private Integer numberOfVotes;

    public BwIdea(User user, String bwSequence, BwRoom bwRoom) {
        this.user = user;
        this.bwSequence = bwSequence;
        this.bwRoom = bwRoom;
    }
}
