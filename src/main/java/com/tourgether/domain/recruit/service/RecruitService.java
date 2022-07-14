package com.tourgether.domain.recruit.service;

import com.tourgether.domain.member.model.Member;
import com.tourgether.domain.member.model.repository.MemberRepository;
import com.tourgether.domain.recruit.controller.UpdateForm;
import com.tourgether.domain.recruit.model.dto.request.RecruitRequestDto;
import com.tourgether.domain.recruit.model.dto.response.RecruitResponseDto;
import com.tourgether.domain.recruit.model.entity.Recruit;
import com.tourgether.domain.recruit.model.repository.RecruitRepository;
import com.tourgether.enums.ErrorCode;
import com.tourgether.exception.MemberNotFoundException;
import com.tourgether.exception.RecruitNotFoundException;
import com.tourgether.exception.WriterMismatchException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class RecruitService {

    private final RecruitRepository recruitRepository;
    private final MemberRepository memberRepository;

    public Long makeRecruit(RecruitRequestDto recruitRequestDto, Long userId) {
        Member writer = memberRepository.findById(userId)
                .orElseThrow(() -> new MemberNotFoundException("This member is null : " + userId, ErrorCode.MEMBER_NOT_FOUND));
        recruitRequestDto.setWriter(writer);

        return recruitRepository.save(recruitRequestDto.toEntity()).getId();
    }

    public void updateRecruit(Long id, UpdateForm updateForm) {
        Recruit recruit = findRecruit(id);
        recruit.update(updateForm.getTitle(), updateForm.getContent());
    }

    public void deleteRecruit(Long id, Long userId) {
        Recruit recruit = findRecruit(id);
        if (!recruit.getWriter().getId().equals(userId)) {
            throw new WriterMismatchException("This member don't match on recruitment : " + userId, ErrorCode.WRITER_MISMATCH);
        }
        recruitRepository.delete(recruit); // 추후 북마크 고려
    }

    @Transactional(readOnly = true)
    public RecruitResponseDto findRecruitDto(Long id) {
        return new RecruitResponseDto(findRecruit(id));
    }

    @Transactional(readOnly = true)
    public List<RecruitResponseDto> findRecruitDtoList() {
        return recruitRepository.findAll().stream()
                .map(RecruitResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    private Recruit findRecruit(Long id) {
        return recruitRepository.findById(id)
                .orElseThrow(() -> new RecruitNotFoundException("This recruitment is null : " + id, ErrorCode.RECRUIT_NOT_FOUND));
    }
}
