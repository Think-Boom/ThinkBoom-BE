package com.sparta.sixhat.Initial;

import com.sparta.sixhat.model.ChatRoom;
import com.sparta.sixhat.repository.ChatRoomRepository;
import com.sparta.sixhat.model.Member;
import com.sparta.sixhat.repository.ChatRoomRepository;
import com.sparta.sixhat.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class InitialData implements ApplicationRunner {
    private final MemberRepository memberRepository;
    private final ChatRoomRepository chatRoomRepository;

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {
//        String password = bCryptPasswordEncoder.encode("123456");
//        Member user = new Member("123@gmail.com", "익명의 프로그래머",password );
        ChatRoom chatRoom = new ChatRoom("test",2L, 1L);

        Member member = new Member("test");

        memberRepository.save(member);

        chatRoomRepository.save(chatRoom);
    }
}
