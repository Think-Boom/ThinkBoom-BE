package com.steepcliff.thinkboom.brainWriting.domain;

import com.steepcliff.thinkboom.user.User;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DynamicInsert
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
    private Integer bwIndex;

    @Column
    private Integer numberOfVotes;

    public BwIdea(User user, String bwSequence, BwRoom bwRoom, Integer bwIndex, Integer numberOfVotes) {
        this.user = user;
        this.bwSequence = bwSequence;
        this.bwRoom = bwRoom;
        this.bwIndex = bwIndex;
        this.numberOfVotes = numberOfVotes;
    }
}
