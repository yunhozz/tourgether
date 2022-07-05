package com.tourgether.domain.member.controller;

import com.tourgether.domain.member.dto.MemberRequestDto;
import com.tourgether.domain.member.service.MemberService;
import com.tourgether.global.dto.MemberSessionResponseDto;
import com.tourgether.global.ui.LoginMember;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

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
    public String signIn(@Valid LoginForm loginForm, BindingResult result, @RequestParam(defaultValue = "/") String redirectUrl) {
        if (result.hasErrors()) {
            return "member/login";
        }
        try {
            memberService.login(loginForm);
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
            return "redirect:/sign-in";
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
            return "redirect:/sign-in";
        }
        model.addAttribute("userId", loginMember.getId());
        return "member/update-info";
    }

    @PostMapping("/update-info")
    public String updateInfo(@Valid UpdateForm updateForm, BindingResult result, @RequestParam("userId") Long userId) {
        if (result.hasErrors()) {
            return "member/update-pw";
        }
        try {
            memberService.updateInfo(userId, updateForm);
        } catch (Exception e) {
            result.addError(new ObjectError("UpdateFail", "비밀번호 변경에 실패하였습니다."));
            return "member/update-pw";
        }
        return "redirect:/";
    }
}
