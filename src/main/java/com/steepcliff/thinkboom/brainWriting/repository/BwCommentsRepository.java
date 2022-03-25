package com.steepcliff.thinkboom.brainWriting.repository;

import com.steepcliff.thinkboom.brainWriting.domain.BwComments;
import com.steepcliff.thinkboom.brainWriting.domain.BwIdea;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BwCommentsRepository extends JpaRepository<BwComments, Long> {
    List<BwComments> findAllByBwIdea(BwIdea bwIdea);
}
