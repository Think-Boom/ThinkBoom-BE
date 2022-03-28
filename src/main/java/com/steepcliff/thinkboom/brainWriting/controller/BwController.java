package com.steepcliff.thinkboom.brainWriting.controller;

import com.steepcliff.thinkboom.brainWriting.dto.*;
import com.steepcliff.thinkboom.brainWriting.dto.bwComment.BwCommentRequestDto;
import com.steepcliff.thinkboom.brainWriting.dto.bwComment.BwCommentResponseDto;
import com.steepcliff.thinkboom.brainWriting.dto.bwIdea.BwIdeaRequestDto;
import com.steepcliff.thinkboom.brainWriting.dto.bwIdea.BwIdeaResponseDto;
import com.steepcliff.thinkboom.brainWriting.dto.bwNickname.BwNickRequestDto;
import com.steepcliff.thinkboom.brainWriting.dto.bwNickname.BwNickResponseDto;
import com.steepcliff.thinkboom.brainWriting.dto.bwRoom.BwRoomRequestDto;
import com.steepcliff.thinkboom.brainWriting.dto.bwRoom.BwRoomResponseDto;
import com.steepcliff.thinkboom.brainWriting.dto.bwVote.BwVoteRequestDto;
import com.steepcliff.thinkboom.brainWriting.dto.bwVote.BwVoteResponseDto;
import com.steepcliff.thinkboom.brainWriting.dto.bwVoteView.BwVoteViewResponseDto;
import com.steepcliff.thinkboom.brainWriting.service.BwService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


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
    @PostMapping("/user/nickname/{bwRoomId}")
    public BwNickResponseDto inputNickname(@PathVariable String bwRoomId , @RequestBody BwNickRequestDto requestDto) {
        return bwService.createNickname(bwRoomId, requestDto);
    }

    // 브레인라이팅 아이디어 입력
    @PostMapping("/idea/{bwRoomId}")
    public BwIdeaResponseDto idea(@PathVariable String bwRoomId, @RequestBody BwIdeaRequestDto requestDto) {
        return bwService.insertIdea(bwRoomId, requestDto);
    }

    // 브레인 라이팅 코멘트 입력.
    @PostMapping("/comment/{bwRoomId}")
    public BwCommentResponseDto comment(@PathVariable String bwRoomId, @RequestBody BwCommentRequestDto requestDto) {
        return bwService.insertComment(bwRoomId,requestDto);
    }

    // 브레인 라이팅 아이디어 클라이언트에 전달.
    @GetMapping("/idea/{bwRoomId}")
    public BwIdeaListDto getCards(@PathVariable String bwRoomId) {
        return bwService.getAllIdeaWithOrederBy(bwRoomId);
    }

    @GetMapping("/voteview/{bwRoomId}")
    public BwVoteViewResponseDto voteView(@PathVariable String bwRoomId) {
        return bwService.voteViewIdea(bwRoomId);
    }

    @PostMapping("/vote/{bwRoomId}")
    public BwVoteResponseDto voting(@PathVariable String bwRoomId, @RequestBody BwVoteRequestDto requestDto, @PathVariable String BwRoomId) {
        return bwService.voteIdea(bwRoomId, requestDto);
    }

    // 갤래리를 위해 갤러리 db에 저장
    @PostMapping("/gallery/save/{bwRoomId}")
    public void saveGallery(@PathVariable String bwRoomId) {
        bwService.bwSaveGallery(bwRoomId);
    }

    // 아이디어 카드 생성.
    @GetMapping("/idea/create/{bwRoomId}")
    public void createIdea(@PathVariable String bwRoomId) {
        bwService.createIdea(bwRoomId);
    }

    // 공유여부 변경 true->false
    @PostMapping("/sharing/{bwRoomId}")
    public void BwSharingCheck(@PathVariable String bwRoomId) {
        bwService.shareCheck(bwRoomId);
    }

    // 남은 시간 주기
    @GetMapping("/timer/{bwroomid}")
    public BwTimersResponseDto remainingTime(@PathVariable String bwroomid) {
        return bwService.getTime(bwroomid);
    }
}
