package com.tourgether.domain.recruit.service;

import com.tourgether.domain.member.model.entity.Member;
import com.tourgether.domain.member.model.repository.MemberRepository;
import com.tourgether.domain.member.model.repository.RecruitMemberRepository;
import com.tourgether.domain.recruit.model.entity.Bookmark;
import com.tourgether.domain.recruit.model.entity.Recruit;
import com.tourgether.domain.recruit.model.repository.ApplyRepository;
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

import static com.tourgether.dto.RecruitDto.*;

@Service
@Transactional
@RequiredArgsConstructor
public class RecruitService {

    private final RecruitRepository recruitRepository;
    private final RecruitMemberRepository recruitMemberRepository;
    private final MemberRepository memberRepository;
    private final BookmarkRepository bookmarkRepository;
    private final ApplyRepository applyRepository;

    public Long makeRecruit(Long writerId, RecruitRequestDto recruitRequestDto) {
        Member writer = memberRepository.getReferenceById(writerId);
        Recruit recruit = Recruit.builder()
                .writer(writer)
                .title(recruitRequestDto.getTitle())
                .content(recruitRequestDto.getContent())
                .view(0)
                .build();

        return recruitRepository.save(recruit).getId();
    }

    public void updateRecruit(Long id, Long writerId, String title, String content) {
        Recruit recruit = findRecruit(id);
        if (!recruit.getWriter().getId().equals(writerId)) {
            throw new WriterMismatchException(ErrorCode.WRITER_MISMATCH);
        }
        recruit.update(title, content);
    }

    public void deleteRecruit(Long id, Long writerId) {
        Recruit recruit = findRecruit(id);
        if (!recruit.getWriter().getId().equals(writerId)) {
            throw new WriterMismatchException(ErrorCode.WRITER_MISMATCH);
        }
        bookmarkRepository.findByRecruit(recruit).forEach(Bookmark::deleteRecruit);
        applyRepository.findByRecruit(recruit).forEach(applyRepository::delete);
        recruitMemberRepository.findByRecruit(recruit).forEach(recruitMemberRepository::delete);
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
                .orElseThrow(() -> new RecruitNotFoundException(ErrorCode.RECRUIT_NOT_FOUND));
    }
}
