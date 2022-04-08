package com.steepcliff.thinkboom.brainWriting.service;

import com.steepcliff.thinkboom.brainWriting.domain.BwComments;
import com.steepcliff.thinkboom.brainWriting.domain.BwIdea;
import com.steepcliff.thinkboom.brainWriting.domain.BwRoom;
import com.steepcliff.thinkboom.brainWriting.domain.BwUserRoom;
import com.steepcliff.thinkboom.brainWriting.dto.*;
import com.steepcliff.thinkboom.brainWriting.dto.bwComment.BwCommentRequestDto;
import com.steepcliff.thinkboom.brainWriting.dto.bwComment.BwCommentResponseDto;
import com.steepcliff.thinkboom.brainWriting.dto.bwIdea.BwIdeaRequestDto;
import com.steepcliff.thinkboom.brainWriting.dto.bwIdea.BwIdeaResponseDto;
import com.steepcliff.thinkboom.brainWriting.dto.bwNickname.BwNickRequestDto;
import com.steepcliff.thinkboom.brainWriting.dto.bwNickname.BwNickResponseDto;
import com.steepcliff.thinkboom.brainWriting.dto.bwResult.BwResultResponseContainer;
import com.steepcliff.thinkboom.brainWriting.dto.bwResult.BwResultResponseDto;
import com.steepcliff.thinkboom.brainWriting.dto.bwResult.BwResultResponseItem;
import com.steepcliff.thinkboom.brainWriting.dto.bwRoom.BwRoomRequestDto;
import com.steepcliff.thinkboom.brainWriting.dto.bwRoom.BwRoomResponseDto;
import com.steepcliff.thinkboom.brainWriting.dto.bwVote.BwVoteRequestDto;
import com.steepcliff.thinkboom.brainWriting.dto.bwVote.BwVoteResponseDto;
import com.steepcliff.thinkboom.brainWriting.dto.bwVoteView.BwVoteViewCardsItem;
import com.steepcliff.thinkboom.brainWriting.dto.bwVoteView.BwVoteViewResponseDto;
import com.steepcliff.thinkboom.brainWriting.repository.BwCommentsRepository;
import com.steepcliff.thinkboom.brainWriting.repository.BwIdeaRepository;
import com.steepcliff.thinkboom.brainWriting.repository.BwRoomRepository;
import com.steepcliff.thinkboom.brainWriting.repository.BwUserRoomRepository;
import com.steepcliff.thinkboom.exception.NotFoundException;
import com.steepcliff.thinkboom.gallery.Gallery;
import com.steepcliff.thinkboom.gallery.GallerySaveResponseDto;
import com.steepcliff.thinkboom.gallery.GalleryService;
import com.steepcliff.thinkboom.user.User;
import com.steepcliff.thinkboom.user.UserService;
import com.steepcliff.thinkboom.webSocket.chat.UserListItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Slf4j
@Service
public class BwService {

    private final BwRoomRepository bwRoomRepository;
    private final BwUserRoomRepository bwUserRoomRepository;
    private final UserService userService;
    private final BwIdeaRepository bwIdeaRepository;
    private final BwCommentsRepository bwCommentsRepository;
    private final GalleryService galleryService;

    @Autowired
    public BwService(BwRoomRepository bwRoomRepository, BwUserRoomRepository bwUserRoomRepository, UserService userService, BwIdeaRepository bwIdeaRepository, BwCommentsRepository bwCommentsRepository, GalleryService galleryService) {
        this.bwRoomRepository = bwRoomRepository;
        this.bwUserRoomRepository = bwUserRoomRepository;
        this.userService = userService;
        this.bwIdeaRepository = bwIdeaRepository;
        this.bwCommentsRepository = bwCommentsRepository;
        this.galleryService = galleryService;
    }

