package com.steepcliff.thinkboom.sixHat.repository;

import com.steepcliff.thinkboom.sixHat.domain.ShRoom;
import com.steepcliff.thinkboom.sixHat.domain.ShUserRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShUserRoomRepository extends JpaRepository<ShUserRoom, Long> {

    ShUserRoom findByUserId(Long userId);

    List<ShUserRoom> findAllByShRoom(ShRoom shRoom);

}
