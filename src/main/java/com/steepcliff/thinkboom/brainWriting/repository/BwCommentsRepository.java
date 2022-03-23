package com.steepcliff.thinkboom.brainWriting.repository;

import com.steepcliff.thinkboom.brainWriting.domain.BwComments;
import com.steepcliff.thinkboom.brainWriting.domain.BwIdea;
import com.steepcliff.thinkboom.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BwCommentsRepository extends JpaRepository<BwComments, Long> {
    List<BwComments> findAllByBwIdea(BwIdea bwIdea);

    Optional<BwComments> findByUserAndBwIdea(User user, BwIdea bwIdea);
}
