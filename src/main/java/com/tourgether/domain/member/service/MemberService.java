package com.tourgether.domain.member.service;

import com.tourgether.domain.member.model.entity.auth.Authority;
import com.tourgether.domain.member.model.entity.Member;
import com.tourgether.domain.member.model.entity.auth.MemberAuthority;
import com.tourgether.domain.member.model.repository.auth.AuthorityRepository;
import com.tourgether.domain.member.model.repository.auth.MemberAuthorityRepository;
import com.tourgether.domain.member.model.repository.MemberRepository;
import com.tourgether.enums.ErrorCode;
import com.tourgether.exception.member.*;
import com.tourgether.util.auth.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.tourgether.dto.MemberDto.*;
import static com.tourgether.dto.TokenDto.*;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final AuthorityRepository authorityRepository;
    private final MemberAuthorityRepository memberAuthorityRepository;
    private final JwtProvider jwtProvider;
    private final BCryptPasswordEncoder encoder;

    public Long join(MemberRequestDto memberRequestDto) {
        memberRepository.findAll().forEach(
                member -> {
                    if (member.getEmail().equals(memberRequestDto.getEmail())) {
                        throw new EmailDuplicateException("이메일이 중복된 회원이 존재합니다.", ErrorCode.EMAIL_DUPLICATION);
                    }
                    if (member.getNickname().equals(memberRequestDto.getNickname())) {
                        throw new NicknameDuplicationException("닉네임이 중복된 회원이 존재합니다.", ErrorCode.NICKNAME_DUPLICATION);
                    }
                }
        );
        Member member = memberRequestDto.toEntity();
        Authority roleUser = authorityRepository.getReferenceById("ROLE_USER");
        MemberAuthority memberAuthority = new MemberAuthority(member, roleUser); // 사용자 권한 부여
        memberAuthorityRepository.save(memberAuthority);

        return memberRepository.save(member).getId();
    }

    public TokenResponseDto login(String email, String password) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email));
        if (!encoder.matches(password, member.getPassword())) {
            throw new PasswordMismatchException("Password is not match.", ErrorCode.PASSWORD_MISMATCH);
        }
        return jwtProvider.createTokenDto(member.getId(), member.getAuthorities());
    }

    public void updatePassword(Long id, String originalPw, String newPw) {
        Member member = findMember(id);
        if (!encoder.matches(originalPw, member.getPassword())) {
            throw new PasswordMismatchException("비밀번호가 다릅니다.", ErrorCode.PASSWORD_MISMATCH);
        }
        if (originalPw.equals(newPw)) {
            throw new PasswordSameException("예전 비밀번호와 같습니다.", ErrorCode.PASSWORD_SAME);
        }
        member.updatePassword(encoder.encode(newPw));
    }

    public void updateInfo(Long id, String name, String nickname, String profileUrl) {
        Member member = findMember(id);
        member.updateInfo(name, nickname, profileUrl);
    }

    public void withdraw(Long id, String password) {
        Member member = findMember(id);
        if (!encoder.matches(password, member.getPassword())) {
            throw new PasswordMismatchException("비밀번호가 다릅니다.", ErrorCode.PASSWORD_MISMATCH);
        }
        // 추후 추가 예정
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
                .orElseThrow(() -> new MemberNotFoundException("회원정보가 없습니다.", ErrorCode.MEMBER_NOT_FOUND));
    }
}
