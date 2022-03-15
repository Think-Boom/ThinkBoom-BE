package com.steepcliff.thinkboom.brainWriting.repository;

import com.steepcliff.thinkboom.brainWriting.domain.BwUserRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BwUserRoomRepository extends JpaRepository<BwUserRoom, Long> {
}
