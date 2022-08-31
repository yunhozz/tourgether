package com.tourgether.api;

import com.tourgether.domain.member.model.repository.MemberRepository;
import com.tourgether.domain.member.service.MemberService;
import com.tourgether.util.auth.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public ResponseEntity<Void> updateMemberInfo(@AuthenticationPrincipal UserDetailsImpl userDetails, @Valid @RequestBody UpdateForm updateForm) {
        memberService.updateInfo(userDetails.getMember().getId(), updateForm.getName(), updateForm.getNickname(), updateForm.getProfileUrl());
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/members/password")
    public ResponseEntity<Void> updateMemberPassword(@AuthenticationPrincipal UserDetailsImpl userDetails, @Valid @RequestBody PasswordForm passwordForm) {
        memberService.updatePassword(userDetails.getMember().getId(), passwordForm.getOriginalPw(), passwordForm.getNewPw());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/members")
    public ResponseEntity<Void> deleteMember(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestParam String password) {
        memberService.withdraw(userDetails.getMember().getId(), password);
        return ResponseEntity.noContent().build();
    }
}
