package com.tourgether.domain.recruit.service;

import com.tourgether.domain.member.model.Member;
import com.tourgether.domain.member.model.repository.MemberRepository;
import com.tourgether.domain.recruit.model.dto.response.BookmarkResponseDto;
import com.tourgether.domain.recruit.model.entity.Bookmark;
import com.tourgether.domain.recruit.model.entity.Recruit;
import com.tourgether.domain.recruit.model.repository.BookmarkRepository;
import com.tourgether.domain.recruit.model.repository.RecruitRepository;
import com.tourgether.enums.ErrorCode;
import com.tourgether.exception.MemberNotFoundException;
import com.tourgether.exception.RecruitNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class BookmarkService {

    private final BookmarkRepository bookmarkRepository;
    private final MemberRepository memberRepository;
    private final RecruitRepository recruitRepository;

    public Long makeBookmark(Long userId, Long recruitId) {
        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new MemberNotFoundException("This member is null: " + userId, ErrorCode.MEMBER_NOT_FOUND));
        Recruit recruit = recruitRepository.findById(recruitId)
                .orElseThrow(() -> new RecruitNotFoundException("This recruitment is null: " + recruitId, ErrorCode.RECRUIT_NOT_FOUND));
        Bookmark bookmark = new Bookmark(member, recruit);

        bookmarkRepository.save(bookmark);
        return bookmark.getId();
    }

    @Transactional(readOnly = true)
    public BookmarkResponseDto findBookmarkDto(Long id) {
        return new BookmarkResponseDto(findBookmark(id));
    }

    @Transactional(readOnly = true)
    public List<BookmarkResponseDto> findBookmarkDtoList() {
        return bookmarkRepository.findAll().stream()
                .map(BookmarkResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    private Bookmark findBookmark(Long id) {
        return bookmarkRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("This bookmark is null: " + id));
    }
}