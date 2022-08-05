package com.tourgether.domain.member.controller;

import com.tourgether.domain.member.controller.form.LoginForm;
import com.tourgether.domain.member.controller.form.PasswordForm;
import com.tourgether.domain.member.controller.form.UpdateForm;
import com.tourgether.domain.member.dto.request.MemberRequestDto;
import com.tourgether.domain.member.dto.response.MemberResponseDto;
import com.tourgether.domain.member.service.MemberService;
import com.tourgether.util.auth.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final UserDetailsService userDetailsService;

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

    @GetMapping("/sign-in")
    public String signIn(@ModelAttribute LoginForm loginForm) {
        return "member/login";
    }

    @PostMapping("/member/sign-in")
    public String signIn(@Valid LoginForm loginForm, BindingResult result, @RequestParam(defaultValue = "/") String redirectUrl) {
        if (result.hasErrors()) {
            return "member/login";
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(loginForm.getEmail());// 이메일 존재 여부 검증, 시큐리티 고유 세션 영역에 저장
        memberService.login(userDetails, loginForm.getPassword()); // 비밀번호 일치 여부 검증

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
    public String updatePw(@AuthenticationPrincipal UserDetailsImpl loginMember, Model model) {
        if (loginMember == null) {
            return "redirect:/member/sign-in";
        }
        PasswordForm passwordForm = new PasswordForm(loginMember.getId());
        model.addAttribute("passwordForm", passwordForm);

        return "member/update-pw";
    }

    @PostMapping("/member/update-pw")
    public String updatePw(@Valid PasswordForm form, BindingResult result) {
        if (result.hasErrors()) {
            return "member/update-pw";
        }
        memberService.updatePassword(form.getUserId(), form.getOriginalPw(), form.getNewPw());
        return "redirect:/";
    }

    @GetMapping("/member/update-info")
    public String updateInfo(@AuthenticationPrincipal UserDetailsImpl loginMember, Model model) {
        if (loginMember == null) {
            return "redirect:/member/sign-in";
        }
        UpdateForm updateForm = new UpdateForm(loginMember.getId());
        model.addAttribute("updateForm", updateForm);

        return "member/update-info";
    }

    @PostMapping("/member/update-info")
    public String updateInfo(@Valid UpdateForm form, BindingResult result) {
        if (result.hasErrors()) {
            return "member/update-info";
        }
        memberService.updateInfo(form.getUserId(), form.getName(), form.getNickname(), form.getProfileUrl());
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
