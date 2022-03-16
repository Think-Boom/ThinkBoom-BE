package com.steepcliff.thinkboom.brainWriting.repository;


import com.steepcliff.thinkboom.brainWriting.domain.BwIdea;
import com.steepcliff.thinkboom.brainWriting.domain.BwRoom;
import com.steepcliff.thinkboom.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BwIdeaRepository extends JpaRepository<BwIdea, Long> {
        BwIdea findByUser(User user);

//        List<BwIdea> findAllByBwRoom(BwRoom bwRoom);

        BwIdea findByBwRoomAndUser(BwRoom bwRoom, User user);

        List<BwIdea> findAllByBwRoomAndIsComment(BwRoom bwRoom, Boolean isComment);

        List<BwIdea> findAllByBwIdeaAndIsComment(BwIdea bwIdea, Boolean isComment);


}
