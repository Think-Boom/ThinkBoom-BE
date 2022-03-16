package com.sparta.sixhat.controller;


import com.sparta.sixhat.dto.MemberNicknameRequestDto;
import com.sparta.sixhat.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


//@CrossOrigin("*")
@RequiredArgsConstructor
@RestController
public class MemberController {

    private final MemberService memberService;


    //멤버유저등록
    @PostMapping("/nickname/member")//
    public ResponseEntity<?> saveNickname(@RequestBody MemberNicknameRequestDto requestDto) {
    //public Member saveNickname(@RequestBody MemberNicknameRequestDto requestDto) {
        Long memberId = memberService.register(requestDto);

        return ResponseEntity.ok().body(memberId);
        //return memberService.addMember(requestDto);

    }

//    @PostMapping("/timer/member")//
//    public ResponseEntity<?> saveEnter(@RequestBody TimeRequestDto requestDto) {
//        //public Member saveNickname(@RequestBody MemberNicknameRequestDto requestDto) {
//        Long memberId = memberService.register(requestDto);
//
//        return ResponseEntity.ok().body("SUCCESS!");
//    }
    //방장유저등록
//    @PostMapping("/nickname/chief")//
//    public void addChiefNickname(@PathVariable String nickname, @RequestBody) {
////        memberService.addChief(restaurantId, requestDtoList);
//    }
    //모자색깔변경
//    @PostMapping("/hat")//
//    public HatResponseDto selectHat(@RequestBody HatRequsetDto requestDto) {
//        return memberService.updateMember(requestDto);

}
