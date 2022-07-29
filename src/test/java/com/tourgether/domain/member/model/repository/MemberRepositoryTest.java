package com.tourgether.domain.member.model.repository;

import com.tourgether.domain.member.model.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void findByEmail() throws Exception {
        //given
        Member member = createMember();
        memberRepository.save(member);

        //when
        memberRepository.findByEmail("email");

        //then
    }

    private Member createMember() {
        return Member.builder()
                .email("email")
                .password("123")
                .build();
    }
}