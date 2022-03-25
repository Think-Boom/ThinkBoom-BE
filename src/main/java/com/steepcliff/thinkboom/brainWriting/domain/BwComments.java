package com.steepcliff.thinkboom.brainWriting.domain;

import com.steepcliff.thinkboom.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BwComments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @Lob
    private String comments;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "bwRoom_id")
    private BwRoom bwRoom;

    @ManyToOne
    @JoinColumn(name = "bwIdea_id")
    private BwIdea bwIdea;

    public void setBwIdea(BwIdea bwIdea) {
        if(this.bwIdea != null) {
            this.bwIdea.getBwCommentsList().remove(this);
        }
        this.bwIdea = bwIdea;
        bwIdea.getBwCommentsList().add(this);
    }

}