    // 브레인 라이팅 방 생성.
    public BwRoomResponseDto createBwRoom(BwRoomRequestDto requestDto) {


        BwRoom bwRoom = new BwRoom(requestDto.getTitle(), requestDto.getHeadCount(), requestDto.getTime(), 0, true, 0);

        bwRoomRepository.save(bwRoom);

        return new BwRoomResponseDto(bwRoom.getId(), requestDto.getHeadCount(), requestDto.getTime());
    }

    // 브레인 라이팅 닉네임 입력.
    @Transactional
    public BwNickResponseDto createNickname(String bwRoomId, BwNickRequestDto requestDto) {

        BwRoom bwRoom = findBwRoom(bwRoomId);

        User user = userService.save(requestDto.getNickname());

        if (bwRoom.getHostId() == null) {
            bwRoom.setHostId(user.getId());
        }
        bwRoomRepository.save(bwRoom);

        bwUserRoomRepository.save(new BwUserRoom(user, bwRoom));

        return new BwNickResponseDto(user.getId(), user.getNickname());
    }

    // 아이디어 카드 생성
    public void createIdea(String bwRoomId) {
        BwRoom bwRoom = findBwRoom(bwRoomId);

        List<BwUserRoom> userRoomList = bwUserRoomRepository.findAllByBwroom(bwRoom);
        Queue<User> userQueue = new LinkedList<>();
        for (BwUserRoom bwUserRoom : userRoomList) {
            userQueue.add(bwUserRoom.getUser());
        }

        for (int i = 0; i < bwRoom.getHeadCount(); i++) {
            StringBuilder sequence = new StringBuilder();
            for (User user : userQueue) {
                sequence.append(user.getId());
                sequence.append(":");
            }
            User user = userQueue.poll();
            userQueue.add(user);

            String sequenceStr = sequence.toString();
            sequenceStr = sequenceStr.substring(0, sequenceStr.length() - 1);
            BwIdea bwIdea = new BwIdea(user, sequenceStr, bwRoom, 1, 0);

            bwIdeaRepository.save(bwIdea);
            log.info("{} {}", bwIdea.getId(), bwIdea.getBwSequence());
        }
    }

    // 브레인 라이팅 아이디어 입력
    @Transactional
    public BwIdeaResponseDto insertIdea(String bwRoomId, BwIdeaRequestDto requestDto) {
//        BwRoom bwRoom = findBwRoom(bwRoomId);
        User user = userService.findById(requestDto.getUserId());

        BwIdea bwIdea = bwIdeaRepository.findByUser(user);
        log.info(requestDto.getIdea());
        bwIdea.setIdea(requestDto.getIdea());

        return new BwIdeaResponseDto(user.getId(), bwIdea.getIdea());
    }

    // 브레인 라이팅 아이디어 반환
    @Transactional
    public BwIdeaViewResponseDto getAllIdeaWithOrederBy(String bwRoomId, Long userId) {

        User user = userService.findById(userId);
        log.info("{} {}", userId, user.getBwIndex());
        BwRoom bwRoom = findBwRoom(bwRoomId);

        List<BwIdea> bwIdeaList = bwIdeaRepository.findAllByBwRoom(bwRoom);
        log.info("아이디어 목록반환1");

        BwIdeaViewResponseDto bwIdeaViewResponseDto = new BwIdeaViewResponseDto();

        for (BwIdea bwIdea : bwIdeaList) {

            if (bwIdea.getBwIndex() >= bwRoom.getHeadCount()) {
                log.info("continue 진행");
                continue;
            }

            String[] strings = bwIdea.getBwSequence().split(":");
            if (Long.valueOf(strings[bwIdea.getBwIndex()]).equals(userId) && bwIdea.getBwIndex().equals(user.getBwIndex())) {

                log.info("bwIndex {}", bwIdea.getBwIndex());
                bwIdeaViewResponseDto.setViewUserId(Long.valueOf(strings[bwIdea.getBwIndex()]));
                bwIdeaViewResponseDto.setIdeaId(bwIdea.getId());
                bwIdeaViewResponseDto.setIdea(bwIdea.getIdea());

                if (bwIdea.getBwIndex() == 1) {
                    bwIdeaViewResponseDto.setIsFirstComment(true);
                }
                bwIdea.setBwIndex(bwIdea.getBwIndex() + 1);
                user.setBwIndex(user.getBwIndex() + 1);
                if (bwIdea.getBwIndex().equals(bwRoom.getHeadCount())) {
                    bwIdeaViewResponseDto.setIsLastComment(true);
                }
                break;
            }
        }
        log.info("아이디어 반환 종료");
        return bwIdeaViewResponseDto;
    }

