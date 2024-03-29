package com.tourgether.domain.member.controller;

import com.tourgether.domain.member.service.MemberService;
import com.tourgether.util.auth.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import static com.tourgether.dto.MemberDto.*;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/member/signup")
    public String signup(@ModelAttribute MemberRequestDto memberRequestDto) {
        return "member/join";
    }

    @PostMapping("/member/signup")
    public String signup(@Valid MemberRequestDto memberRequestDto, BindingResult result, @RequestParam(defaultValue = "/") String redirectUrl) {
        if (result.hasErrors()) {
            return "member/join";
        }
        memberService.join(memberRequestDto);

        if (redirectUrl != null) {
            return "redirect:" + redirectUrl;
        }
        return "redirect:/";
    }

//    @GetMapping("/member/login")
//    public String loginSelect() {
//        return "member/login-select";
//    }

    @PostMapping("/member/login")
    public String loginSelect(@RequestParam String method) {
        if (method.equals("email")) {
            return "redirect:/member/sign-in";
        }
        if (method.equals("kakao")) {
            return "redirect:/oauth2/authorization/kakao?redirect_uri=http://localhost:3000/oauth/redirect";
        }
        return null;
    }

    @GetMapping("/member/sign-in")
    public String signIn(@ModelAttribute LoginForm loginForm) {
        return "member/login";
    }

    @PostMapping("/member/sign-in")
    public String signIn(@Valid LoginForm loginForm, BindingResult result, @RequestParam(defaultValue = "/") String redirectUrl) {
        if (result.hasErrors()) {
            return "member/login";
        }
        memberService.login(loginForm.getEmail(), loginForm.getPassword());

        if (redirectUrl != null) {
            return "redirect:" + redirectUrl;
        }
        return "redirect:/";
    }

    @GetMapping("/member/{userId}")
    public String memberInfo(@PathVariable String userId, Model model) {
        MemberResponseDto member = memberService.findMemberDto(Long.valueOf(userId));
        model.addAttribute("member", member);

        return "member/info";
    }

    @GetMapping("/member/update-pw")
    public String updatePw(@AuthenticationPrincipal UserDetailsImpl loginMember, @ModelAttribute PasswordForm passwordForm, HttpServletResponse response) {
        if (loginMember == null) {
            return "redirect:/member/sign-in";
        }
        response.setHeader("userId", String.valueOf(loginMember.getMember().getId()));
        return "member/update-pw";
    }

    @PostMapping("/member/update-pw")
    public String updatePw(@Valid PasswordForm form, BindingResult result, HttpServletRequest request) {
        if (result.hasErrors()) {
            return "member/update-pw";
        }
        String userId = request.getHeader("userId");
        memberService.updatePassword(Long.valueOf(userId), form.getOriginalPw(), form.getNewPw());

        return "redirect:/";
    }

    @GetMapping("/member/update-info")
    public String updateInfo(@AuthenticationPrincipal UserDetailsImpl loginMember, @ModelAttribute PasswordForm passwordForm, HttpServletResponse response) {
        if (loginMember == null) {
            return "redirect:/member/sign-in";
        }
        response.setHeader("userId", String.valueOf(loginMember.getMember().getId()));
        return "member/update-info";
    }

    @PostMapping("/member/update-info")
    public String updateInfo(@Valid UpdateForm form, BindingResult result, HttpServletRequest request) {
        if (result.hasErrors()) {
            return "member/update-info";
        }
        String userId = request.getHeader("userId");
        memberService.updateInfo(Long.valueOf(userId), form.getName(), form.getNickname(), form.getProfileUrl());

        return "redirect:/";
    }

    @GetMapping("/member/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            cookie.setPath("/");
            cookie.setMaxAge(0);
            response.addCookie(cookie);
        }
        return "redirect:/";
    }
}
