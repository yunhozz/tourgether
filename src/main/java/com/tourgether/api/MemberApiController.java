package com.tourgether.api;

import com.tourgether.domain.member.model.repository.MemberRepository;
import com.tourgether.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.tourgether.dto.MemberDto.*;
import static com.tourgether.dto.TokenDto.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;
    private final MemberRepository memberRepository;

    @GetMapping("/members/{userId}")
    public ResponseEntity<MemberResponseDto> getMember(@PathVariable String userId) {
        return ResponseEntity.ok(memberService.findMemberDto(Long.valueOf(userId)));
    }

    @GetMapping("/members")
    public ResponseEntity<List<MemberResponseDto>> getMembers() {
        return ResponseEntity.ok(memberService.findMemberDtoList());
    }

    @PostMapping("/members")
    public ResponseEntity<Long> signup(@Valid @RequestBody MemberRequestDto memberRequestDto) {
        return ResponseEntity.ok(memberService.join(memberRequestDto));
    }

    @PostMapping("/members/login")
    public ResponseEntity<TokenResponseDto> login(@Valid @RequestBody LoginForm loginForm) {
        return ResponseEntity.ok(memberService.login(loginForm.getEmail(), loginForm.getPassword()));
    }

    @PatchMapping("/members/info")
    public ResponseEntity<Void> updateMemberInfo(@RequestParam String userId, @Valid @RequestBody UpdateForm updateForm) {
        memberService.updateInfo(Long.valueOf(userId), updateForm.getName(), updateForm.getNickname(), updateForm.getProfileUrl());
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/members/password")
    public ResponseEntity<Void> updateMemberPassword(@RequestParam String userId, @Valid @RequestBody PasswordForm passwordForm) {
        memberService.updatePassword(Long.valueOf(userId), passwordForm.getOriginalPw(), passwordForm.getNewPw());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/members")
    public ResponseEntity<Void> deleteMember(@RequestParam String userId, @RequestParam String password) {
        memberService.withdraw(Long.valueOf(userId), password);
        return ResponseEntity.noContent().build();
    }
}