    // 코멘트 입력
    @Transactional
    public BwCommentResponseDto insertComment(String bwRoomId, BwCommentRequestDto requestDto) {

        BwRoom bwRoom = findBwRoom(bwRoomId);

        User user = userService.findById(requestDto.getUserId());
        BwIdea bwIdea = bwIdeaRepository.findById(requestDto.getIdeaId()).orElseThrow(
                () -> new NotFoundException("해당 아이디어가 존재하지 않습니다.")
        );

        if (bwCommentsRepository.existsByBwIdeaAndUser(bwIdea, user)) {
            BwComments bwComments = bwCommentsRepository.findByBwIdeaAndUser(bwIdea, user);
            bwComments.setComments(requestDto.getComment());
            return new BwCommentResponseDto(bwComments.getBwIdea().getId(), bwComments.getUser().getId(), bwComments.getComments());
        } else {
            BwComments bwComments = new BwComments();
            bwComments.setComments(requestDto.getComment());
            bwComments.setUser(user);
            bwComments.setBwRoom(bwRoom);
            bwComments.setBwIdea(bwIdea);
            bwCommentsRepository.save(bwComments);
            return new BwCommentResponseDto(bwComments.getBwIdea().getId(), bwComments.getUser().getId(), bwComments.getComments());
        }

    }

    // 투표뷰 데이터 주기.
    public BwVoteViewResponseDto voteViewIdea(String bwRoomId) {

        BwRoom bwRoom = findBwRoom(bwRoomId);

        List<BwVoteViewCardsItem> bwVoteViewCardsItemList = new ArrayList<>();

        List<BwIdea> bwIdeaList = bwIdeaRepository.findAllByBwRoom(bwRoom);

        for (BwIdea bwIdea : bwIdeaList) {

            List<BwComments> bwCommentsList = bwIdea.getBwCommentsList();
            List<String> commentList = new ArrayList<>();
            for(BwComments bwComments:bwCommentsList) {
                commentList.add(bwComments.getComments());
            }

            BwVoteViewCardsItem bwVoteViewCardsItem = new BwVoteViewCardsItem();

            bwVoteViewCardsItem.setIdeaId(bwIdea.getId());
            bwVoteViewCardsItem.setIdea(bwIdea.getIdea());
            bwVoteViewCardsItem.setCommentList(commentList);

            bwVoteViewCardsItemList.add(bwVoteViewCardsItem);
        }
        BwVoteViewResponseDto bwVoteViewResponseDto = new BwVoteViewResponseDto();
        bwVoteViewResponseDto.setSubject(bwRoom.getSubject());
        bwVoteViewResponseDto.setIdeaList(bwVoteViewCardsItemList);

        return bwVoteViewResponseDto;
    }

    // 투표하기
    @Transactional
    public BwVoteResponseDto voteIdea(String bwRoomId, BwVoteRequestDto requestDto) {
        log.info("투표하기 시작");
        userService.isvote(requestDto.getUserId());

        for (Long votedIdeaId : requestDto.getVotedIdeaList()) {
            BwIdea bwIdea = bwIdeaRepository.findById(votedIdeaId).orElseThrow(
                    () -> new NotFoundException("찾는 아이디어가 없습니다.")
            );

            bwIdea.setNumberOfVotes(bwIdea.getNumberOfVotes() + 1);
        }

        BwRoom bwRoom = findBwRoom(bwRoomId);
        bwRoom.setPresentVoted(bwRoom.getPresentVoted() + 1);
        BwVoteResponseDto bwVoteResponseDto = new BwVoteResponseDto(bwRoom.getHeadCount(), bwRoom.getPresentVoted());
        log.info("투표하기 끝");
        return bwVoteResponseDto;
    }

