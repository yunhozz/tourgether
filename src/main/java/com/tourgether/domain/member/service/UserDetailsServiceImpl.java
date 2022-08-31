package com.tourgether.domain.member.service;

import com.tourgether.domain.member.model.entity.Member;
import com.tourgether.domain.member.model.entity.auth.MemberAuthority;
import com.tourgether.domain.member.model.repository.MemberRepository;
import com.tourgether.domain.member.model.repository.auth.MemberAuthorityRepository;
import com.tourgether.enums.ErrorCode;
import com.tourgether.exception.member.MemberNotFoundException;
import com.tourgether.util.auth.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final MemberAuthorityRepository memberAuthorityRepository;

    @Override
    public UserDetailsImpl loadUserByUsername(String userId) throws UsernameNotFoundException {
        Member member = memberRepository.findById(Long.valueOf(userId))
                .orElseThrow(() -> new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND));
        Set<MemberAuthority> authorities = memberAuthorityRepository.findByMember(member);

        return new UserDetailsImpl(member, authorities);
    }
}
