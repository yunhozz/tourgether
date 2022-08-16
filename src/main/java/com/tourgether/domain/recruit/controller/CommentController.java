package com.tourgether.domain.recruit.controller;

import com.tourgether.domain.recruit.model.repository.CommentRepository;
import com.tourgether.domain.recruit.service.CommentService;
import com.tourgether.util.auth.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import static com.tourgether.dto.CommentDto.*;

@Controller
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final CommentRepository commentRepository;

    @PostMapping("/comment/write")
    public String comment(@Valid CommentRequestDto commentRequestDto, BindingResult result, HttpServletRequest request) {
        if (result.hasErrors()) {
            return "recruit/detail";
        }
        String userId = request.getHeader("userId");
        String recruitId = request.getHeader("recruitId");
        commentService.makeComment(Long.valueOf(userId), Long.valueOf(recruitId), commentRequestDto);

        return "redirect:/recruit/" + recruitId;
    }

    @GetMapping("/comment/{commentId}/update")
    public String updateComment(@AuthenticationPrincipal UserDetailsImpl loginMember, @PathVariable String commentId, @RequestParam String recruitId,
                                @ModelAttribute UpdateForm updateForm, HttpServletResponse response) {
        if (loginMember == null) {
            return "redirect:/member/signIn";
        }
        response.setHeader("userId", String.valueOf(loginMember.getMember().getId()));
        response.setHeader("recruitId", recruitId);
        response.setHeader("commentId", commentId);

        return "recruit/comment-update";
    }

    @PostMapping("/comment/update")
    public String updateComment(@Valid UpdateForm form, BindingResult result, HttpServletRequest request) {
        if (result.hasErrors()) {
            return "recruit/comment-update";
        }
        String userId = request.getHeader("userId");
        String recruitId = request.getHeader("recruitId");
        String commentId = request.getHeader("commentId");

        commentService.updateComment(Long.valueOf(commentId), Long.valueOf(userId), form.getContent());
        return "redirect:/recruit/" + recruitId;
    }

    @GetMapping("/comment/{commentId}/delete")
    public String deleteComment(@AuthenticationPrincipal UserDetailsImpl loginMember, @PathVariable String commentId, @RequestParam String recruitId) {
        if (loginMember == null) {
            return "redirect:/member/signIn";
        }
        commentRepository.deleteById(Long.valueOf(commentId));
        return "redirect:/recruit/" + recruitId;
    }
}
