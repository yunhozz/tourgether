package com.tourgether.api;

import com.tourgether.api.dto.Response;
import com.tourgether.domain.member.model.repository.MemberRepository;
import com.tourgether.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static com.tourgether.dto.MemberDto.*;
import static com.tourgether.dto.TokenDto.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;
    private final MemberRepository memberRepository;

    @GetMapping("/member/{userId}")
    public Response getMember(@PathVariable String userId) {
        return Response.success(memberService.findMemberDto(Long.valueOf(userId)));
    }

    @GetMapping("/member/list")
    public Response getMembers() {
        return Response.success(memberService.findMemberDtoList());
    }

    @PatchMapping("/member/update-info")
    public Response updateMemberInfo(@RequestParam String userId, @RequestBody UpdateForm updateForm) {
        memberService.updateInfo(Long.valueOf(userId), updateForm.getName(), updateForm.getNickname(), updateForm.getProfileUrl());
        return Response.success(memberService.findMemberDto(Long.valueOf(userId)));
    }

    @PatchMapping("/member/update-password")
    public Response updateMemberPassword(@RequestParam String userId, @RequestBody PasswordForm passwordForm) {
        memberService.updatePassword(Long.valueOf(userId), passwordForm.getOriginalPw(), passwordForm.getNewPw());
        return Response.success(memberService.findMemberDto(Long.valueOf(userId)));
    }

    @DeleteMapping("/member/delete")
    public Response deleteMember(@RequestParam String userId, @RequestParam String password) {
        memberService.withdraw(Long.valueOf(userId), password);
        return Response.success();
    }

    @PostMapping("/member/signup")
    public Response signup(@RequestBody MemberRequestDto memberRequestDto) {
        Long userId = memberService.join(memberRequestDto);
        return Response.success(memberService.findMemberDto(userId));
    }

    @PostMapping("/member/login")
    public Response login(@RequestBody LoginForm loginForm) {
        TokenResponseDto tokenResponseDto = memberService.login(loginForm.getEmail(), loginForm.getPassword());
        return Response.success(tokenResponseDto);
    }
}
