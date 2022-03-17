package com.steepcliff.thinkboom.sixHat;

import com.steepcliff.thinkboom.brainWriting.domain.BwRoom;
import com.steepcliff.thinkboom.brainWriting.domain.BwUserRoom;
import com.steepcliff.thinkboom.brainWriting.dto.BwNickRequestDto;
import com.steepcliff.thinkboom.brainWriting.dto.BwNickResponseDto;
import com.steepcliff.thinkboom.sixHat.domain.ShRoom;
import com.steepcliff.thinkboom.sixHat.domain.ShUserRoom;
import com.steepcliff.thinkboom.sixHat.dto.ShNickRequestDto;
import com.steepcliff.thinkboom.sixHat.dto.ShNickResponseDto;
import com.steepcliff.thinkboom.sixHat.dto.ShRoomRequestDto;
import com.steepcliff.thinkboom.sixHat.dto.ShRoomResponseDto;
import com.steepcliff.thinkboom.sixHat.repository.ShRoomRepository;
import com.steepcliff.thinkboom.sixHat.repository.ShUserRoomRepository;
import com.steepcliff.thinkboom.user.User;
import com.steepcliff.thinkboom.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Slf4j
@Service
public class ShService {

    private final ShRoomRepository shRoomRepository;
    private final ShUserRoomRepository shUserRoomRepository;
    private final UserService userService;

    @Autowired
    public ShService(ShRoomRepository shRoomRepository, ShUserRoomRepository shUserRoomRepository, UserService userService) {
        this.shRoomRepository = shRoomRepository;
        this.shUserRoomRepository = shUserRoomRepository;
        this.userService = userService;
    }

    // 식스햇 방 생성.
    public ShRoomResponseDto createRoom(ShRoomRequestDto requestDto) {

        ShRoom shRoom = new ShRoom(requestDto.getTitle(),requestDto.getHeadCount(), requestDto.getTimer());

        shRoomRepository.save(shRoom);

        return new ShRoomResponseDto(String.valueOf(shRoom.getId()),shRoom.getTitle() ,shRoom.getHeadCount(), shRoom.getTime());
    }

    // 시스햇 닉네임 입력
    @Transactional
    public ShNickResponseDto createNickname(ShNickRequestDto requestDto) {
        log.info("서비스 시작");
        ShRoom shRoom = shRoomRepository.findById(requestDto.getShRoomId()).orElseThrow(
                () -> new NullPointerException("해당 방이 존재하지 않습니다.")
        );

        User user = userService.save(requestDto.getNickname());
        log.info("유저 아이디{}", user.getId());
        if(shRoom.getHostId() == null) shRoom.setHostId(user.getId());

        shRoomRepository.save(shRoom);

        shUserRoomRepository.save(new ShUserRoom(user, shRoom));
        log.info("shRoom.getId() {} user.getId() {}",shRoom.getId(), user.getId());
        return new ShNickResponseDto(String.valueOf(shRoom.getId()), user.getId(), user.getNickname());
    }



}
