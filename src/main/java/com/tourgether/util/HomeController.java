package com.tourgether.util;

import com.tourgether.dto.MemberSessionResponseDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(@SessionAttribute(value = SessionConstants.LOGIN_MEMBER, required = false) MemberSessionResponseDto loginMember) {
        if (loginMember == null) {
            return "home";
        }

        return "loginHome";
    }
}
