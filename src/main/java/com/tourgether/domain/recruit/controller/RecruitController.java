package com.tourgether.domain.recruit.controller;

import com.tourgether.domain.recruit.controller.form.SearchForm;
import com.tourgether.domain.recruit.controller.form.UpdateForm;
import com.tourgether.domain.recruit.model.dto.RecruitQueryDto;
import com.tourgether.domain.recruit.model.dto.request.CommentRequestDto;
import com.tourgether.domain.recruit.model.dto.request.RecruitRequestDto;
import com.tourgether.domain.recruit.model.dto.response.CommentResponseDto;
import com.tourgether.domain.recruit.model.dto.response.RecruitResponseDto;
import com.tourgether.domain.recruit.model.repository.RecruitRepository;
import com.tourgether.domain.recruit.service.RecruitService;
import com.tourgether.enums.SearchCondition;
import com.tourgether.util.auth.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class RecruitController {

    private final RecruitService recruitService;
    private final RecruitRepository recruitRepository;

    @GetMapping("/recruit")
    public String recruitPage(@AuthenticationPrincipal UserDetailsImpl loginMember, @ModelAttribute SearchForm searchForm, @RequestParam(required = false) String query,
                              @PageableDefault(size = 10) Pageable pageable, Model model) {
        if (loginMember == null) {
            return "redirect:/member/signIn";
        }
        Page<RecruitQueryDto> recruits = recruitRepository.findPageWithCreated(pageable);
        if (query.equals("c")){
            recruits = recruitRepository.findPageWithCreated(pageable);
        }
        if (query.equals("m")) {
            recruits = recruitRepository.findPageWithModified(pageable);
        }
        if (query.equals("p")) {
            recruits = recruitRepository.findPageWithPopularity(pageable);
        }
        model.addAttribute("recruits", recruits);
        model.addAttribute("isPopularity", false);

        return "recruit/list";
    }

    @GetMapping("/recruit/search")
    public String searchRecruit(@Valid SearchForm searchForm, BindingResult result, @PageableDefault(size = 10) Pageable pageable, Model model) {
        if (result.hasErrors()) {
            return "recruit/list";
        }
        Page<RecruitQueryDto> recruits = null;
        if (searchForm.getCondition().equals(SearchCondition.ACCURACY_ORDER)) {
            recruits = recruitRepository.findPageWithKeywordOnAccuracyOrder(searchForm.getKeyword(), pageable); // ????????????
        } else if (searchForm.getCondition().equals(SearchCondition.LATEST_ORDER)){
            recruits = recruitRepository.findPageWithKeywordOnLatestOrder(searchForm.getKeyword(), pageable); // ?????????
        } else if (searchForm.getCondition() == null) {
            recruits = recruitRepository.findPageWithKeyword(searchForm.getKeyword(), pageable); // ?????????
        }
        model.addAttribute("recruits", recruits);
        model.addAttribute("isPopularity", false);

        return "recruit/list";
    }

    @GetMapping("/recruit/{recruitId}")
    public String readRecruit(@AuthenticationPrincipal UserDetailsImpl loginMember, @PathVariable String recruitId, @ModelAttribute CommentRequestDto commentRequestDto,
                              HttpServletRequest request, HttpServletResponse response, Model model) {
        if (loginMember == null) {
            return "redirect:/member/signIn";
        }
        RecruitResponseDto recruit = recruitService.findRecruitDto(Long.valueOf(recruitId));
        addViewCount(Long.valueOf(recruitId), request, response); // ????????? ?????? (?????? x)
        model.addAttribute("recruit", recruit);

        boolean isRecruitWriter = recruit.getWriterId().equals(loginMember.getMember().getId());
        model.addAttribute("isRecruitWriter", isRecruitWriter); // ????????? ???????????? ?????? ??????, ?????? ?????? ?????????
        List<CommentResponseDto> comments = recruit.getComments();
        for (CommentResponseDto comment : comments) {
            boolean isCommentWriter = comment.getWriterId().equals(loginMember.getMember().getId());
            model.addAttribute("isCommentWriter", isCommentWriter); // ?????? ???????????? ?????? ??????, ?????? ?????? ?????????
        }
        return "recruit/detail";
    }

    @GetMapping("/recruit/write")
    public String writePage(@AuthenticationPrincipal UserDetailsImpl loginMember, @ModelAttribute RecruitRequestDto recruitRequestDto, Model model) {
        if (loginMember == null) {
            return "redirect:/member/signIn";
        }
        model.addAttribute("writer", loginMember.getMember().getId());
        return "recruit/write";
    }

    @PostMapping("/recruit/write")
    public String write(@Valid @RequestBody RecruitRequestDto recruitRequestDto, BindingResult result, @RequestParam String writerId) {
        if (result.hasErrors()) {
            return "recruit/write";
        }
        recruitService.makeRecruit(recruitRequestDto, Long.valueOf(writerId));
        return "redirect:/recruit";
    }

    @GetMapping("/recruit/{recruitId}/update")
    public String updateRecruitForm(@AuthenticationPrincipal UserDetailsImpl loginMember, @PathVariable String recruitId, @ModelAttribute UpdateForm updateForm,
                                    Model model) {
        if (loginMember == null) {
            return "redirect:/member/signIn";
        }
        model.addAttribute("recruitId", recruitId);
        return "recruit/update";
    }

    @PostMapping("/recruit/update")
    public String updateRecruit(@Valid @RequestBody UpdateForm updateForm, BindingResult result, @RequestParam String recruitId) {
        if (result.hasErrors()) {
            return "recruit/update";
        }
        recruitService.updateRecruit(Long.valueOf(recruitId), updateForm);
        return "redirect:/" + recruitId;
    }

    @GetMapping("/recruit/{recruitId}/delete")
    public String deleteRecruit(@AuthenticationPrincipal UserDetailsImpl loginMember, @PathVariable String recruitId) {
        if (loginMember == null) {
            return "redirect:/member/signIn";
        }
        recruitService.deleteRecruit(Long.valueOf(recruitId), loginMember.getMember().getId());
        return "redirect:/recruit";
    }

    /*
    ????????? ????????? ??? : ????????? ?????? recruitId ??? ???????????? ?????? ?????? ?????? ????????? ??????
    ????????? ???????????? ?????? ??? : ????????? recruit ?????? ??????
     */
    private void addViewCount(Long recruitId, HttpServletRequest request, HttpServletResponse response) {
        Cookie oldCookie = null;
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("recruit")) {
                    oldCookie = cookie;
                }
            }
        }
        if (oldCookie != null) {
            if (!oldCookie.getValue().contains("[" + recruitId.toString() + "]")) {
                recruitRepository.addView(recruitId);
                oldCookie.setValue(oldCookie.getValue() + "_[" + recruitId + "]");
                oldCookie.setPath("/");
                oldCookie.setMaxAge(60 * 60 * 24);
                response.addCookie(oldCookie);
            }
        } else {
            recruitRepository.addView(recruitId);
            Cookie newCookie = new Cookie("recruit","[" + recruitId + "]");
            newCookie.setPath("/");
            newCookie.setMaxAge(60 * 60 * 24);
            response.addCookie(newCookie);
        }
    }
}
