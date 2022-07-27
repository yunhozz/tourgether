package com.tourgether.api;

import com.tourgether.domain.member.service.OAuth2UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MemberApiController {

    private final OAuth2UserServiceImpl oAuth2UserService;

    @GetMapping("/kakao")
    public void kakaoCallback(@RequestParam String code) throws IOException {
        String accessToken = oAuth2UserService.getKakaoAccessToken(code);
        oAuth2UserService.createKakaoUser(accessToken);
    }
}
