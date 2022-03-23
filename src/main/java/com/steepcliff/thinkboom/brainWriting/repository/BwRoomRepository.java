package com.steepcliff.thinkboom.brainWriting.repository;

import com.steepcliff.thinkboom.brainWriting.domain.BwRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BwRoomRepository extends JpaRepository<BwRoom, String> {
}
