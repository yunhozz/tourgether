package com.tourgether.domain.member.service;

import com.tourgether.domain.member.model.entity.Member;
import com.tourgether.domain.member.controller.form.UpdateForm;
import com.tourgether.domain.member.model.dto.MemberRequestDto;
import com.tourgether.domain.member.model.dto.MemberResponseDto;
import com.tourgether.domain.member.model.repository.MemberRepository;
import com.tourgether.enums.ErrorCode;
import com.tourgether.exception.EmailDuplicateException;
import com.tourgether.exception.MemberNotFoundException;
import com.tourgether.exception.NicknameDuplicationException;
import com.tourgether.exception.PasswordMismatchException;
import com.tourgether.ui.SessionConstants;
import com.tourgether.ui.auth.UserDetailsImpl;
import com.tourgether.dto.MemberSessionResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder encoder;
    private final HttpSession session;

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
        memberRepository.save(member);

        return member.getId();
    }

    @Transactional(readOnly = true)
    public MemberSessionResponseDto login(UserDetails userDetails, String password) {
        if (!encoder.matches(password, userDetails.getPassword())) {
            throw new PasswordMismatchException("비밀번호가 다릅니다.", ErrorCode.PASSWORD_MISMATCH);
        }
        UserDetailsImpl userDetailsImpl = (UserDetailsImpl) userDetails;
        session.setAttribute(SessionConstants.LOGIN_MEMBER, userDetailsImpl.getMember()); // 세션에 회원 정보 저장

        return new MemberSessionResponseDto(userDetailsImpl.getMember());
    }

    public void updatePassword(Long id, String originalPw, String newPw) {
        Member member = findMember(id);
        if (!encoder.matches(originalPw, member.getPassword())) {
            throw new PasswordMismatchException("비밀번호가 다릅니다.", ErrorCode.PASSWORD_MISMATCH);
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
                .orElseThrow(() -> new MemberNotFoundException("회원정보가 없습니다.", ErrorCode.MEMBER_NOT_FOUND));
    }
}
