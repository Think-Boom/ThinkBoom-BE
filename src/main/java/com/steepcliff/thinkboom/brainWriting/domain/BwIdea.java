package com.steepcliff.thinkboom.brainWriting.domain;

import com.steepcliff.thinkboom.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_bwIdea_id")
    private BwIdea bwIdea;

    @Column
    private Boolean isComment; // idea 라면 false, comment 라면 true

    @Column
    private String sequence;

    @Column
    @ColumnDefault("1")
    private Integer index;

    @Column
    @ColumnDefault("0")
    private Integer numberOfVotes;

    public BwIdea(User user, String sequence, Boolean isComment, BwRoom bwRoom) {
        this.user = user;
        this.sequence = sequence;
        this.isComment = isComment;
        this.bwRoom = bwRoom;
    }
}
