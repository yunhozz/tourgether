package com.tourgether.domain.recruit.controller;

import com.tourgether.domain.recruit.controller.form.CommentUpdateForm;
import com.tourgether.domain.recruit.dto.request.CommentRequestDto;
import com.tourgether.domain.recruit.model.repository.CommentRepository;
import com.tourgether.domain.recruit.service.CommentService;
import com.tourgether.util.auth.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final CommentRepository commentRepository;

    @PostMapping("/comment/write")
    public String comment(@Valid CommentRequestDto commentRequestDto, BindingResult result) {
        if (result.hasErrors()) {
            return "recruit/detail";
        }
        commentService.makeComment(commentRequestDto);
        return "redirect:/" + commentRequestDto.getRecruitId();
    }

    @GetMapping("/comment/{commentId}/update")
    public String updateComment(@AuthenticationPrincipal UserDetailsImpl loginMember, @PathVariable String commentId, @RequestParam String recruitId,
                                    Model model) {
        if (loginMember == null) {
            return "redirect:/member/signIn";
        }
        CommentUpdateForm commentUpdateForm = new CommentUpdateForm(recruitId, commentId, loginMember.getId());
        model.addAttribute("updateForm", commentUpdateForm);

        return "recruit/comment-update";
    }

    @PostMapping("/comment/update")
    public String updateComment(@Valid CommentUpdateForm form, BindingResult result) {
        if (result.hasErrors()) {
            return "recruit/comment-update";
        }
        commentService.updateComment(Long.valueOf(form.getCommentId()), form.getWriterId(), form.getContent());
        return "redirect:/" + form.getRecruitId();
    }

    @GetMapping("/comment/{commentId}/delete")
    public String deleteComment(@AuthenticationPrincipal UserDetailsImpl loginMember, @PathVariable String commentId, @RequestParam String recruitId) {
        if (loginMember == null) {
            return "redirect:/member/signIn";
        }
        commentRepository.deleteById(Long.valueOf(commentId));
        return "redirect:/" + recruitId;
    }
}