    // 남은 시간 주기
    public BwTimersResponseDto getTime(String roomId) {
        BwRoom bwRoom = findBwRoom(roomId);

        LocalDateTime nowTime = LocalDateTime.now();
        log.info("nowTime seconds:{}", nowTime);
        LocalDateTime remainingTime = bwRoom.getTimer();
        log.info("remainingTime seconds:{}", remainingTime);
        long seconds = ChronoUnit.SECONDS.between(nowTime, remainingTime);

        Long remainingSec = seconds;

        log.info("남은시간 seconds:{}", seconds);
        log.info("남은시간 total:{}", remainingSec);

        return new BwTimersResponseDto(remainingSec);
    }

    // 갤러리에 데이터 저장.
    public void bwSaveGallery(String bwRoomId) {
        BwRoom bwRoom = findBwRoom(bwRoomId);

        GallerySaveResponseDto gallerySaveResponseDto = new GallerySaveResponseDto();
        gallerySaveResponseDto.setRoomId(bwRoom.getId());
        gallerySaveResponseDto.setCategory(Gallery.RoomType.brainwriting);
        gallerySaveResponseDto.setTitle(bwRoom.getTitle());
        gallerySaveResponseDto.setSubject(bwRoom.getSubject());

        galleryService.saveGallery(gallerySaveResponseDto);

    }

    // 브레인 라이팅 결과 데이터
    public BwResultResponseContainer getResult(String bwRoomId) {
        BwRoom bwRoom = findBwRoom(bwRoomId);

        // 아이디어 투표 수로 내림차순 정렬.
        List<BwIdea> bwIdeaList = bwIdeaRepository.findAllByBwRoom(bwRoom);
        bwIdeaList.sort((o1, o2) -> o2.getNumberOfVotes() - o1.getNumberOfVotes());

        List<BwResultResponseItem> bwResultResponseItemList = new ArrayList<>();
        for (BwIdea bwIdea : bwIdeaList) {
            // BwResultResponseItem 을 위한 코멘트 리스트 준비
            List<String> commentList = new ArrayList<>();
            for (BwComments bwComments : bwIdea.getBwCommentsList()) {
                commentList.add(bwComments.getComments());
            }

            // BwResultResponseDto 를 위한 아이디어 리스트 준비.
            BwResultResponseItem bwResultResponseItem = new BwResultResponseItem();
            bwResultResponseItem.setIdeaId(bwIdea.getId());
            bwResultResponseItem.setIdea(bwIdea.getIdea());
            bwResultResponseItem.setVoteCount(bwIdea.getNumberOfVotes());
            bwResultResponseItem.setCommentList(commentList);
            bwResultResponseItemList.add(bwResultResponseItem);
        }

        // isWinner에 1등 표시하기.
        int BigNum = 0;
        for (BwResultResponseItem bwResultResponseItem : bwResultResponseItemList) {
            if (bwResultResponseItem.getVoteCount() >= BigNum) {
                BigNum = bwResultResponseItem.getVoteCount();
                bwResultResponseItem.setIsWinner(true);
            } else bwResultResponseItem.setIsWinner(false);
        }


        // 최종적으로 반환할 dto 준비
        BwResultResponseDto bwResultResponseDto = new BwResultResponseDto();
        bwResultResponseDto.setSubject(bwRoom.getSubject());
        bwResultResponseDto.setVoteResult(bwResultResponseItemList);

        BwResultResponseContainer bwResultResponseContainer = new BwResultResponseContainer();
        bwResultResponseContainer.setCategory(Gallery.RoomType.brainwriting);
        bwResultResponseContainer.setData(bwResultResponseDto);
        return bwResultResponseContainer;
    }

