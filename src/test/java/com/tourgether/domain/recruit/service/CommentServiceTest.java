package com.tourgether.domain.recruit.service;

import com.tourgether.domain.member.model.dto.MemberRequestDto;
import com.tourgether.domain.member.service.MemberService;
import com.tourgether.domain.recruit.model.dto.request.CommentRequestDto;
import com.tourgether.domain.recruit.model.dto.request.RecruitRequestDto;
import com.tourgether.domain.recruit.model.dto.response.CommentResponseDto;
import com.tourgether.enums.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@Commit
class CommentServiceTest {

    @Autowired
    private CommentService commentService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private RecruitService recruitService;

    @Test
    void makeComment() throws Exception {
        //given
        MemberRequestDto writer = createMemberDto("writer", "writer", "writer", "writer");
        MemberRequestDto member1 = createMemberDto("email1", "pw1", "yunho1", "pyh1");
        MemberRequestDto member2 = createMemberDto("email2", "pw2", "yunho2", "pyh2");
        MemberRequestDto member3 = createMemberDto("email3", "pw3", "yunho3", "pyh3");
        Long writerId = memberService.join(writer);
        Long userId1 = memberService.join(member1);
        Long userId2 = memberService.join(member2);
        Long userId3 = memberService.join(member3);

        RecruitRequestDto recruitDto = createRecruitDto("title", "content");
        Long recruitId = recruitService.makeRecruit(recruitDto, writerId);

        CommentRequestDto commentDto = createCommentDto("comment");
        commentService.makeComment(commentDto, userId1, recruitId);
        commentService.makeComment(commentDto, userId2, recruitId);
        commentService.makeComment(commentDto, userId3, recruitId);

        //when
        List<CommentResponseDto> result = commentService.findCommentDtoList();

        //then
        assertThat(result.size()).isEqualTo(3);
    }

    @Test
    void makeCommentChild() throws Exception {
        //given
        MemberRequestDto writer = createMemberDto("writer", "writer", "writer", "writer");
        MemberRequestDto member1 = createMemberDto("email1", "pw1", "yunho1", "pyh1");
        MemberRequestDto member2 = createMemberDto("email2", "pw2", "yunho2", "pyh2");
        MemberRequestDto member3 = createMemberDto("email3", "pw3", "yunho3", "pyh3");
        Long writerId = memberService.join(writer);
        Long userId1 = memberService.join(member1);
        Long userId2 = memberService.join(member2);
        Long userId3 = memberService.join(member3);

        RecruitRequestDto recruitDto = createRecruitDto("title", "content");
        Long recruitId = recruitService.makeRecruit(recruitDto, writerId);

        CommentRequestDto commentDto = createCommentDto("comment");
        Long parentId = commentService.makeComment(commentDto, userId1, recruitId);

        CommentRequestDto commentChildDto = createCommentDto("commentChild");
        commentService.makeCommentChild(commentChildDto, userId2, recruitId, parentId);
        commentService.makeCommentChild(commentChildDto, userId3, recruitId, parentId);

        //when
        List<CommentResponseDto> result = commentService.findCommentDtoList();

        //then
        assertThat(result.size()).isEqualTo(3);
    }

    @Test
    void updateComment() throws Exception {
        //given
        MemberRequestDto writer = createMemberDto("writer", "writer", "writer", "writer");
        MemberRequestDto member1 = createMemberDto("email1", "pw1", "yunho1", "pyh1");
        MemberRequestDto member2 = createMemberDto("email2", "pw2", "yunho2", "pyh2");
        MemberRequestDto member3 = createMemberDto("email3", "pw3", "yunho3", "pyh3");
        Long writerId = memberService.join(writer);
        Long userId1 = memberService.join(member1);
        Long userId2 = memberService.join(member2);
        Long userId3 = memberService.join(member3);

        RecruitRequestDto recruitDto = createRecruitDto("title", "content");
        Long recruitId = recruitService.makeRecruit(recruitDto, writerId);

        CommentRequestDto commentDto = createCommentDto("comment");
        Long commentId1 = commentService.makeComment(commentDto, userId1, recruitId);
        Long commentId2 = commentService.makeComment(commentDto, userId2, recruitId);
        Long commentId3 = commentService.makeComment(commentDto, userId3, recruitId);

        //when
        commentService.updateComment(commentId2, userId2, "update comment");
        List<CommentResponseDto> result = commentService.findCommentDtoList();

        //then
        assertThat(result.size()).isEqualTo(3);
        assertThat(result.get(1).getContent()).isEqualTo("update comment");
    }

    private CommentRequestDto createCommentDto(String content) {
        return new CommentRequestDto(content);
    }

    private MemberRequestDto createMemberDto(String email, String password, String name, String nickname) {
        return MemberRequestDto.builder()
                .email(email)
                .password(password)
                .name(name)
                .nickname(nickname)
                .role(Role.USER)
                .build();
    }

    private RecruitRequestDto createRecruitDto(String title, String content) {
        return new RecruitRequestDto(title, content);
    }
}