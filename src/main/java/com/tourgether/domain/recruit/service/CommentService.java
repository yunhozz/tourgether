package com.tourgether.domain.recruit.service;

import com.tourgether.domain.member.model.entity.Member;
import com.tourgether.domain.member.model.repository.MemberRepository;
import com.tourgether.domain.recruit.service.dto.request.CommentRequestDto;
import com.tourgether.domain.recruit.service.dto.response.CommentResponseDto;
import com.tourgether.domain.recruit.model.entity.Comment;
import com.tourgether.domain.recruit.model.entity.Recruit;
import com.tourgether.domain.recruit.model.repository.CommentRepository;
import com.tourgether.domain.recruit.model.repository.RecruitRepository;
import com.tourgether.enums.ErrorCode;
import com.tourgether.exception.recruit.WriterMismatchException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final RecruitRepository recruitRepository;

    public Long makeComment(CommentRequestDto commentRequestDto, Long writerId, Long recruitId) {
        Member writer = memberRepository.getReferenceById(writerId);
        Recruit recruit = recruitRepository.getReferenceById(recruitId);

        commentRequestDto.setWriter(writer);
        commentRequestDto.setRecruit(recruit);

        return commentRepository.save(commentRequestDto.parentToEntity()).getId();
    }

    public Long makeCommentChild(CommentRequestDto commentRequestDto, Long writerId, Long recruitId, Long parentId) {
        Member writer = memberRepository.getReferenceById(writerId);
        Recruit recruit = recruitRepository.getReferenceById(recruitId);
        Comment parent = findComment(parentId);

        commentRequestDto.setWriter(writer);
        commentRequestDto.setRecruit(recruit);
        commentRequestDto.setParent(parent);

        return commentRepository.save(commentRequestDto.childToEntity()).getId();
    }

    public void updateComment(Long commentId, Long writerId, String content) {
        Comment comment = findComment(commentId);
        if (!comment.getWriter().getId().equals(writerId)) {
            throw new WriterMismatchException("This member is not writer of this: " + writerId, ErrorCode.WRITER_MISMATCH);
        }
        comment.update(content);
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
    private Comment findComment(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("This comment is null: " + id));
    }
}
