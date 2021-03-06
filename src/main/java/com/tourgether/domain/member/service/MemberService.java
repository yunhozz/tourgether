package com.tourgether.domain.member.service;

import com.tourgether.domain.member.model.entity.Authority;
import com.tourgether.domain.member.model.entity.Member;
import com.tourgether.domain.member.controller.form.UpdateForm;
import com.tourgether.domain.member.model.dto.request.MemberRequestDto;
import com.tourgether.domain.member.model.dto.response.MemberResponseDto;
import com.tourgether.domain.member.model.entity.MemberAuthority;
import com.tourgether.domain.member.model.entity.Team;
import com.tourgether.domain.member.model.repository.AuthorityRepository;
import com.tourgether.domain.member.model.repository.MemberAuthorityRepository;
import com.tourgether.domain.member.model.repository.MemberRepository;
import com.tourgether.domain.member.model.repository.TeamRepository;
import com.tourgether.dto.MemberSessionResponseDto;
import com.tourgether.enums.ErrorCode;
import com.tourgether.exception.member.EmailDuplicateException;
import com.tourgether.exception.member.MemberNotFoundException;
import com.tourgether.exception.member.NicknameDuplicationException;
import com.tourgether.exception.member.PasswordMismatchException;
import com.tourgether.util.SessionConstants;
import com.tourgether.util.auth.UserDetailsImpl;
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
    private final AuthorityRepository authorityRepository;
    private final MemberAuthorityRepository memberAuthorityRepository;
    private final TeamRepository teamRepository;
    private final BCryptPasswordEncoder encoder;
    private final HttpSession session;

    public Long join(MemberRequestDto memberRequestDto) {
        memberRepository.findAll().forEach(
                member -> {
                    if (member.getEmail().equals(memberRequestDto.getEmail())) {
                        throw new EmailDuplicateException("???????????? ????????? ????????? ???????????????.", ErrorCode.EMAIL_DUPLICATION);
                    }
                    if (member.getNickname().equals(memberRequestDto.getNickname())) {
                        throw new NicknameDuplicationException("???????????? ????????? ????????? ???????????????.", ErrorCode.NICKNAME_DUPLICATION);
                    }
                }
        );
        Member member = memberRequestDto.toEntity();
        Authority roleUser = authorityRepository.getReferenceById("ROLE_USER");
        MemberAuthority memberAuthority = new MemberAuthority(member, roleUser); // ????????? ?????? ??????
        memberAuthorityRepository.save(memberAuthority);

        return memberRepository.save(member).getId();
    }

    @Transactional(readOnly = true)
    public void login(UserDetails userDetails, String password) {
        if (!encoder.matches(password, userDetails.getPassword())) {
            throw new PasswordMismatchException("??????????????? ????????????.", ErrorCode.PASSWORD_MISMATCH);
        }
        UserDetailsImpl userDetailsImpl = (UserDetailsImpl) userDetails;
        session.setAttribute(SessionConstants.LOGIN_MEMBER, new MemberSessionResponseDto(userDetailsImpl.getMember())); // ????????? ?????? ?????? ??????
    }

    public void updatePassword(Long id, String originalPw, String newPw) {
        Member member = findMember(id);
        if (!encoder.matches(originalPw, member.getPassword())) {
            throw new PasswordMismatchException("??????????????? ????????????.", ErrorCode.PASSWORD_MISMATCH);
        }
        member.updatePassword(newPw);
    }

    public void updateInfo(Long id, UpdateForm updateForm) {
        Member member = findMember(id);
        member.updateInfo(updateForm.getName(), updateForm.getNickname(), updateForm.getProfileUrl());
    }

    public void withdraw(Long id, String password) {
        Member member = findMember(id);
        if (!encoder.matches(password, member.getPassword())) {
            throw new PasswordMismatchException("??????????????? ????????????.", ErrorCode.PASSWORD_MISMATCH);
        }
        // ?????? ?????? ??????
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
                .orElseThrow(() -> new MemberNotFoundException("??????????????? ????????????.", ErrorCode.MEMBER_NOT_FOUND));
    }
}
