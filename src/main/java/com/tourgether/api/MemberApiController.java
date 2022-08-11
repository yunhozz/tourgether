package com.tourgether.api;

import com.tourgether.domain.member.model.repository.MemberRepository;
import com.tourgether.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.tourgether.dto.MemberDto.*;
import static com.tourgether.dto.TokenDto.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;
    private final MemberRepository memberRepository;

    @GetMapping("/member/{userId}")
    public MemberResponseDto getMember(@PathVariable String userId) {
        return memberService.findMemberDto(Long.valueOf(userId));
    }

    @GetMapping("/member/list")
    public List<MemberResponseDto> getMembers() {
        return memberService.findMemberDtoList();
    }

    @PatchMapping("/member/{userId}/update-info")
    public MemberResponseDto updateMemberInfo(@PathVariable String userId, @RequestBody UpdateForm updateForm) {
        memberService.updateInfo(Long.valueOf(userId), updateForm.getName(), updateForm.getNickname(), updateForm.getProfileUrl());
        return memberService.findMemberDto(Long.valueOf(userId));
    }

    @PatchMapping("/member/{userId}/update-password")
    public MemberResponseDto updateMemberPassword(@PathVariable String userId, @RequestBody PasswordForm passwordForm) {
        memberService.updatePassword(Long.valueOf(userId), passwordForm.getOriginalPw(), passwordForm.getNewPw());
        return memberService.findMemberDto(Long.valueOf(userId));
    }

    @DeleteMapping("/member/{userId}/delete")
    public List<MemberResponseDto> deleteMember(@PathVariable String userId, @RequestParam String password) {
        memberService.withdraw(Long.valueOf(userId), password);
        return memberService.findMemberDtoList();
    }

    @PostMapping("/member/signup")
    public ResponseEntity<Long> signup(@RequestBody MemberRequestDto memberRequestDto) {
        return ResponseEntity.ok(memberService.join(memberRequestDto));
    }

    @PostMapping("/member/login")
    public ResponseEntity<UserDetails> login(@RequestBody LoginForm loginForm) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(loginForm.getEmail()); // throws UsernameNotFoundException
        memberService.login(userDetails, loginForm.getPassword()); // throws PasswordMismatchException

        return ResponseEntity.ok(userDetails);
    }
}
