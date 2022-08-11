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
        if (memberRepository.findById(Long.valueOf(userId)).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(memberService.findMemberDto(Long.valueOf(userId)));
    }

    @GetMapping("/member/list")
    public ResponseEntity<List<MemberResponseDto>> getMembers() {
        return ResponseEntity.ok(memberService.findMemberDtoList());
    }

    @PatchMapping("/member/{userId}/update-info")
    public ResponseEntity<Object> updateMemberInfo(@PathVariable String userId, @RequestBody UpdateForm updateForm) {
        if (memberRepository.findById(Long.valueOf(userId)).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        memberService.updateInfo(Long.valueOf(userId), updateForm.getName(), updateForm.getNickname(), updateForm.getProfileUrl());
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/member/{userId}/update-password")
    public ResponseEntity<Object> updateMemberPassword(@PathVariable String userId, @RequestBody PasswordForm passwordForm) {
        if (memberRepository.findById(Long.valueOf(userId)).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        memberService.updatePassword(Long.valueOf(userId), passwordForm.getOriginalPw(), passwordForm.getNewPw());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/member/{userId}/delete")
    public ResponseEntity<Object> deleteMember(@PathVariable String userId, @RequestParam String password) {
        if (memberRepository.findById(Long.valueOf(userId)).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        memberService.withdraw(Long.valueOf(userId), password);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/member/signup")
    public ResponseEntity<MemberResponseDto> signup(@RequestBody MemberRequestDto memberRequestDto) {
        Long userId = memberService.join(memberRequestDto);
        return ResponseEntity.ok(memberService.findMemberDto(userId));
    }

    @PostMapping("/member/login")
    public ResponseEntity<TokenResponseDto> login(@RequestBody LoginForm loginForm) {
        TokenResponseDto tokenResponseDto = memberService.login(loginForm.getEmail(), loginForm.getPassword());
        return ResponseEntity.ok(tokenResponseDto);
    }
}
