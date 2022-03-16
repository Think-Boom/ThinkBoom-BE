package com.steepcliff.thinkboom.brainWriting.repository;

import com.steepcliff.thinkboom.brainWriting.domain.BwRoom;
import com.steepcliff.thinkboom.brainWriting.domain.BwUserRoom;
import com.steepcliff.thinkboom.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Queue;

public interface BwUserRoomRepository extends JpaRepository<BwUserRoom, Long> {
    List<BwUserRoom> findAllByBwroom(BwRoom bwRoom);
}
