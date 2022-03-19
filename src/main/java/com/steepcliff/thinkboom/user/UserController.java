package com.steepcliff.thinkboom.user;

import com.steepcliff.thinkboom.brainWriting.dto.BwNickRequestDto;
import com.steepcliff.thinkboom.brainWriting.dto.BwNickResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }



    // 테스트용
    @GetMapping("/user/{userId}")
    public String getUserNick(@PathVariable Long userId) {
        return userService.getUseId(userId);
    }

}
