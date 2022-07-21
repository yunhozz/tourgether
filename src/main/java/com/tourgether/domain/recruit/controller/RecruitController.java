package com.tourgether.domain.recruit.controller;

import com.tourgether.domain.recruit.controller.form.SearchForm;
import com.tourgether.domain.recruit.controller.form.UpdateForm;
import com.tourgether.domain.recruit.model.dto.RecruitQueryDto;
import com.tourgether.domain.recruit.model.dto.request.CommentRequestDto;
import com.tourgether.domain.recruit.model.dto.request.RecruitRequestDto;
import com.tourgether.domain.recruit.model.dto.response.CommentResponseDto;
import com.tourgether.domain.recruit.model.dto.response.RecruitResponseDto;
import com.tourgether.domain.recruit.model.repository.CommentRepository;
import com.tourgether.domain.recruit.model.repository.RecruitRepository;
import com.tourgether.domain.recruit.service.CommentService;
import com.tourgether.domain.recruit.service.RecruitService;
import com.tourgether.dto.MemberSessionResponseDto;
import com.tourgether.enums.SearchCondition;
import com.tourgether.ui.login.LoginMember;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/recruit")
@RequiredArgsConstructor
public class RecruitController {

    private final RecruitService recruitService;
    private final CommentService commentService;
    private final RecruitRepository recruitRepository;
    private final CommentRepository commentRepository;

    @GetMapping
    public String recruitPage(@LoginMember MemberSessionResponseDto loginMember, @ModelAttribute SearchForm searchForm, @PageableDefault(size = 10) Pageable pageable,
                              Model model) {
        if (loginMember == null) {
            return "redirect:/member/signIn";
        }
        Page<RecruitQueryDto> recruits = recruitRepository.findSimplePage(pageable);
        model.addAttribute("recruits", recruits);
        model.addAttribute("isPopularity", false);

        return "recruit/list";
    }

    @GetMapping("/popular")
    public String recruitPageWithPopularity(@LoginMember MemberSessionResponseDto loginMember, @PageableDefault(size = 10) Pageable pageable, Model model) {
        if (loginMember == null) {
            return "redirect:/member/signIn";
        }
        Page<RecruitQueryDto> recruits = recruitRepository.findPageWithPopularity(pageable);
        model.addAttribute("recruits", recruits);
        model.addAttribute("isPopularity", true);

        return "recruit/list";
    }

    @GetMapping("/search")
    public String searchRecruit(@Valid @RequestBody SearchForm searchForm, BindingResult result, @PageableDefault(size = 10) Pageable pageable, Model model) {
        if (result.hasErrors()) {
            return "recruit/list";
        }
        Page<RecruitQueryDto> recruits = null;
        if (searchForm.getCondition().equals(SearchCondition.ACCURACY_ORDER)) {
            recruits = recruitRepository.findPageWithKeywordOnAccuracyOrder(searchForm.getKeyword(), pageable); // 정확도순
        } else if (searchForm.getCondition().equals(SearchCondition.LATEST_ORDER)){
            recruits = recruitRepository.findPageWithKeywordOnLatestOrder(searchForm.getKeyword(), pageable); // 수정순
        } else if (searchForm.getCondition() == null) {
            recruits = recruitRepository.findPageWithKeyword(searchForm.getKeyword(), pageable); // 생성순
        }
        model.addAttribute("recruits", recruits);
        model.addAttribute("isPopularity", false);

        return "recruit/list";
    }

    @GetMapping("/{id}")
    public String readRecruit(@LoginMember MemberSessionResponseDto loginMember, @PathVariable("id") Long recruitId,
                              @ModelAttribute CommentRequestDto commentRequestDto, HttpServletRequest request, HttpServletResponse response, Model model) {
        if (loginMember == null) {
            return "redirect:/member/signIn";
        }
        RecruitResponseDto recruit = recruitService.findRecruitDto(recruitId);
        addViewCount(recruitId, request, response); // 조회수 증가 (중복 x)
        model.addAttribute("recruit", recruit);

        boolean isRecruitWriter = recruit.getWriterId().equals(loginMember.getId());
        model.addAttribute("isRecruitWriter", isRecruitWriter); // 모집글 작성자일 경우 수정, 삭제 버튼 활성화
        List<CommentResponseDto> comments = recruit.getComments();
        for (CommentResponseDto comment : comments) {
            boolean isCommentWriter = comment.getWriterId().equals(loginMember.getId());
            model.addAttribute("isCommentWriter", isCommentWriter); // 댓글 작성자일 경우 수정, 삭제 버튼 활성화
        }
        return "recruit/detail";
    }

