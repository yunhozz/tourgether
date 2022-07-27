package com.tourgether.domain.recruit.controller;

import com.tourgether.domain.recruit.model.dto.BookmarkQueryDto;
import com.tourgether.domain.recruit.model.dto.response.BookmarkResponseDto;
import com.tourgether.domain.recruit.model.repository.BookmarkRepository;
import com.tourgether.domain.recruit.service.BookmarkService;
import com.tourgether.dto.MemberSessionResponseDto;
import com.tourgether.ui.login.LoginMember;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/bookmark")
@RequiredArgsConstructor
public class BookmarkController {

    private final BookmarkService bookmarkService;
    private final BookmarkRepository bookmarkRepository;

    @GetMapping("/list")
    public String getBookmarks(@LoginMember MemberSessionResponseDto loginMember, Model model) {
        if (loginMember == null) {
            return "redirect:/member/login";
        }
        Page<BookmarkQueryDto> bookmarks = bookmarkRepository.findPageWithUserId(loginMember.getId(), Pageable.ofSize(10));
        model.addAttribute("bookmarks", bookmarks);

        return "bookmark/list";
    }

    @GetMapping("/{id}")
    public String selectBookmark(@PathVariable("id") Long bookmarkId, Model model) {
        BookmarkResponseDto bookmark = bookmarkService.findBookmarkDto(bookmarkId);
        Long recruitId = bookmark.getRecruitId();

        return "redirect:/recruit/" + recruitId;
    }

    @GetMapping("/delete/{id}")
    public String deleteBookmark(@PathVariable("id") Long bookmarkId) {
        bookmarkService.deleteBookmark(bookmarkId);
        return "redirect:/bookmark/list";
    }
}
