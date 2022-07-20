package com.tourgether.ui;

import com.tourgether.domain.member.model.Member;
import com.tourgether.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    public void init() throws Exception {
        initService.initMember();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    public static class InitService {

        private final EntityManager em;

        public void initMember() throws Exception {
            for (int i = 1; i <= 5; i++) {
                Member member = Member.builder()
                        .email("email" + i)
                        .password("pw" + i)
                        .name("yunho" + i)
                        .nickname("pyh" + i)
                        .profileImgUrl(null)
                        .oAuth2Id(null)
                        .role(Role.USER)
                        .build();

                em.persist(member);
                Thread.sleep(10);
            }
        }
    }
}
