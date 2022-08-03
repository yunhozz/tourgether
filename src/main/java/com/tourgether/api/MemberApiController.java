package com.tourgether.api;

import com.tourgether.domain.member.controller.form.LoginForm;
import com.tourgether.domain.member.model.dto.request.MemberRequestDto;
import com.tourgether.domain.member.model.dto.response.MemberResponseDto;
import com.tourgether.domain.member.service.MemberService;
import com.tourgether.domain.member.service.UserDetailsServiceImpl;
import com.tourgether.util.SessionConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;
    private final UserDetailsServiceImpl userDetailsService;
    private final HttpSession session;

    @GetMapping("/members")
    public List<MemberResponseDto> findMembers() {
        return memberService.findMemberDtoList();
    }

    @GetMapping("/member/signup")
    public ResponseEntity<MemberResponseDto> signup(@Valid MemberRequestDto memberRequestDto) {
        Long memberId = memberService.join(memberRequestDto);
        return ResponseEntity.ok(memberService.findMemberDto(memberId));
    }

    @PostMapping("/member/login")
    public ResponseEntity<Object> login(@Valid LoginForm loginForm) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(loginForm.getEmail());
        memberService.login(userDetails, loginForm.getPassword());

        return ResponseEntity.ok(session.getAttribute(SessionConstants.LOGIN_MEMBER));
    }
}
