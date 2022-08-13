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
        if (memberRepository.findById(Long.valueOf(userId)).isEmpty()) {
            return Response.failure(400, "회원이 존재하지 않습니다.");
        }
        return Response.success(memberService.findMemberDto(Long.valueOf(userId)));
    }

    @GetMapping("/member/list")
    public Response getMembers() {
        return Response.success(memberService.findMemberDtoList());
    }

    @PatchMapping("/member/update-info")
    public Response updateMemberInfo(@RequestParam String id, @RequestBody UpdateForm updateForm) {
        if (memberRepository.findById(Long.valueOf(id)).isEmpty()) {
            return Response.failure(400, "회원이 존재하지 않습니다.");
        }
        memberService.updateInfo(Long.valueOf(id), updateForm.getName(), updateForm.getNickname(), updateForm.getProfileUrl());
        return Response.success(memberService.findMemberDto(Long.valueOf(id)));
    }

    @PatchMapping("/member/update-password")
    public Response updateMemberPassword(@RequestParam String id, @RequestBody PasswordForm passwordForm) {
        if (memberRepository.findById(Long.valueOf(id)).isEmpty()) {
            return Response.failure(400, "회원이 존재하지 않습니다.");
        }
        memberService.updatePassword(Long.valueOf(id), passwordForm.getOriginalPw(), passwordForm.getNewPw());
        return Response.success(memberService.findMemberDto(Long.valueOf(id)));
    }

    @DeleteMapping("/member/delete")
    public Response deleteMember(@RequestParam String id, @RequestParam String password) {
        if (memberRepository.findById(Long.valueOf(id)).isEmpty()) {
            return Response.failure(400, "회원이 존재하지 않습니다.");
        }
        memberService.withdraw(Long.valueOf(id), password);
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
