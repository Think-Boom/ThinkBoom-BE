package com.steepcliff.thinkboom.brainWriting.service;

import com.steepcliff.thinkboom.brainWriting.domain.BwIdea;
import com.steepcliff.thinkboom.brainWriting.domain.BwRoom;
import com.steepcliff.thinkboom.brainWriting.domain.BwUserRoom;
import com.steepcliff.thinkboom.brainWriting.dto.*;
import com.steepcliff.thinkboom.brainWriting.dto.bwComment.BwCommentRequestDto;
import com.steepcliff.thinkboom.brainWriting.dto.bwComment.BwCommentResponseDto;
import com.steepcliff.thinkboom.brainWriting.dto.bwIdea.BwIdeaRequestDto;
import com.steepcliff.thinkboom.brainWriting.dto.bwIdea.BwIdeaResponseDto;
import com.steepcliff.thinkboom.brainWriting.dto.bwVote.BwVoteRequestDto;
import com.steepcliff.thinkboom.brainWriting.dto.bwVote.BwVoteResponseDto;
import com.steepcliff.thinkboom.brainWriting.dto.bwVoteView.BwVoteViewCardsItem;
import com.steepcliff.thinkboom.brainWriting.dto.bwVoteView.BwVoteViewResponseDto;
import com.steepcliff.thinkboom.brainWriting.repository.BwIdeaRepository;
import com.steepcliff.thinkboom.brainWriting.repository.BwRoomRepository;
import com.steepcliff.thinkboom.brainWriting.repository.BwUserRoomRepository;
import com.steepcliff.thinkboom.user.User;
import com.steepcliff.thinkboom.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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

    @Autowired
    public BwService(BwRoomRepository bwRoomRepository, BwUserRoomRepository bwUserRoomRepository, UserService userService, BwIdeaRepository bwIdeaRepository) {
        this.bwRoomRepository = bwRoomRepository;
        this.bwUserRoomRepository = bwUserRoomRepository;
        this.userService = userService;
        this.bwIdeaRepository = bwIdeaRepository;
    }

    // 브레인 라이팅 방 생성.
    public BwRoomResponseDto createBwRoom(BwRoomRequestDto requestDto) {

        BwRoom bwRoom = new BwRoom(requestDto.getHeadCount(), requestDto.getTime());

        bwRoomRepository.save(bwRoom);

        return new BwRoomResponseDto(bwRoom.getId(), requestDto.getHeadCount(), requestDto.getTime());
    }

    // 브레인 라이팅 닉네임 입력.
    @Transactional
    public BwNickResponseDto createNickname(BwNickRequestDto requestDto) {

        BwRoom room = bwRoomRepository.findById(Long.valueOf(requestDto.getRoomId())).orElseThrow(
                () -> new NullPointerException()
        );

        User user = userService.save(requestDto.getNickname());

        if(room.getHostId() == null) {
            room.setHostId(user.getId());
        }

        bwUserRoomRepository.save(new BwUserRoom(user, room));

        return new BwNickResponseDto(user.getId(), user.getNickname());
    }

    public void createIdea(Long bwRoomId) {
        BwRoom bwRoom = bwRoomRepository.findById(bwRoomId).orElseThrow(
                () -> new NullPointerException()
        );

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

            BwIdea bwIdea = new BwIdea(user, sequence.toString(), false,bwRoom);

            bwIdeaList.add(bwIdea);
        }
        List<BwIdea> bwIdeaListTest =  bwIdeaRepository.saveAll(bwIdeaList);

        // 테스트용
