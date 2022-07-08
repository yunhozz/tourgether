package com.tourgether.domain.member.controller;

import com.tourgether.domain.member.controller.form.LoginForm;
import com.tourgether.domain.member.controller.form.PasswordForm;
import com.tourgether.domain.member.controller.form.UpdateForm;
import com.tourgether.domain.member.model.dto.MemberRequestDto;
import com.tourgether.domain.member.service.MemberService;
import com.tourgether.dto.MemberSessionResponseDto;
import com.tourgether.ui.login.LoginMember;
import com.tourgether.ui.SessionConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final UserDetailsService userDetailsService;

    @GetMapping("/signup")
    public String signup(@ModelAttribute MemberRequestDto memberRequestDto) {
        return "member/join";
    }

    @PostMapping("/signup")
    public String signup(@Valid MemberRequestDto memberRequestDto, BindingResult result, @RequestParam(defaultValue = "/") String redirectUrl) {
        if (result.hasErrors()) {
            return "member/join";
        }
        try {
            memberService.join(memberRequestDto);
        } catch (Exception e) {
            result.addError(new ObjectError("joinFail", "회원가입에 실패하였습니다."));
            return "member/join";
        }
        if (redirectUrl != null) {
            return "redirect:" + redirectUrl;
        }
        return "redirect:/";
    }

    @GetMapping("/sign-in")
    public String signIn(@ModelAttribute LoginForm loginForm) {
        return "member/login";
    }

    @PostMapping("/sign-in")
    public String signIn(@Valid LoginForm loginForm, BindingResult result, HttpSession session, @RequestParam(defaultValue = "/") String redirectUrl) {
        if (result.hasErrors()) {
            return "member/login";
        }
        try {
            UserDetails userDetails = userDetailsService.loadUserByUsername(loginForm.getEmail()); // 이메일 존재 여부 검증, 시큐리티 고유 세션 영역에 저장
            MemberSessionResponseDto member = memberService.login(userDetails, loginForm.getPassword()); // 비밀번호 일치 여부 검증
            session.setAttribute(SessionConstants.LOGIN_MEMBER, member); // 세션에 회원 정보 저장
        } catch (Exception e) {
            result.addError(new ObjectError("loginFail", "로그인에 실패하였습니다."));
            return "member/login";
        }
        if (redirectUrl != null) {
            return "redirect:" + redirectUrl;
        }
        return "redirect:/";
    }

    @GetMapping("/update-pw")
    public String updatePw(@LoginMember MemberSessionResponseDto loginMember, @ModelAttribute PasswordForm passwordForm, Model model) {
        if (loginMember == null) {
            return "redirect:/member/sign-in";
        }
        model.addAttribute("userId", loginMember.getId());
        return "member/update-pw";
    }

    @PostMapping("/update-pw")
    public String updatePw(@Valid PasswordForm passwordForm, BindingResult result, @RequestParam("userId") Long userId) {
        if (result.hasErrors()) {
            return "member/update-pw";
        }
        try {
            memberService.updatePassword(userId, passwordForm.getOriginalPw(), passwordForm.getNewPw());
        } catch (Exception e) {
            result.addError(new ObjectError("UpdateFail", "비밀번호 변경에 실패하였습니다."));
            return "member/update-pw";
        }
        return "redirect:/";
    }

    @GetMapping("/update-info")
    public String updateInfo(@LoginMember MemberSessionResponseDto loginMember, @ModelAttribute UpdateForm updateForm, Model model) {
        if (loginMember == null) {
            return "redirect:/member/sign-in";
        }
        model.addAttribute("userId", loginMember.getId());
        return "member/update-info";
    }

    @PostMapping("/update-info")
    public String updateInfo(@Valid UpdateForm updateForm, BindingResult result, @RequestParam("userId") Long userId) {
        if (result.hasErrors()) {
            return "member/update-info";
        }
        try {
            memberService.updateInfo(userId, updateForm);
        } catch (Exception e) {
            result.addError(new ObjectError("UpdateFail", "회원정보 변경에 실패하였습니다."));
            return "member/update-info";
        }
        return "redirect:/";
    }

    @GetMapping("/logout")
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
