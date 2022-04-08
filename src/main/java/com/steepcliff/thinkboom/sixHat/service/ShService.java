package com.steepcliff.thinkboom.sixHat.service;

import com.steepcliff.thinkboom.exception.NotFoundException;
import com.steepcliff.thinkboom.gallery.Gallery;
import com.steepcliff.thinkboom.gallery.GallerySaveResponseDto;
import com.steepcliff.thinkboom.gallery.GalleryService;
import com.steepcliff.thinkboom.sixHat.domain.ShRoom;
import com.steepcliff.thinkboom.sixHat.domain.ShUserRoom;
import com.steepcliff.thinkboom.sixHat.dto.*;
import com.steepcliff.thinkboom.sixHat.dto.nickname.ShNickRequestDto;
import com.steepcliff.thinkboom.sixHat.dto.nickname.ShNickResponseDto;
import com.steepcliff.thinkboom.sixHat.dto.room.ShRoomRequestDto;
import com.steepcliff.thinkboom.sixHat.dto.room.ShRoomResponseDto;
import com.steepcliff.thinkboom.sixHat.repository.ShRoomRepository;
import com.steepcliff.thinkboom.sixHat.repository.ShUserRoomRepository;
import com.steepcliff.thinkboom.user.User;
import com.steepcliff.thinkboom.user.UserService;
import com.steepcliff.thinkboom.webSocket.chat.UserListItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class ShService {

    private final ShRoomRepository shRoomRepository;
    private final ShUserRoomRepository shUserRoomRepository;
    private final UserService userService;
    private final GalleryService galleryService;

    @Autowired
    public ShService(ShRoomRepository shRoomRepository, ShUserRoomRepository shUserRoomRepository, UserService userService, GalleryService galleryService) {
        this.shRoomRepository = shRoomRepository;
        this.shUserRoomRepository = shUserRoomRepository;
        this.userService = userService;
        this.galleryService = galleryService;
    }

    // 식스햇 방 생성.
    public ShRoomResponseDto createRoom(ShRoomRequestDto requestDto) {

        ShRoom shRoom = new ShRoom(requestDto.getTitle(),requestDto.getHeadCount(), 0, true, requestDto.getTimer());

        shRoomRepository.save(shRoom);

        return new ShRoomResponseDto(shRoom.getId(),shRoom.getTitle() ,shRoom.getHeadCount(), requestDto.getTimer());
    }

    // 식스햇 닉네임 입력
    @Transactional
    public ShNickResponseDto createNickname(ShNickRequestDto requestDto) {
        log.info("서비스 시작");
        ShRoom shRoom = findShRoom(requestDto.getShRoomId());
        // 방에서 닉네임 중복 검사
        List<ShUserRoom> shUserRoomList = shUserRoomRepository.findAllByShRoom(shRoom);
        List<String> userNicknameList = new ArrayList<>();
        for(ShUserRoom shUserRoom:shUserRoomList) {
            userNicknameList.add(shUserRoom.getUser().getNickname());
        }

        if(userNicknameList.contains(requestDto.getNickname())) {
            return new ShNickResponseDto(requestDto.getShRoomId(),requestDto.getNickname(), true );
        } else {
            User user = userService.save(requestDto.getNickname());
            log.info("유저 아이디{}, 모자 {}", user.getId(), user.getHat());
            if(shRoom.getHostId() == null) shRoom.setHostId(user.getId());

            shRoomRepository.save(shRoom);
            ShUserRoom shUserRoom= new ShUserRoom(user, shRoom);

            shUserRoomRepository.save(shUserRoom);
            log.info("shRoom.getId() {} user.getId() {}",shRoom.getId(), user.getId());
            return new ShNickResponseDto(String.valueOf(shRoom.getId()), user.getId(), user.getNickname(), false);
        }


    }

    // 남은 시간 주기
    public ShTimerResponseDto getTime(String shRoomId) {
        ShRoom shRoom = findShRoom(shRoomId);

        LocalDateTime nowTime = LocalDateTime.now();

        LocalDateTime remainingTime = shRoom.getShTimer();

        long seconds = ChronoUnit.SECONDS.between(nowTime, remainingTime);

        Long remainingSec = seconds;

        log.info("남은시간 seconds:{}", seconds);
        log.info("남은시간 total:{}", remainingSec);

        return new ShTimerResponseDto(remainingSec);
    }

    // 갤러리에 저장.
    public void shSaveGallery(String shRoomId) {
        ShRoom shRoom = findShRoom(shRoomId);

        GallerySaveResponseDto gallerySaveResponseDto = new GallerySaveResponseDto();
        gallerySaveResponseDto.setRoomId(shRoom.getId());
<<<<<<< HEAD
        gallerySaveResponseDto.setType(Gallery.RoomType.sixHat);
=======
        gallerySaveResponseDto.setCategory(Gallery.RoomType.sixhat);
>>>>>>> e3ecf966c16a767229a9405bc18c6cfccdcba3a3
        gallerySaveResponseDto.setTitle(shRoom.getTitle());
        gallerySaveResponseDto.setSubject(shRoom.getSubject());
        galleryService.saveGallery(gallerySaveResponseDto);
    }

    //식스햇 공유여부 true->false
    @Transactional
    public void shSharingCheck(String shRoomId) {
        ShRoom shRoom = findShRoom(shRoomId);

        shRoom.setSharing(false);
//        galleryService.deleteGallery(shRoomId);

    }

    // 유저 1 증가
    @Transactional
    public void plusUserCount(String roomId) {

        ShRoom shRoom = findShRoom(roomId);

        shRoom.setCurrentUsers(shRoom.getCurrentUsers() + 1);
    }

    // 유저 1 감소
    @Transactional
    public void minusUserCount(String roomId) {

        ShRoom shRoom = findShRoom(roomId);

        shRoom.setCurrentUsers(shRoom.getCurrentUsers() - 1);
    }

    // 식스햇 방 찾기
    public ShRoom findShRoom(String shRoomId) {
        return shRoomRepository.findById(shRoomId).orElseThrow(
                () -> new NotFoundException("해당 방이 존재하지 않습니다.")
        );
    }

    public String getEnterUserRoomId(String senderId) {
        return shUserRoomRepository.findByUserId(Long.valueOf(senderId)).getShRoom().getId();
    }

    // 주제 저장하기
    @Transactional
    public void saveSubject(String shRoomId ,String subject) {
        ShRoom shRoom = findShRoom(shRoomId);

        shRoom.setSubject(subject);
    }

    // 모자 정보와 유저 정보가 담긴 유저 리스트 넘기기
    public List<UserListItem> getUserList(String shRoomId) {
        ShRoom shRoom = findShRoom(shRoomId);

        List<ShUserRoom> shUserRoomList = shUserRoomRepository.findAllByShRoom(shRoom);

        List<UserListItem> userListItemList = new ArrayList<>();
        for(ShUserRoom shUserRoom:shUserRoomList) {
            UserListItem userListItem = new UserListItem();

            userListItem.setNickname(shUserRoom.getUser().getNickname());
            userListItem.setHat(shUserRoom.getUser().getHat());

            userListItemList.add(userListItem);
        }
        return userListItemList;
    }

    // 시간 갱신하기
    @Transactional
    public void renewTimer(String shRoomId) {
        ShRoom shRoom = findShRoom(shRoomId);

        LocalDateTime localDateTime = LocalDateTime.now().plusMinutes(shRoom.getTimes());
        log.info("sixHat renew time: {}", localDateTime);

        shRoom.setShTimer(localDateTime);
    }
}
