package com.tourgether.domain.recruit.controller;

import com.tourgether.domain.recruit.dto.request.CommentRequestDto;
import com.tourgether.domain.recruit.model.repository.CommentRepository;
import com.tourgether.domain.recruit.service.CommentService;
import com.tourgether.util.auth.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final CommentRepository commentRepository;

    @PostMapping("/comment/write")
    public String comment(@Valid @RequestBody CommentRequestDto commentRequestDto, BindingResult result, @RequestParam String writerId, @RequestParam String recruitId) {
        if (result.hasErrors()) {
            return "recruit/detail";
        }
        commentService.makeComment(commentRequestDto, Long.valueOf(writerId), Long.valueOf(recruitId));
        return "redirect:/" + recruitId;
    }

    @GetMapping("/comment/{commentId}/update")
    public String updateCommentForm(@AuthenticationPrincipal UserDetailsImpl loginMember, @PathVariable String commentId, @RequestParam String recruitId,
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
    public String updateComment(@RequestParam String content, @RequestParam String userId, @RequestParam String recruitId, @RequestParam String commentId) {
        if (!StringUtils.hasText(content)) {
            return "recruit/comment-update";
        }
        commentService.updateComment(Long.valueOf(commentId), Long.valueOf(userId), content);
        return "redirect:/" + recruitId;
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
