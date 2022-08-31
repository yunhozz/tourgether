package com.tourgether.api;

import com.tourgether.domain.recruit.service.CommentService;
import com.tourgether.util.auth.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.tourgether.dto.CommentDto.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CommentApiController {

    private final CommentService commentService;

    @GetMapping("/comments")
    public ResponseEntity<List<CommentResponseDto>> getComments(@RequestParam String recruitId) {
        return ResponseEntity.ok(commentService.findCommentDtoListByRecruit(Long.valueOf(recruitId)));
    }

    @PostMapping("/comments")
    public ResponseEntity<Long> createComment(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestParam String recruitId, @Valid @RequestBody CommentRequestDto commentRequestDto) {
        return ResponseEntity.ok(commentService.makeComment(userDetails.getMember().getId(), Long.valueOf(recruitId), commentRequestDto));
    }

    @PostMapping("/comments-child")
    public ResponseEntity<Long> createCommentChild(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestParam String recruitId, @RequestParam String parentId, @Valid @RequestBody CommentRequestDto commentRequestDto) {
        return ResponseEntity.ok(commentService.makeCommentChild(userDetails.getMember().getId(), Long.valueOf(recruitId), Long.valueOf(parentId), commentRequestDto));
    }

    @PatchMapping("/comments")
    public ResponseEntity<Void> updateComment(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestParam String commentId, @Valid @RequestBody UpdateForm updateForm) {
        commentService.updateComment(Long.valueOf(commentId), userDetails.getMember().getId(), updateForm.getContent());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/comments")
    public ResponseEntity<Void> deleteComment(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestParam String commentId) {
        commentService.deleteComment(Long.valueOf(commentId), userDetails.getMember().getId());
        return ResponseEntity.noContent().build();
    }
}
