package com.tourgether.domain.member.service;

import com.tourgether.domain.member.Member;
import com.tourgether.domain.member.controller.LoginForm;
import com.tourgether.domain.member.controller.UpdateForm;
import com.tourgether.domain.member.dto.MemberRequestDto;
import com.tourgether.domain.member.dto.MemberResponseDto;
import com.tourgether.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final UserDetailsServiceImpl userDetailsService;
    private final BCryptPasswordEncoder encoder;

    public Long join(MemberRequestDto memberRequestDto) {
        memberRepository.findAll().forEach(
                member -> {
                    if (member.getEmail().equals(memberRequestDto.getEmail())) {
                        throw new IllegalStateException("이메일이 중복된 회원이 존재합니다.");
                    }
                }
        );
        Member member = memberRequestDto.toEntity();
        memberRepository.save(member);

        return member.getId();
    }

    public void login(LoginForm loginForm) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(loginForm.getEmail()); // 이메일 존재 여부 검증 -> 세션 생성
        if (!encoder.matches(loginForm.getPassword(), userDetails.getPassword())) {
            throw new IllegalStateException("비밀번호가 다릅니다.");
        }
    }

    public void updatePassword(Long id, String originalPw, String newPw) {
        Member member = findMember(id);
        if (!encoder.matches(originalPw, member.getPassword())) {
            throw new IllegalStateException("비밀번호가 다릅니다.");
        }
        member.updatePassword(newPw);
    }

    public void updateInfo(Long id, UpdateForm updateForm) {
        Member member = findMember(id);
        member.updateInfo(updateForm.getName(), updateForm.getNickname(), updateForm.getProfileUrl());
    }

    public void withdraw(Long id) {

    }

    @Transactional(readOnly = true)
    public MemberResponseDto findMemberDto(Long id) {
        return new MemberResponseDto(findMember(id));
    }

    @Transactional(readOnly = true)
    public List<MemberResponseDto> findMemberDtoList() {
        return memberRepository.findAll().stream()
                .map(MemberResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    private Member findMember(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("This member is null : " + id));
    }
}
