package com.tourgether.domain.recruit.service;

import com.tourgether.domain.member.model.entity.Member;
import com.tourgether.domain.member.model.repository.MemberRepository;
import com.tourgether.domain.recruit.model.entity.Comment;
import com.tourgether.domain.recruit.model.entity.Recruit;
import com.tourgether.domain.recruit.model.repository.CommentRepository;
import com.tourgether.domain.recruit.model.repository.RecruitRepository;
import com.tourgether.enums.ErrorCode;
import com.tourgether.exception.recruit.CommentNotFoundException;
import com.tourgether.exception.recruit.RecruitNotFoundException;
import com.tourgether.exception.recruit.WriterMismatchException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.tourgether.dto.CommentDto.*;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final RecruitRepository recruitRepository;

    public Long makeComment(Long writerId, Long recruitId, CommentRequestDto commentRequestDto) {
        Member writer = memberRepository.getReferenceById(writerId);
        Recruit recruit = recruitRepository.findById(recruitId)
                .orElseThrow(() -> new RecruitNotFoundException(ErrorCode.RECRUIT_NOT_FOUND));

        Comment comment = Comment.create(recruit, writer, commentRequestDto.getContent());
        return commentRepository.save(comment).getId();
    }

    public Long makeCommentChild(Long writerId, Long recruitId, Long parentId, CommentRequestDto commentRequestDto) {
        Member writer = memberRepository.getReferenceById(writerId);
        Recruit recruit = recruitRepository.findById(recruitId)
                .orElseThrow(() -> new RecruitNotFoundException(ErrorCode.RECRUIT_NOT_FOUND));
        Comment parent = findComment(parentId);

        Comment commentChild = Comment.createChild(recruit, writer, parent, commentRequestDto.getContent());
        return commentRepository.save(commentChild).getId();
    }

    public void updateComment(Long commentId, Long writerId, String content) {
        Comment comment = findComment(commentId);
        if (!comment.getWriter().getId().equals(writerId)) {
            throw new WriterMismatchException(ErrorCode.WRITER_MISMATCH);
        }
        comment.update(content);
    }

    public void deleteComment(Long commentId, Long writerId) {
        Comment comment = findComment(commentId);
        if (!comment.getWriter().getId().equals(writerId)) {
            throw new WriterMismatchException(ErrorCode.WRITER_MISMATCH);
        }
        commentRepository.delete(comment); // orphan remove : children
    }

    @Transactional(readOnly = true)
    public CommentResponseDto findCommentDto(Long id) {
        return new CommentResponseDto(findComment(id));
    }

    @Transactional(readOnly = true)
    public List<CommentResponseDto> findCommentDtoList() {
        return commentRepository.findAll().stream()
                .map(CommentResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<CommentResponseDto> findCommentDtoListByRecruit(Long recruitId) {
        Recruit recruit = recruitRepository.findById(recruitId)
                .orElseThrow(() -> new RecruitNotFoundException(ErrorCode.RECRUIT_NOT_FOUND));

        return commentRepository.findByRecruit(recruit).stream()
                .map(CommentResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    private Comment findComment(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new CommentNotFoundException(ErrorCode.COMMENT_NOT_FOUND));
    }
}
