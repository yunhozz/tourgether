package com.tourgether.domain.recruit.service;

import com.tourgether.domain.member.model.entity.Member;
import com.tourgether.domain.member.model.repository.MemberRepository;
import com.tourgether.domain.recruit.dto.response.BookmarkResponseDto;
import com.tourgether.domain.recruit.model.entity.Bookmark;
import com.tourgether.domain.recruit.model.entity.Recruit;
import com.tourgether.domain.recruit.model.repository.BookmarkRepository;
import com.tourgether.domain.recruit.model.repository.RecruitRepository;
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
        Member member = memberRepository.getReferenceById(userId);
        Recruit recruit = recruitRepository.getReferenceById(recruitId);
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
