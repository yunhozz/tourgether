package com.tourgether.domain.member.service;

import com.tourgether.domain.member.Member;
import com.tourgether.domain.member.UserDetailsImpl;
import com.tourgether.domain.member.repository.MemberRepository;
import com.tourgether.global.dto.MemberSessionResponseDto;
import com.tourgether.global.ui.SessionConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private HttpSession session;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email));
        session.setAttribute(SessionConstants.LOGIN_MEMBER, new MemberSessionResponseDto(member));

        return new UserDetailsImpl(member);
    }
}
