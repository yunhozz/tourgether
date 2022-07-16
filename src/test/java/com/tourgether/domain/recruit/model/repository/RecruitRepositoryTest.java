package com.tourgether.domain.recruit.model.repository;

import com.tourgether.domain.member.model.Member;
import com.tourgether.domain.member.model.repository.MemberRepository;
import com.tourgether.domain.recruit.model.dto.RecruitQueryDto;
import com.tourgether.domain.recruit.model.entity.Recruit;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@Commit
class RecruitRepositoryTest {

    @Autowired
    private RecruitRepository recruitRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void addView() throws Exception {
        //given
        Member writer = createMember("email", "123", "yunho", "pyh123");
        Recruit recruit = createRecruit(writer, "title", "content");
        memberRepository.save(writer);
        recruitRepository.save(recruit);

        //when
        for (int i = 0; i < 5; i++) {
            recruitRepository.addView(recruit.getId());
        }

        //then
        assertThat(recruit.getView()).isEqualTo(0); // view = 5
    }

    @Test
    void findSimplePage() throws Exception {
        //given
        for (int i = 1; i <= 3; i++) {
            Member writer = createMember("email" + i, String.valueOf(i), "yunho" + i, "pyh" + i);
            memberRepository.save(writer);
            for (int j = 1; j <= 5; j++) {
                Recruit recruit = createRecruit(writer, randomStr(), randomStr());
                recruitRepository.save(recruit);
                Thread.sleep(10);
            }
            Thread.sleep(10);
        }
        PageRequest pageable = PageRequest.of(0, 10);

        //when
        Page<RecruitQueryDto> result = recruitRepository.findSimplePage(pageable);

        //then
        assertThat(result.getSize()).isEqualTo(10);
        assertThat(result.getTotalPages()).isEqualTo(2);
        assertThat(result).extracting("writerId").contains(7L, 13L);
    }

    @Test
    void findPageWithPopularity() throws Exception {
        //given
        for (int i = 1; i <= 3; i++) {
            Member writer = createMember("email" + i, String.valueOf(i), "yunho" + i, "pyh" + i);
            memberRepository.save(writer);
            for (int j = 1; j <= 5; j++) {
                Recruit recruit = createRecruit(writer, randomStr(), randomStr());
                recruitRepository.save(recruit);
                for (int k = 0; k <= Math.random() * 10; k++) {
                    recruitRepository.addView(recruit.getId());
                }
                Thread.sleep(10);
            }
            Thread.sleep(10);
        }
        PageRequest pageable = PageRequest.of(0, 10);

        //when
        Page<RecruitQueryDto> result = recruitRepository.findPageWithPopularity(pageable);

        //then
        assertThat(result.getSize()).isEqualTo(10);
        assertThat(result.getTotalPages()).isEqualTo(2);
        for (RecruitQueryDto recruit : result) {
            System.out.println("id: " + recruit.getId() + ", view: " + recruit.getView());
        }
    }

    @Test
    void findPageWithKeyword() throws Exception {
        //given
        for (int i = 1; i <= 3; i++) {
            Member writer = createMember("email" + i, String.valueOf(i), "yunho" + i, "pyh" + i);
            memberRepository.save(writer);
            for (int j = 1; j <= 5; j++) {
                Recruit recruit = createRecruit(writer, randomStr(), randomStr());
                recruitRepository.save(recruit);
                Thread.sleep(10);
            }
            Thread.sleep(10);
        }
        PageRequest pageable = PageRequest.of(0, 10);

        //when
        Page<RecruitQueryDto> result = recruitRepository.findPageWithKeyword("A", pageable);

        //then
        assertThat(result.getSize()).isEqualTo(10);
        assertThat(result.getTotalPages()).isEqualTo(2);
        for (RecruitQueryDto recruit : result) {
            System.out.println("id: " + recruit.getId() + ", title: " + recruit.getTitle() + ", content: " + recruit.getContent());
        }
    }

    @Test
    void findPageWithKeywordOnLatestOrder() throws Exception {
        //given
        for (int i = 1; i <= 3; i++) {
            Member writer = createMember("email" + i, String.valueOf(i), "yunho" + i, "pyh" + i);
            memberRepository.save(writer);
            for (int j = 1; j <= 5; j++) {
                Recruit recruit = createRecruit(writer, randomStr(), randomStr());
                recruitRepository.save(recruit);
                Thread.sleep(10);
            }
            Thread.sleep(10);
        }
        PageRequest pageable = PageRequest.of(0, 10);

        //when
        List<Recruit> recruits = recruitRepository.findAll();
        for (Recruit recruit : recruits) {
            if (recruit.getId() % 5 == 0) {
                recruit.update("UPDATE-TITLE", "UPDATE-CONTENT");
            }
            Thread.sleep(10);
        }
        Page<RecruitQueryDto> result = recruitRepository.findPageWithKeywordOnLatestOrder("A", pageable);

        //then
        assertThat(result.getSize()).isEqualTo(10);
        assertThat(result.getTotalPages()).isEqualTo(2);
        for (RecruitQueryDto recruit : result) {
            System.out.println("id: " + recruit.getId() + ", title: " + recruit.getTitle() + ", content: " + recruit.getContent());
        }
    }

    @Test
    void findPageWithKeywordOnAccuracyOrder() throws Exception {
        //given
        for (int i = 1; i <= 3; i++) {
            Member writer = createMember("email" + i, String.valueOf(i), "yunho" + i, "pyh" + i);
            memberRepository.save(writer);
            for (int j = 1; j <= 5; j++) {
                Recruit recruit = createRecruit(writer, randomStr(), randomStr());
                recruitRepository.save(recruit);
                Thread.sleep(10);
            }
            Thread.sleep(10);
        }
        PageRequest pageable = PageRequest.of(0, 10);

        //when
        Page<RecruitQueryDto> result = recruitRepository.findPageWithKeywordOnAccuracyOrder("A", pageable);

        //then
        assertThat(result.getSize()).isEqualTo(10);
        assertThat(result.getTotalPages()).isEqualTo(2);
        for (RecruitQueryDto recruit : result) {
            System.out.println("id: " + recruit.getId() + ", title: " + recruit.getTitle() + ", content: " + recruit.getContent());
        }
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

    private String randomStr() {
        String[] arr = new String[6];
        int[] ascii = new int[26];
        int num = 65;

        for (int i = 0; i < 26; i++) {
            ascii[i] = num++;
        }
        for (int i = 0; i < 6; i++) {
            arr[i] = Character.toString(ascii[(int) (Math.random() * 25)]);
        }
        return Arrays.toString(arr)
                .replace("[", "")
                .replace("]", "")
                .replace(", ", "");
    }
}