package com.tourgether.domain.recruit.service;

import com.tourgether.domain.member.model.entity.Member;
import com.tourgether.domain.member.model.repository.MemberRepository;
import com.tourgether.domain.recruit.controller.form.RecruitForm;
import com.tourgether.domain.recruit.controller.form.UpdateForm;
import com.tourgether.domain.recruit.service.dto.request.RecruitRequestDto;
import com.tourgether.domain.recruit.service.dto.response.RecruitResponseDto;
import com.tourgether.domain.recruit.model.entity.Bookmark;
import com.tourgether.domain.recruit.model.entity.Recruit;
import com.tourgether.domain.recruit.model.repository.BookmarkRepository;
import com.tourgether.domain.recruit.model.repository.RecruitRepository;
import com.tourgether.enums.ErrorCode;
import com.tourgether.exception.recruit.RecruitNotFoundException;
import com.tourgether.exception.recruit.WriterMismatchException;
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
    private final BookmarkRepository bookmarkRepository;

    public Long makeRecruit(RecruitForm recruitForm, Long writerId) {
        Member writer = memberRepository.getReferenceById(writerId);
        RecruitRequestDto recruitRequestDto = RecruitRequestDto.builder()
                .writer(writer)
                .title(recruitForm.getTitle())
                .content(recruitForm.getContent())
                .view(0)
                .build();

        return recruitRepository.save(recruitRequestDto.toEntity()).getId();
    }

    public void updateRecruit(Long id, UpdateForm updateForm) {
        Recruit recruit = findRecruit(id);
        recruit.update(updateForm.getTitle(), updateForm.getContent());
    }

    public void deleteRecruit(Long id, Long writerId) {
        Recruit recruit = findRecruit(id);
        if (!recruit.getWriter().getId().equals(writerId)) {
            throw new WriterMismatchException("This member don't match on recruitment : " + writerId, ErrorCode.WRITER_MISMATCH);
        }
        List<Bookmark> bookmarks = bookmarkRepository.findWithRecruitId(recruit.getId());
        bookmarks.forEach(Bookmark::deleteRecruit);
        recruitRepository.delete(recruit);
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
