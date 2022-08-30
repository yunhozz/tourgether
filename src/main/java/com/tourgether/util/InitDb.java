package com.tourgether.util;

import com.tourgether.domain.chat.model.entity.Chatroom;
import com.tourgether.domain.chat.model.repository.ChatroomRepository;
import com.tourgether.domain.member.model.entity.auth.Authority;
import com.tourgether.domain.member.model.entity.Member;
import com.tourgether.domain.member.model.entity.auth.MemberAuthority;
import com.tourgether.domain.member.model.repository.MemberRepository;
import com.tourgether.domain.member.model.repository.auth.AuthorityRepository;
import com.tourgether.domain.member.model.repository.auth.MemberAuthorityRepository;
import com.tourgether.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

import static com.tourgether.dto.TokenDto.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct
    public void init() throws Exception {
        initService.initAuthority();
        initService.initMember();
        initService.initLogin();
        initService.initChatroom();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    public static class InitService {

        private final MemberService memberService;
        private final MemberRepository memberRepository;
        private final AuthorityRepository authorityRepository;
        private final MemberAuthorityRepository memberAuthorityRepository;
        private final ChatroomRepository chatroomRepository;
        private final BCryptPasswordEncoder encoder;

        public void initAuthority() {
            Authority auth1 = new Authority("ROLE_ADMIN");
            Authority auth2 = new Authority("ROLE_USER");
            authorityRepository.save(auth1);
            authorityRepository.save(auth2);
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

                memberRepository.save(member);
                memberAuthorityRepository.save(memberAuthority1);
                memberAuthorityRepository.save(memberAuthority2);
                Thread.sleep(10);
            }
        }

        public void initLogin() throws Exception {
            for (int i = 1; i <= 5; i++) {
                Member member = memberRepository.getReferenceById((long) i * 3 - 2);
                TokenResponseDto token = memberService.login(member.getEmail(), "pw" + i);
                log.info("token" + i + " = " + token.getAccessToken());
                Thread.sleep(10);
            }
        }

        public void initChatroom() throws Exception {
            for (int i = 1; i <= 5; i++) {
                Member member = memberRepository.getReferenceById((long) i * 3 - 2);
                Chatroom chatroom = new Chatroom(member, "title" + i);
                chatroomRepository.save(chatroom);
                Thread.sleep(10);
            }
        }
    }
}
