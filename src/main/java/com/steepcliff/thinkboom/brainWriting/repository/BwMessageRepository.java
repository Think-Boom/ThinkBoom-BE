package com.steepcliff.thinkboom.brainWriting.repository;

import com.steepcliff.thinkboom.brainWriting.domain.BwChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BwMessageRepository extends JpaRepository<BwChatMessage, Long> {
}

