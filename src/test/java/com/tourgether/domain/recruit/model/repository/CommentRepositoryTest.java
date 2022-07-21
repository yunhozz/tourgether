package com.tourgether.domain.recruit.model.repository;

import com.tourgether.domain.member.model.Member;
import com.tourgether.domain.member.model.repository.MemberRepository;
import com.tourgether.domain.recruit.model.entity.Comment;
import com.tourgether.domain.recruit.model.entity.Recruit;
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
class CommentRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private RecruitRepository recruitRepository;

    @Test
    void saveAndDelete() throws Exception {
        //given
        Member writer = createMember("email1", "pw1", "yunho1", "pyh1");
        Member member = createMember("email2", "pw2", "yunho2", "pyh2");
        memberRepository.save(writer);
        memberRepository.save(member);

        Recruit recruit = createRecruit(writer, "title", "content");
        recruitRepository.save(recruit);

        Comment target = null;

        for (int i = 1; i <= 3; i++) {
            Comment parent = createComment(recruit, member, "comment" + i);
            commentRepository.save(parent);
            if (i == 2) {
                target = parent;
            }
            for (int j = 1; j <= 3; j++) {
                Comment child = createCommentChild(recruit, member, parent, "commentChild" + j);
                commentRepository.save(child);
                Thread.sleep(10);
            }
            Thread.sleep(10);
        }

        //when
        commentRepository.delete(target);
        List<Comment> result = commentRepository.findAll();

        //then
        assertThat(result.size()).isEqualTo(8);
        assertThat(result).doesNotContain(target, target.getChildren().get(0), target.getChildren().get(1), target.getChildren().get(2));
    }

    private Comment createComment(Recruit recruit, Member writer, String content) {
        return Comment.createComment(recruit, writer, content);
    }

    private Comment createCommentChild(Recruit recruit, Member writer, Comment parent, String content) {
        return Comment.createCommentChild(recruit, writer, parent, content);
    }

    private Member createMember(String email, String password, String name, String nickname) {
        return Member.builder()
                .email(email)
                .password(password)
                .name(name)
                .nickname(nickname)
                .build();
    }

    private Recruit createRecruit(Member writer, String title, String content) {
        return Recruit.builder()
                .writer(writer)
                .title(title)
                .content(content)
                .build();
    }
}