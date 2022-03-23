package com.steepcliff.thinkboom.brainWriting.controller;

import com.steepcliff.thinkboom.brainWriting.dto.*;
import com.steepcliff.thinkboom.brainWriting.dto.bwComment.BwCommentRequestDto;
import com.steepcliff.thinkboom.brainWriting.dto.bwComment.BwCommentResponseDto;
import com.steepcliff.thinkboom.brainWriting.dto.bwIdea.BwIdeaRequestDto;
import com.steepcliff.thinkboom.brainWriting.dto.bwIdea.BwIdeaResponseDto;
import com.steepcliff.thinkboom.brainWriting.dto.bwNick.BwNickRequestDto;
import com.steepcliff.thinkboom.brainWriting.dto.bwNick.BwNickResponseDto;
import com.steepcliff.thinkboom.brainWriting.dto.bwRoom.BwRoomRequestDto;
import com.steepcliff.thinkboom.brainWriting.dto.bwRoom.BwRoomResponseDto;
import com.steepcliff.thinkboom.brainWriting.dto.bwVote.BwVoteRequestDto;
import com.steepcliff.thinkboom.brainWriting.dto.bwVote.BwVoteResponseDto;
import com.steepcliff.thinkboom.brainWriting.dto.bwVoteView.BwVoteViewResponseDto;
import com.steepcliff.thinkboom.brainWriting.service.BwService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
@RequestMapping("/api/brainwriting")
public class BwController {

    private final BwService bwService;

    @Autowired
    public BwController(BwService bwService) {
        this.bwService = bwService;
    }

    // 브레인라이팅 방 만들기.
    @PostMapping("/rooms")
    public BwRoomResponseDto createBwRoomController(@RequestBody BwRoomRequestDto requestDto) {
        return bwService.createBwRoom(requestDto);
    }

    // 브레인라이팅 닉네임 입력
    @PostMapping("/user/nickname/{bwroomid}")
    public BwNickResponseDto inputNickname(@PathVariable String bwroomid, @RequestBody BwNickRequestDto requestDto) {
        return bwService.createNickname(bwroomid, requestDto);
    }

    // 브레인라이팅 아이디어 입력
    @PostMapping("/idea/{bwroomid}")
    public BwIdeaResponseDto idea(@PathVariable String bwroomid, @RequestBody BwIdeaRequestDto requestDto) {
        return bwService.insertIdea(bwroomid, requestDto);
    }

    // 브레인 라이팅 코멘트 입력.
    @PostMapping("/comment/{bwroomid}")
    public BwCommentResponseDto comment(@PathVariable String bwroomid, @RequestBody BwCommentRequestDto requestDto) {
        return bwService.insertComment(bwroomid,requestDto);
    }

    // 브레인 라이팅 아이디어 클라이언트에 전달.
    @GetMapping("/idea/{bwroomid}")
    public BwIdeaListDto getCards(@PathVariable String bwroomid) {
        return bwService.getAllIdeaWithOrederBy(bwroomid);
    }

    @GetMapping("/voteView/{bwroomid}")
    public BwVoteViewResponseDto voteView(@PathVariable String bwroomid) {
        return bwService.voteViewIdea(bwroomid);
    }

    @PostMapping("/vote/{bwroomid}")
    public BwVoteResponseDto voting(@PathVariable String bwroomid, @RequestBody BwVoteRequestDto requestDto, @PathVariable String BwRoomId) {
        return bwService.voteIdea(bwroomid, requestDto);
    }

    // 아이디어 카드 생성.
    @GetMapping("/idea/create/{bwroomid}")
    public void createIdea(@PathVariable String bwroomid) {

        bwService.createIdea(bwroomid);
    }

    // 남은 시간 주기
    @GetMapping("/timer/{bwroomid}")
    public BwTimersResponseDto remainingTime(@PathVariable String bwroomid) {
        return bwService.getTime(bwroomid);
    }
}
