package com.tourgether.domain.recruit.model.repository;

import com.tourgether.domain.member.model.entity.Member;
import com.tourgether.domain.member.model.repository.MemberRepository;
import com.tourgether.domain.recruit.model.entity.Bookmark;
import com.tourgether.domain.recruit.model.entity.Recruit;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@Commit
class BookmarkRepositoryTest {

    @Autowired
    private BookmarkRepository bookmarkRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private RecruitRepository recruitRepository;

    @Test
    void findWithRecruitId() throws Exception {
        //given
        Member writer = createMember("writer", "writer", "writer", "writer");
        Member member1 = createMember("email1", "pw1", "yunho1", "pyh1");
        Member member2 = createMember("email2", "pw2", "yunho2", "pyh2");
        Member member3 = createMember("email3", "pw3", "yunho3", "pyh3");
        memberRepository.save(writer);
        memberRepository.save(member1);
        memberRepository.save(member2);
        memberRepository.save(member3);

        Recruit recruit = createRecruit(writer, "title1", "content1");
        recruitRepository.save(recruit);

        Bookmark bookmark1 = createBookmark(member1, recruit);
        Bookmark bookmark2 = createBookmark(member2, recruit);
        Bookmark bookmark3 = createBookmark(member3, recruit);
        bookmarkRepository.save(bookmark1);
        bookmarkRepository.save(bookmark2);
        bookmarkRepository.save(bookmark3);

        //when
        List<Bookmark> result = bookmarkRepository.findWithRecruitId(recruit.getId());

        //then
        assertThat(result.size()).isEqualTo(3);
        assertThat(result).extracting("recruit").containsOnly(recruit);
        assertThat(result).extracting("member").contains(member1, member2, member3);
    }

    @Test
    void deleteBookmark() throws Exception {
        //given
        Member writer = createMember("writer", "writer", "writer", "writer");
        Member member1 = createMember("email1", "pw1", "yunho1", "pyh1");
        Member member2 = createMember("email2", "pw2", "yunho2", "pyh2");
        Member member3 = createMember("email3", "pw3", "yunho3", "pyh3");
        memberRepository.save(writer);
        memberRepository.save(member1);
        memberRepository.save(member2);
        memberRepository.save(member3);

        Recruit recruit = createRecruit(writer, "title1", "content1");
        recruitRepository.save(recruit);

        Bookmark bookmark1 = createBookmark(member1, recruit);
        Bookmark bookmark2 = createBookmark(member2, recruit);
        Bookmark bookmark3 = createBookmark(member3, recruit);
        bookmarkRepository.save(bookmark1);
        bookmarkRepository.save(bookmark2);
        bookmarkRepository.save(bookmark3);

        //when
        bookmarkRepository.deleteBookmark(bookmark1.getId());
        List<Bookmark> result = bookmarkRepository.findAll();

        //then
        assertThat(result.size()).isEqualTo(2);
        assertThat(result).extracting("id").contains(bookmark2.getId(), bookmark3.getId());
        assertThat(bookmarkRepository.findById(bookmark1.getId())).isNotPresent();
    }

    @Test
    void findPageWithUserId() throws Exception {
        //given
        Member writer = createMember("writer", "writer", "writer", "writer");
        Member member1 = createMember("email1", "pw1", "yunho1", "pyh1");
        Member member2 = createMember("email2", "pw2", "yunho2", "pyh2");
        Member member3 = createMember("email3", "pw3", "yunho3", "pyh3");
        memberRepository.save(writer);
        memberRepository.save(member1);
        memberRepository.save(member2);
        memberRepository.save(member3);

        Recruit recruit = createRecruit(writer, "title1", "content1");
        recruitRepository.save(recruit);

        Bookmark bookmark1 = createBookmark(member1, recruit);
        Bookmark bookmark2 = createBookmark(member2, recruit);
        Bookmark bookmark3 = createBookmark(member3, recruit);
        bookmarkRepository.save(bookmark1);
        bookmarkRepository.save(bookmark2);
        bookmarkRepository.save(bookmark3);

        //when
        PageRequest pageable = PageRequest.of(0, 10);
        bookmarkRepository.findPageWithUserId(member1.getId(), pageable);

        //then
    }

    private Bookmark createBookmark(Member member, Recruit recruit) {
        return new Bookmark(member, recruit);
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