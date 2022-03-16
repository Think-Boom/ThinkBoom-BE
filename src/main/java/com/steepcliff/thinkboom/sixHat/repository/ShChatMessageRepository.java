package com.steepcliff.thinkboom.sixHat.repository;

import com.steepcliff.thinkboom.sixHat.domain.ShChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShChatMessageRepository extends JpaRepository<ShChatMessage, Long> {
}