//        List<BwIdea> bwIdeaListTest = bwIdeaRepository.findAllByBwRoom(bwRoom);
        for (BwIdea bwIdea : bwIdeaListTest) {
            log.info("{} {}", bwIdea.getId(),bwIdea.getSequence());
        }
    }

    // 브레인 라이팅 아이디어 입력
    @Transactional
    public BwIdeaResponseDto insertIdea(Long bwRoomId, BwIdeaRequestDto requestDto) {
        BwRoom bwRoom = bwRoomRepository.findById(bwRoomId).orElseThrow(
                () -> new NullPointerException()
        );
        User user = userService.findById(requestDto.getUserId());

        BwIdea bwIdea = bwIdeaRepository.findByUser(user);
        log.info(requestDto.getIdea());
        bwIdea.setIdea(requestDto.getIdea());
        bwIdeaRepository.save(bwIdea);

        return new  BwIdeaResponseDto(user.getId(),bwIdea.getIdea());
    }

    // 브레인 라이팅 아이디어 목록 반환
    public BwIdeaListDto getAllIdeaWithOrederBy(Long bwRoomId) {

        BwRoom bwRoom = bwRoomRepository.findById(bwRoomId).orElseThrow(
                () -> new NullPointerException("해당 방이 존재하지 않습니다.")
        );

        List<BwIdea> bwIdeaList = bwIdeaRepository.findAllByBwRoomAndIsComment(bwRoom, false);
        List<BwIdeaListItem> bwIdeaListItemList = new ArrayList<>();

        Boolean endComment = false;
        for(BwIdea bwIdea:bwIdeaList) {
            BwIdeaListItem bwIdeaListItem = new BwIdeaListItem();

            String[] strings = bwIdea.getSequence().split(":");

            bwIdeaListItem.setViewUserId(Long.valueOf(strings[bwIdea.getIndex()]));
            bwIdeaListItem.setIdeaId(bwIdea.getId());
            bwIdeaListItem.setIdea(bwIdea.getIdea());
            bwIdeaListItemList.add(bwIdeaListItem);

            bwIdea.setIndex(bwIdea.getIndex()+1);
            bwIdeaRepository.save(bwIdea);
            if(bwIdea.getIndex().equals(bwRoom.getHeadCount())) {
                endComment = true;
            }
        }
        return new BwIdeaListDto(endComment, bwIdeaListItemList);
    }

    // 코멘트 입력
    public BwCommentResponseDto insertComment(Long bwRoomId, BwCommentRequestDto requestDto) {
        BwRoom bwRoom = bwRoomRepository.findById(bwRoomId).orElseThrow(
                () -> new NullPointerException("해당 방이 존재하지 않습니다.")
        );
        User user =userService.findById(requestDto.getUserId());
        BwIdea parentsBwIdea = bwIdeaRepository.findById(requestDto.getIdeaId()).orElseThrow(
                () -> new NullPointerException("해당 아이디어가 존재하지 않습니다.")
        );

        BwIdea bwIdea = new BwIdea();
        bwIdea.setIdea(requestDto.getComment());
        bwIdea.setUser(user);
        bwIdea.setBwRoom(bwRoom);
        bwIdea.setBwIdea(parentsBwIdea);
        bwIdea.setIsComment(true);
        bwIdeaRepository.save(bwIdea);

        return new BwCommentResponseDto(bwIdea.getId(), bwIdea.getUser().getId(), bwIdea.getIdea());
    }

    // 투표뷰 데이터 주기.
    public BwVoteViewResponseDto voteViewIdea(Long bwRoomId) {

        BwRoom bwRoom = bwRoomRepository.findById(bwRoomId).orElseThrow(
                ()-> new NullPointerException()
        );

        List<BwVoteViewCardsItem> bwVoteViewCardsItemList = new ArrayList<>();

        List<BwIdea> bwIdeaList = bwIdeaRepository.findAllByBwRoomAndIsComment(bwRoom, false);

        for(BwIdea bwIdea : bwIdeaList) {

            List<BwIdea> bwCommentList = bwIdeaRepository.findAllByBwIdeaAndIsComment(bwIdea, true);
            List<String> bwCommentStringList = new ArrayList<>();
            for(BwIdea bwComment : bwCommentList) {
                bwCommentStringList.add(bwComment.getIdea());
            }


            BwVoteViewCardsItem bwVoteViewCardsItem = new BwVoteViewCardsItem();

            bwVoteViewCardsItem.setIdeaId(bwIdea.getId());
            bwVoteViewCardsItem.setIdea(bwIdea.getIdea());
            bwVoteViewCardsItem.setCommentsList(bwCommentStringList);

            bwVoteViewCardsItemList.add(bwVoteViewCardsItem);
        }
        return new BwVoteViewResponseDto(bwRoom.getSubject(), bwVoteViewCardsItemList);
    }

    // 투표하기
    @Transactional
    public BwVoteResponseDto voteIdea(Long bwRoomId, BwVoteRequestDto requestDto) {

        userService.isvote(requestDto.getUserId());

        for(Long votedIdeaId : requestDto.getVotedIdeaList()) {
            BwIdea bwIdea = bwIdeaRepository.findById(votedIdeaId).orElseThrow(
                    () -> new NullPointerException("찾는 아이디어가 없습니다.")
            );
            bwIdea.setNumberOfVotes(bwIdea.getNumberOfVotes()+1);
        }

        BwRoom bwRoom = bwRoomRepository.findById(bwRoomId).orElseThrow(
                () -> new NullPointerException("찾는 브레인라이팅 방이 없습니다.")
        );

        bwRoom.setPresentVoted(bwRoom.getPresentVoted()+1);
        bwRoomRepository.save(bwRoom);


        return new BwVoteResponseDto(bwRoom.getHeadCount(), bwRoom.getPresentVoted());
    }


}
