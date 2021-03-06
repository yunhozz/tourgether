package com.tourgether.domain.member.controller;

import com.tourgether.domain.member.controller.form.LoginForm;
import com.tourgether.domain.member.controller.form.PasswordForm;
import com.tourgether.domain.member.controller.form.UpdateForm;
import com.tourgether.domain.member.model.dto.request.MemberRequestDto;
import com.tourgether.domain.member.model.dto.response.MemberResponseDto;
import com.tourgether.domain.member.service.MemberService;
import com.tourgether.domain.member.service.UserDetailsServiceImpl;
import com.tourgether.util.auth.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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
    private final UserDetailsServiceImpl userDetailsService;

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
        UserDetails userDetails = userDetailsService.loadUserByUsername(loginForm.getEmail());// ????????? ?????? ?????? ??????, ???????????? ?????? ?????? ????????? ??????
        memberService.login(userDetails, loginForm.getPassword()); // ???????????? ?????? ?????? ??????, ?????? ??????

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
    public String updatePw(@AuthenticationPrincipal UserDetailsImpl loginMember, @ModelAttribute PasswordForm passwordForm, Model model) {
        if (loginMember == null) {
            return "redirect:/member/sign-in";
        }
        model.addAttribute("userId", loginMember.getMember().getId());
        return "member/update-pw";
    }

    @PostMapping("/member/update-pw")
    public String updatePw(@Valid PasswordForm passwordForm, BindingResult result, @RequestParam String userId) {
        if (result.hasErrors()) {
            return "member/update-pw";
        }
        memberService.updatePassword(Long.valueOf(userId), passwordForm.getOriginalPw(), passwordForm.getNewPw());
        return "redirect:/";
    }

    @GetMapping("/member/update-info")
    public String updateInfo(@AuthenticationPrincipal UserDetailsImpl loginMember, @ModelAttribute UpdateForm updateForm, Model model) {
        if (loginMember == null) {
            return "redirect:/member/sign-in";
        }
        model.addAttribute("userId", loginMember.getMember().getId());
        return "member/update-info";
    }

    @PostMapping("/member/update-info")
    public String updateInfo(@Valid UpdateForm updateForm, BindingResult result, @RequestParam String userId) {
        if (result.hasErrors()) {
            return "member/update-info";
        }
        memberService.updateInfo(Long.valueOf(userId), updateForm);
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
