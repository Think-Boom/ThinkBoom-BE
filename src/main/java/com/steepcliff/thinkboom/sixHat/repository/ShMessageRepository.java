package com.steepcliff.thinkboom.sixHat.repository;

import com.steepcliff.thinkboom.sixHat.domain.ShChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShMessageRepository extends JpaRepository<ShChatMessage, Long> {
}