    @GetMapping("/write")
    public String writePage(@LoginMember MemberSessionResponseDto loginMember, @ModelAttribute RecruitRequestDto recruitRequestDto, Model model) {
        if (loginMember == null) {
            return "redirect:/member/signIn";
        }
        model.addAttribute("writer", loginMember.getId());
        return "recruit/write";
    }

    @PostMapping("/write")
    public String write(@Valid @RequestBody RecruitRequestDto recruitRequestDto, BindingResult result, @RequestParam("writer") Long writerId) {
        if (result.hasErrors()) {
            return "recruit/write";
        }
        recruitService.makeRecruit(recruitRequestDto, writerId);
        return "redirect:/recruit";
    }

    @PostMapping("/comment/write")
    public String comment(@Valid @RequestBody CommentRequestDto commentRequestDto, BindingResult result, @RequestParam("writer") Long writerId,
                          @RequestParam("recruit") Long recruitId) {
        if (result.hasErrors()) {
            return "recruit/detail";
        }
        commentService.makeComment(commentRequestDto, writerId, recruitId);
        return "redirect:/" + recruitId;
    }

    @GetMapping("/{id}/update")
    public String updateRecruitForm(@LoginMember MemberSessionResponseDto loginMember, @PathVariable("id") Long recruitId, @ModelAttribute UpdateForm updateForm,
                                    Model model) {
        if (loginMember == null) {
            return "redirect:/member/signIn";
        }
        model.addAttribute("recruitId", recruitId);
        return "recruit/update";
    }

    @PostMapping("/update")
    public String updateRecruit(@Valid @RequestBody UpdateForm updateForm, BindingResult result, @RequestParam("id") Long recruitId) {
        if (result.hasErrors()) {
            return "recruit/update";
        }
        recruitService.updateRecruit(recruitId, updateForm);
        return "redirect:/" + recruitId;
    }

    @GetMapping("/{id}/comment/{commentId}/update")
    public String updateCommentForm(@LoginMember MemberSessionResponseDto loginMember, @PathVariable("id") Long recruitId, @PathVariable("commentId") Long commentId,
                                    Model model) {
        if (loginMember == null) {
            return "redirect:/member/signIn";
        }
        model.addAttribute("writer", loginMember.getId());
        model.addAttribute("recruitId", recruitId);
        model.addAttribute("commentId", commentId);

        return "recruit/comment-update";
    }

    @PostMapping("/comment/update")
    public String updateComment(@RequestParam("content") String content, BindingResult result, @RequestParam("writer") Long userId,
                                @RequestParam("id") Long recruitId, @RequestParam("commentId") Long commentId) {
        if (!StringUtils.hasText(content)) {
            return "recruit/comment-update";
        }
        commentService.updateComment(commentId, userId, content);
        return "redirect:/" + recruitId;
    }

    @GetMapping("/{id}/delete")
    public String deleteRecruit(@LoginMember MemberSessionResponseDto loginMember, @PathVariable("id") Long recruitId) {
        if (loginMember == null) {
            return "redirect:/member/signIn";
        }
        recruitService.deleteRecruit(recruitId, loginMember.getId());
        return "redirect:/recruit";
    }

    @GetMapping("/{id}/comment/{commentId}/delete")
    public String deleteComment(@LoginMember MemberSessionResponseDto loginMember, @PathVariable("id") Long recruitId, @PathVariable("commentId") Long commentId) {
        if (loginMember == null) {
            return "redirect:/member/signIn";
        }
        commentRepository.deleteById(commentId);
        return "redirect:/" + recruitId;
    }

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
        /*
        쿠키가 존재할 때 : 쿠키의 값에 recruitId 를 포함하고 있지 않을 때만 조회수 증가
        쿠키가 존재하지 않을 때 : 새로운 recruit 쿠키 생성
         */
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
