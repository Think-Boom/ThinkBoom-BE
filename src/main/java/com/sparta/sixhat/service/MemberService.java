package com.sparta.sixhat.service;


import com.sparta.sixhat.dto.MemberNicknameRequestDto;
import com.sparta.sixhat.model.Member;
import com.sparta.sixhat.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Member findById(Long id) {
        return memberRepository.findById(id).orElseThrow(
                ()-> new IllegalArgumentException("존재하지 않는 유저입니다.")
        );
    }


//    @Transactional
//    public Member addMember(MemberNicknameRequestDto requestDto) {
//
//        Member member = Member.builder()
//                .nickname(requestDto.getNickname())
//                .build();
//
//        return memberRepository.save(member);
//    }

    public Long register(MemberNicknameRequestDto requestDto) {

        Member member = new Member(requestDto.getNickname());
        memberRepository.save(member);
        return member.getId();
    }


}
