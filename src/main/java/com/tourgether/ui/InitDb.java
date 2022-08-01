package com.tourgether.ui;

import com.tourgether.domain.member.model.entity.Authority;
import com.tourgether.domain.member.model.entity.Member;
import com.tourgether.domain.member.model.entity.MemberAuthority;
import com.tourgether.domain.member.model.repository.AuthorityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct
    public void init() throws Exception {
        initService.initAuthority();
        initService.initMember();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    public static class InitService {

        private final AuthorityRepository authorityRepository;
        private final BCryptPasswordEncoder encoder;
        private final EntityManager em;

        public void initAuthority() {
            Authority auth1 = new Authority("ROLE_ADMIN");
            Authority auth2 = new Authority("ROLE_USER");
            em.persist(auth1);
            em.persist(auth2);
        }

        public void initMember() throws Exception {
            for (int i = 1; i <= 5; i++) {
                Member member = Member.builder()
                        .email("email" + i)
                        .password(encoder.encode("pw" + i))
                        .name("yunho" + i)
                        .nickname("pyh" + i)
                        .profileImgUrl(null)
                        .oAuth2Id(null)
                        .build();

                Authority roleAdmin = authorityRepository.getReferenceById("ROLE_ADMIN");
                Authority roleUser = authorityRepository.getReferenceById("ROLE_USER");
                MemberAuthority memberAuthority1 = new MemberAuthority(member, roleAdmin);
                MemberAuthority memberAuthority2 = new MemberAuthority(member, roleUser);

                em.persist(member);
                em.persist(memberAuthority1);
                em.persist(memberAuthority2);
                Thread.sleep(10);
            }
        }
    }
}