    // 공유 여부 변경
    @Transactional
    public void shareCheck(String bwRoomId) {
        BwRoom bwRoom = findBwRoom(bwRoomId);
        bwRoom.setSharing(false);
        galleryService.deleteGallery(bwRoomId);
    }

    // 인원 1증가
    @Transactional
    public void plusUserCount(String roomId) {

        BwRoom bwRoom = findBwRoom(roomId);

        bwRoom.setCurrentUsers(bwRoom.getCurrentUsers() + 1);
    }

    // 인원 1 감소
    @Transactional
    public void minusUserCount(String roomId) {
        BwRoom bwRoom = findBwRoom(roomId);

        bwRoom.setCurrentUsers(bwRoom.getCurrentUsers() - 1);

    }

    // 브레인 라이팅 방 찾기
    public BwRoom findBwRoom(String roomId) {
        return bwRoomRepository.findById(roomId).orElseThrow(
                () -> new NotFoundException("해당 방이 존재하지 않습니다.")
        );
    }

    public String getEnterUserRoomId(String senderId) {
        return bwUserRoomRepository.findByUserId(Long.valueOf(senderId)).getBwroom().getId();
    }

    //  주제 저장하기
    @Transactional
    public void saveSubject(String bwRoomId, String subject) {
        BwRoom bwRoom = findBwRoom(bwRoomId);

        bwRoom.setSubject(subject);
    }

    // 아이디어 작성 및 코멘트 작성 시간 갱신하기
    @Transactional
    public BwTimersResponseDto renewTime(String bwRoomId) {
        BwRoom bwRoom = findBwRoom(bwRoomId);

        LocalDateTime localDateTime = LocalDateTime.now().plusMinutes(bwRoom.getTimes());
        log.info("localDateTime {}", localDateTime);
        bwRoom.setTimer(localDateTime);

        return new BwTimersResponseDto((long) bwRoom.getTimes() * 60);
    }

    // 투표 시간 갱신하기
    @Transactional
    public BwTimersResponseDto renewVoteTime(String bwRoomId) {
        BwRoom bwRoom = findBwRoom(bwRoomId);

        LocalDateTime localDateTime = null;
        switch (bwRoom.getHeadCount()) {
            case 2:
                localDateTime = LocalDateTime.now().plusMinutes(2);
                
                break;
            case 3:
                localDateTime = LocalDateTime.now().plusMinutes(4);
                
                break;
            case 4:
                localDateTime = LocalDateTime.now().plusMinutes(6);
                
                break;
            case 5:
                localDateTime = LocalDateTime.now().plusMinutes(8);
                
                break;
            case 6:
                localDateTime = LocalDateTime.now().plusMinutes(10);
                
                break;
            case 7:
                localDateTime = LocalDateTime.now().plusMinutes(12);
                
                break;
            case 8:
                localDateTime = LocalDateTime.now().plusMinutes(15);
                break;
        }
        bwRoom.setTimer(localDateTime);

//        return new BwTimersResponseDto((long) bwRoom.getTimes() * 60);
        return getTime(bwRoomId);
    }

    // 해당 방의 유저 리스트 넘기기.
    public List<UserListItem> getBwUserList(String bwRoomId) {
        BwRoom bwRoom = findBwRoom(bwRoomId);

        List<BwUserRoom> bwUserRoomList = bwUserRoomRepository.findAllByBwroom(bwRoom);

        List<UserListItem> userListItemList = new ArrayList<>();
        for (BwUserRoom bwUserRoom : bwUserRoomList) {
            UserListItem userListItem = new UserListItem();
            userListItem.setNickname(bwUserRoom.getUser().getNickname());

            userListItemList.add(userListItem);
        }
        return userListItemList;
    }
}
