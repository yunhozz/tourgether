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
    public ResponseEntity<MemberResponseDto> getMember(@PathVariable String userId) {
        return ResponseEntity.ok(memberService.findMemberDto(Long.valueOf(userId)));
    }

    @GetMapping("/member/list")
    public ResponseEntity<List<MemberResponseDto>> getMembers() {
        return ResponseEntity.ok(memberService.findMemberDtoList());
    }

    @PatchMapping("/member/update-info")
    public ResponseEntity<Void> updateMemberInfo(@RequestParam String userId, @RequestBody UpdateForm updateForm) {
        memberService.updateInfo(Long.valueOf(userId), updateForm.getName(), updateForm.getNickname(), updateForm.getProfileUrl());
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/member/update-password")
    public ResponseEntity<Void> updateMemberPassword(@RequestParam String userId, @RequestBody PasswordForm passwordForm) {
        memberService.updatePassword(Long.valueOf(userId), passwordForm.getOriginalPw(), passwordForm.getNewPw());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/member/delete")
    public ResponseEntity<Void> deleteMember(@RequestParam String userId, @RequestParam String password) {
        memberService.withdraw(Long.valueOf(userId), password);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/member/signup")
    public ResponseEntity<Long> signup(@RequestBody MemberRequestDto memberRequestDto) {
        return ResponseEntity.ok(memberService.join(memberRequestDto));
    }

    @PostMapping("/member/login")
    public ResponseEntity<TokenResponseDto> login(@RequestBody LoginForm loginForm) {
        return ResponseEntity.ok(memberService.login(loginForm.getEmail(), loginForm.getPassword()));
    }
}
