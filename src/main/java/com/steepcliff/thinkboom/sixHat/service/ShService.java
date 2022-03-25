package com.steepcliff.thinkboom.sixHat.service;

import com.steepcliff.thinkboom.gallery.Gallery;
import com.steepcliff.thinkboom.gallery.GallerySaveResponseDto;
import com.steepcliff.thinkboom.gallery.GalleryService;
import com.steepcliff.thinkboom.sixHat.domain.ShRoom;
import com.steepcliff.thinkboom.sixHat.domain.ShUserRoom;
import com.steepcliff.thinkboom.sixHat.dto.*;
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

        ShRoom shRoom = new ShRoom(requestDto.getTitle(),requestDto.getHeadCount(), requestDto.getTimer(), 0, true);

        shRoomRepository.save(shRoom);

        return new ShRoomResponseDto(String.valueOf(shRoom.getId()),shRoom.getTitle() ,shRoom.getHeadCount(), shRoom.getShTimer());
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
        ShUserRoom shUserRoom= new ShUserRoom(user, shRoom);

        shUserRoomRepository.save(shUserRoom);
        log.info("shRoom.getId() {} user.getId() {}",shRoom.getId(), user.getId());
        return new ShNickResponseDto(String.valueOf(shRoom.getId()), user.getId(), user.getNickname());
    }

    // 갤러리에 저장.
    public void shSaveGallery(String shRoomId) {
        ShRoom shRoom = shRoomRepository.findById(shRoomId).orElseThrow(
                () -> new NullPointerException("해당 방이 존재하지 않습니다.")
        );

        GallerySaveResponseDto gallerySaveResponseDto = new GallerySaveResponseDto();
        gallerySaveResponseDto.setRoomId(shRoom.getId());
        gallerySaveResponseDto.setType(Gallery.RoomType.SH);
        gallerySaveResponseDto.setTitle(shRoom.getTitle());
        gallerySaveResponseDto.setSubject(shRoom.getSubject());
        galleryService.saveGallery(gallerySaveResponseDto);
    }

    //식스햇 공유여부 true->false
    @Transactional
    public void shSharingCheck(String shRoomId) {
        ShRoom shRoom = shRoomRepository.findById(shRoomId).orElseThrow(
                () -> new NullPointerException("해당 방이 존재하지 않습니다.")
        );

        shRoom.setSharing(false);
//        galleryService.deleteGallery(shRoomId);

    }

    // 유저 1 증가
    @Transactional
    public void plusUserCount(String roomId) {

        ShRoom shRoom = shRoomRepository.findById(roomId).orElseThrow(
                () -> new NullPointerException("해당 방이 존재하지 않습니다.")
        );

        shRoom.setCurrentUsers(shRoom.getCurrentUsers() + 1);

        shRoomRepository.save(shRoom);
    }

    // 유저 1 감소
    @Transactional
    public void minusUserCount(String roomId) {

        ShRoom shRoom = shRoomRepository.findById(roomId).orElseThrow(
                () -> new NullPointerException("해당 방이 존재하지 않습니다.")
        );

        shRoom.setCurrentUsers(shRoom.getCurrentUsers() - 1);

        shRoomRepository.save(shRoom);
    }

    // 식스햇 방 찾기
    public ShRoom findShRoom(String shRoomId) {
        return shRoomRepository.findById(shRoomId).orElseThrow(
                () -> new NullPointerException("해당 방이 존재하지 않습니다.")
        );
    }

    // 주제 저장하기
    @Transactional
    public void saveSubject(String shRoomId ,String subject) {
        ShRoom shRoom = findShRoom(shRoomId);

        shRoom.setSubject(subject);

        shRoomRepository.save(shRoom);
    }
}
