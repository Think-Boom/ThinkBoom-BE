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
import com.steepcliff.thinkboom.brainWriting.dto.bwResult.BwResultResponseComments;
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
import com.steepcliff.thinkboom.gallery.Gallery;
import com.steepcliff.thinkboom.gallery.GallerySaveResponseDto;
import com.steepcliff.thinkboom.gallery.GalleryService;
import com.steepcliff.thinkboom.user.User;
import com.steepcliff.thinkboom.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

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

        // 시간 구하기
        LocalDateTime localDateTime = LocalDateTime.now().plusMinutes(requestDto.getTime());

        BwRoom bwRoom = new BwRoom(requestDto.getTitle(), requestDto.getHeadCount(), localDateTime, 0, true);

        bwRoomRepository.save(bwRoom);

        return new BwRoomResponseDto(bwRoom.getId(),requestDto.getHeadCount(), requestDto.getTime());
    }

    // 브레인 라이팅 닉네임 입력.
    @Transactional
    public BwNickResponseDto createNickname(String bwRoomId, BwNickRequestDto requestDto) {

        BwRoom bwRoom = findBwRoom(bwRoomId);

        User user = userService.save(requestDto.getNickname());

        if(bwRoom.getHostId() == null) {
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
        for(BwUserRoom bwUserRoom:userRoomList) {
            userQueue.add(bwUserRoom.getUser());
        }
        List<BwIdea> bwIdeaList = new ArrayList<>();
        for(int i=0; i<bwRoom.getHeadCount(); i++) {
            StringBuilder sequence = new StringBuilder();
            for(User user : userQueue) {
                sequence.append(user.getId());
                sequence.append(":");
            }
            User user = userQueue.poll();
            userQueue.add(user);

            BwIdea bwIdea = new BwIdea(user, sequence.toString(),bwRoom, 1, 0);

            bwIdeaList.add(bwIdea);
        }
        List<BwIdea> bwIdeaListTest =  bwIdeaRepository.saveAll(bwIdeaList);

        // 테스트용
//        List<BwIdea> bwIdeaListTest = bwIdeaRepository.findAllByBwRoom(bwRoom);
        for (BwIdea bwIdea : bwIdeaListTest) {
            log.info("{} {}", bwIdea.getId(),bwIdea.getBwSequence());
        }
    }

    // 브레인 라이팅 아이디어 입력
    @Transactional
    public BwIdeaResponseDto insertIdea(String bwRoomId, BwIdeaRequestDto requestDto) {
        BwRoom bwRoom = findBwRoom(bwRoomId);
        User user = userService.findById(requestDto.getUserId());

        BwIdea bwIdea = bwIdeaRepository.findByUser(user);
        log.info(requestDto.getIdea());
        bwIdea.setIdea(requestDto.getIdea());

        return new  BwIdeaResponseDto(user.getId(),bwIdea.getIdea());
    }

    // 브레인 라이팅 아이디어 목록 반환
    public BwIdeaListDto getAllIdeaWithOrederBy(String bwRoomId) {

        BwRoom bwRoom = findBwRoom(bwRoomId);

        List<BwIdea> bwIdeaList = bwIdeaRepository.findAllByBwRoom(bwRoom);
        List<BwIdeaListItem> bwIdeaListItemList = new ArrayList<>();

        boolean endComment = false;
        for(BwIdea bwIdea:bwIdeaList) {
            BwIdeaListItem bwIdeaListItem = new BwIdeaListItem();

            String[] strings = bwIdea.getBwSequence().split(":");

            bwIdeaListItem.setViewUserId(Long.valueOf(strings[bwIdea.getBwIndex()]));
            bwIdeaListItem.setIdeaId(bwIdea.getId());
            bwIdeaListItem.setIdea(bwIdea.getIdea());
            bwIdeaListItemList.add(bwIdeaListItem);

            bwIdea.setBwIndex(bwIdea.getBwIndex()+1);
            bwIdeaRepository.save(bwIdea);
            if(bwIdea.getBwIndex().equals(bwRoom.getHeadCount())) {
                endComment = true;
            }
        }
        return new BwIdeaListDto(endComment, bwIdeaListItemList);
    }

    // 코멘트 입력
    public BwCommentResponseDto insertComment(String bwRoomId, BwCommentRequestDto requestDto) {
        BwRoom bwRoom = findBwRoom(bwRoomId);
        User user =userService.findById(requestDto.getUserId());
        BwIdea bwIdea = bwIdeaRepository.findById(requestDto.getIdeaId()).orElseThrow(
                () -> new NullPointerException("해당 아이디어가 존재하지 않습니다.")
        );

        BwComments bwComments = new BwComments();
        bwComments.setComments(requestDto.getComment());
        bwComments.setUser(user);
        bwComments.setBwRoom(bwRoom);
        bwComments.setBwIdea(bwIdea);

        bwCommentsRepository.save(bwComments);

        return new BwCommentResponseDto(bwComments.getBwIdea().getId(), bwComments.getUser().getId(), bwComments.getComments());
    }

    // 투표뷰 데이터 주기.
    public BwVoteViewResponseDto voteViewIdea(String bwRoomId) {

        BwRoom bwRoom = findBwRoom(bwRoomId);

        List<BwVoteViewCardsItem> bwVoteViewCardsItemList = new ArrayList<>();

        List<BwIdea> bwIdeaList = bwIdeaRepository.findAllByBwRoom(bwRoom);

        for(BwIdea bwIdea : bwIdeaList) {

            List<BwComments> bwCommentsList = bwCommentsRepository.findAllByBwIdea(bwIdea);

            BwVoteViewCardsItem bwVoteViewCardsItem = new BwVoteViewCardsItem();

            bwVoteViewCardsItem.setIdeaId(bwIdea.getId());
            bwVoteViewCardsItem.setIdea(bwIdea.getIdea());
            bwVoteViewCardsItem.setCommentsList(bwCommentsList);

            bwVoteViewCardsItemList.add(bwVoteViewCardsItem);
        }
        return new BwVoteViewResponseDto(bwRoom.getSubject(), bwVoteViewCardsItemList);
    }

    // 투표하기
    @Transactional
    public BwVoteResponseDto voteIdea(String bwRoomId, BwVoteRequestDto requestDto) {

        userService.isvote(requestDto.getUserId());

        for(Long votedIdeaId : requestDto.getVotedIdeaList()) {
            BwIdea bwIdea = bwIdeaRepository.findById(votedIdeaId).orElseThrow(
                    () -> new NullPointerException("찾는 아이디어가 없습니다.")
            );
            bwIdea.setNumberOfVotes(bwIdea.getNumberOfVotes()+1);
        }

        BwRoom bwRoom = findBwRoom(bwRoomId);

        bwRoom.setPresentVoted(bwRoom.getPresentVoted()+1);

        return new BwVoteResponseDto(bwRoom.getHeadCount(), bwRoom.getPresentVoted());
    }

    // 남은 시간 주기
    public BwTimersResponseDto getTime(String roomId) {
        BwRoom bwRoom = findBwRoom(roomId);

        LocalDateTime nowTime = LocalDateTime.now();

        LocalDateTime remainingTime = bwRoom.getTimer();

        long hours = ChronoUnit.HOURS.between(remainingTime, nowTime);
        long minutes = ChronoUnit.MINUTES.between(remainingTime, nowTime);
        long seconds = ChronoUnit.MINUTES.between(remainingTime, nowTime);

        Long remainingSec = hours*3600 + minutes*60 + seconds;

        return new BwTimersResponseDto(remainingSec);
    }

    // 갤러리에 데이터 저장.
    public void bwSaveGallery(String bwRoomId) {
        BwRoom bwRoom = findBwRoom(bwRoomId);

        GallerySaveResponseDto gallerySaveResponseDto = new GallerySaveResponseDto();
        gallerySaveResponseDto.setRoomId(bwRoom.getId());
        gallerySaveResponseDto.setType(Gallery.RoomType.BW);
        gallerySaveResponseDto.setTitle(bwRoom.getTitle());
        gallerySaveResponseDto.setSubject(bwRoom.getSubject());

        galleryService.saveGallery(gallerySaveResponseDto);

    }

    // 브레인 라이팅 결과 데이터
   public BwResultResponseDto getResult(String bwRoomId) {
        BwRoom bwRoom = findBwRoom(bwRoomId);
        List<BwIdea> bwIdeaList = bwIdeaRepository.findAllByBwRoom(bwRoom);

       List<BwResultResponseItem> bwResultResponseItemList = new ArrayList<>();
       for(BwIdea bwIdea:bwIdeaList) {
           // BwResultResponseItem 을 위한 코멘트 리스트 준비
           List<BwResultResponseComments> bwResultResponseCommentsList = new ArrayList<>();
           for(BwComments bwComments:bwIdea.getBwCommentsList()) {
               BwResultResponseComments bwResultResponseComments = new BwResultResponseComments();
               bwResultResponseComments.setNickname(bwComments.getUser().getNickname());
               bwResultResponseComments.setComment(bwComments.getComments());
               bwResultResponseCommentsList.add(bwResultResponseComments);
           }

           // BwResultResponseDto 를 위한 아이디어 리스트 준비.
           BwResultResponseItem bwResultResponseItem = new BwResultResponseItem();
           bwResultResponseItem.setIdeaId(bwIdea.getId());
           bwResultResponseItem.setNickname(bwIdea.getUser().getNickname());
           bwResultResponseItem.setIdea(bwIdea.getIdea());
           bwResultResponseItem.setVoteCount(bwIdea.getNumberOfVotes());
           bwResultResponseItem.setCommentsList(bwResultResponseCommentsList);
           bwResultResponseItemList.add(bwResultResponseItem);
       }
       // 최종적으로 반환할 dto 준비
       BwResultResponseDto bwResultResponseDto = new BwResultResponseDto();
       bwResultResponseDto.setSubject(bwRoom.getSubject());
       bwResultResponseDto.setResult(bwResultResponseItemList);

       return bwResultResponseDto;
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

        bwRoom.setCurrentUsers(bwRoom.getCurrentUsers() -1);

    }

    // 브레인 라이팅 방 찾기
    public BwRoom findBwRoom(String roomId) {
        return bwRoomRepository.findById(roomId).orElseThrow(
                () -> new NullPointerException("해당 방이 존재하지 않습니다.")
        );
    }

    public String getEnterUserRoomId(String senderId) {
        return bwUserRoomRepository.findByUserId(Long.valueOf(senderId)).getBwroom().getId();
    }
}
