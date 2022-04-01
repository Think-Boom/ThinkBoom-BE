package com.steepcliff.thinkboom.user;

import com.steepcliff.thinkboom.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(
                ()-> new NotFoundException("찾는 유저가 없습니다")
        );
    }
    @Transactional
    public User save(String nickname) {

        return userRepository.save(new User(nickname));
    }

    // 투표 여부 업데이트
    @Transactional
    public void isvote(Long userId) {
        User user = findById(userId);

        user.setIsVote(true);
    }

    // 모자 정보 저장
    @Transactional
    public void saveHat(Long userId, String hat) {
        User user = findById(userId);

        user.setHat(hat);
    }

    // 모자 정보 찾기
    public String getHat(Long userId) {
        User user = findById(userId);

        return user.getHat();
    }

    // 아이디가 잘 입력되었는지 테스트용
    public String getUseId(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                ()-> new NotFoundException("찾는 유저가 없습니다.")
        );

        return user.getNickname();
    }

}
