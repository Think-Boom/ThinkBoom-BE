package com.steepcliff.thinkboom.sixHat.repository;

import com.steepcliff.thinkboom.sixHat.domain.ShChatMessage;
import com.steepcliff.thinkboom.sixHat.domain.ShRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShMessageRepository extends JpaRepository<ShChatMessage, Long> {
    List<ShChatMessage> findAllByShRoom(ShRoom shRoom);
}
