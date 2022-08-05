package com.tourgether.domain.recruit.controller;

import com.tourgether.domain.recruit.service.dto.BookmarkQueryDto;
import com.tourgether.domain.recruit.service.dto.response.BookmarkResponseDto;
import com.tourgether.domain.recruit.model.repository.BookmarkRepository;
import com.tourgether.domain.recruit.service.BookmarkService;
import com.tourgether.util.auth.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class BookmarkController {

    private final BookmarkService bookmarkService;
    private final BookmarkRepository bookmarkRepository;

    @GetMapping("/bookmark/list")
    public String getBookmarks(@AuthenticationPrincipal UserDetailsImpl loginMember, Model model) {
        if (loginMember == null) {
            return "redirect:/member/login";
        }
        Page<BookmarkQueryDto> bookmarks = bookmarkRepository.findPageWithUserId(loginMember.getId(), Pageable.ofSize(10));
        model.addAttribute("bookmarks", bookmarks);

        return "bookmark/list";
    }

    @GetMapping("/bookmark/{bookmarkId}")
    public String selectBookmark(@PathVariable String bookmarkId, Model model) {
        BookmarkResponseDto bookmark = bookmarkService.findBookmarkDto(Long.valueOf(bookmarkId));
        Long recruitId = bookmark.getRecruitId();

        return "redirect:/recruit/" + recruitId;
    }

    @GetMapping("/bookmark/{bookmarkId}/delete")
    public String deleteBookmark(@PathVariable String bookmarkId) {
        bookmarkService.deleteBookmark(Long.valueOf(bookmarkId));
        return "redirect:/bookmark/list";
    }
}
