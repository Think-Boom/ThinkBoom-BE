package com.steepcliff.thinkboom.brainWriting.repository;

import com.steepcliff.thinkboom.brainWriting.domain.BwRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BwRoomRepository extends JpaRepository<BwRoom, String> {
}
