package com.tourgether.api;

import com.tourgether.domain.member.controller.form.LoginForm;
import com.tourgether.domain.member.controller.form.PasswordForm;
import com.tourgether.domain.member.controller.form.UpdateForm;
import com.tourgether.domain.member.dto.request.MemberRequestDto;
import com.tourgether.domain.member.dto.response.MemberResponseDto;
import com.tourgether.domain.member.service.MemberService;
import com.tourgether.domain.member.service.UserDetailsServiceImpl;
import com.tourgether.dto.TokenResponseDto;
import com.tourgether.util.auth.jwt.JwtFilter;
import com.tourgether.util.auth.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;
    private final UserDetailsServiceImpl userDetailsService;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final TokenProvider tokenProvider;

    @GetMapping("/member/{userId}")
    public MemberResponseDto getMember(@PathVariable String userId) {
        return memberService.findMemberDto(Long.valueOf(userId));
    }

    @GetMapping("/member/list")
    public List<MemberResponseDto> getMembers() {
        return memberService.findMemberDtoList();
    }

    @PatchMapping("/member/{userId}/update-info")
    public MemberResponseDto updateMemberInfo(@PathVariable String userId, @RequestBody UpdateForm form) {
        memberService.updateInfo(Long.valueOf(userId), form.getName(), form.getNickname(), form.getProfileUrl());
        return memberService.findMemberDto(Long.valueOf(userId));
    }

    @PatchMapping("/member/{userId}/update-password")
    public MemberResponseDto updateMemberPassword(@PathVariable String userId, @RequestBody PasswordForm form) {
        memberService.updatePassword(Long.valueOf(userId), form.getOriginalPw(), form.getNewPw());
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

    @PostMapping("/member/jwt-token")
    public ResponseEntity<TokenResponseDto> provideToken(@RequestBody LoginForm loginForm) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginForm.getEmail(), loginForm.getPassword());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.createToken(authentication);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);

        return new ResponseEntity<>(new TokenResponseDto(jwt), httpHeaders, HttpStatus.OK);
    }
}
