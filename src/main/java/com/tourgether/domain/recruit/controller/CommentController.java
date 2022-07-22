package com.tourgether.domain.recruit.controller;

import com.tourgether.domain.recruit.model.dto.request.CommentRequestDto;
import com.tourgether.domain.recruit.model.repository.CommentRepository;
import com.tourgether.domain.recruit.service.CommentService;
import com.tourgether.dto.MemberSessionResponseDto;
import com.tourgether.ui.login.LoginMember;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final CommentRepository commentRepository;

    @PostMapping("/write")
    public String comment(@Valid @RequestBody CommentRequestDto commentRequestDto, BindingResult result, @RequestParam("writer") Long writerId,
                          @RequestParam("recruit") Long recruitId) {
        if (result.hasErrors()) {
            return "recruit/detail";
        }
        commentService.makeComment(commentRequestDto, writerId, recruitId);
        return "redirect:/" + recruitId;
    }

    @GetMapping("/{id}/update")
    public String updateCommentForm(@LoginMember MemberSessionResponseDto loginMember, @PathVariable("id") Long commentId, @RequestParam("recruit") Long recruitId,
                                    Model model) {
        if (loginMember == null) {
            return "redirect:/member/signIn";
        }
        model.addAttribute("writer", loginMember.getId());
        model.addAttribute("recruitId", recruitId);
        model.addAttribute("commentId", commentId);

        return "recruit/comment-update";
    }

    @PostMapping("/update")
    public String updateComment(@RequestParam String content, @RequestParam("writer") Long userId, @RequestParam("recruit") Long recruitId,
                                @RequestParam("comment") Long commentId) {
        if (!StringUtils.hasText(content)) {
            return "recruit/comment-update";
        }
        commentService.updateComment(commentId, userId, content);
        return "redirect:/" + recruitId;
    }

    @GetMapping("/{id}/delete")
    public String deleteComment(@LoginMember MemberSessionResponseDto loginMember, @PathVariable("id") Long commentId, @RequestParam("recruit") Long recruitId) {
        if (loginMember == null) {
            return "redirect:/member/signIn";
        }
        commentRepository.deleteById(commentId);
        return "redirect:/" + recruitId;
    }
}
