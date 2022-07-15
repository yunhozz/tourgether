package com.tourgether.domain.recruit.controller;

import com.tourgether.domain.recruit.model.dto.RecruitQueryDto;
import com.tourgether.domain.recruit.model.dto.response.RecruitResponseDto;
import com.tourgether.domain.recruit.model.repository.RecruitRepository;
import com.tourgether.domain.recruit.service.RecruitService;
import com.tourgether.dto.MemberSessionResponseDto;
import com.tourgether.enums.SearchCondition;
import com.tourgether.ui.login.LoginMember;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/recruit")
@RequiredArgsConstructor
public class RecruitController {

    private final RecruitService recruitService;
    private final RecruitRepository recruitRepository;

    @GetMapping
    public String recruitPage(@LoginMember MemberSessionResponseDto loginMember, @ModelAttribute SearchForm searchForm, @PageableDefault(size = 10) Pageable pageable,
                              Model model) {
        if (loginMember == null) {
            return "redirect:/member/signIn";
        }
        Page<RecruitQueryDto> recruits = recruitRepository.findSimplePage(pageable);
        model.addAttribute("recruits", recruits);
        model.addAttribute("isPopularity", false);

        return "recruit/list";
    }

    @GetMapping("/popular")
    public String recruitPageWithPopularity(@LoginMember MemberSessionResponseDto loginMember, @PageableDefault(size = 10) Pageable pageable, Model model) {
        if (loginMember == null) {
            return "redirect:/member/signIn";
        }
        Page<RecruitQueryDto> recruits = recruitRepository.findPageWithPopularity(pageable);
        model.addAttribute("recruits", recruits);
        model.addAttribute("isPopularity", true);

        return "recruit/list";
    }

    @GetMapping("/search")
    public String searchRecruit(@Valid @RequestBody SearchForm searchForm, BindingResult result, @PageableDefault(size = 10) Pageable pageable, Model model) {
        if (result.hasErrors()) {
            return "recruit/list";
        }
        Page<RecruitQueryDto> recruits = null;
        if (searchForm.getCondition().equals(SearchCondition.ACCURACY_ORDER)) {
            recruits = recruitRepository.findPageWithKeywordOnAccuracyOrder(searchForm.getKeyword(), pageable); // 정확도순
        } else if (searchForm.getCondition().equals(SearchCondition.LATEST_ORDER)){
            recruits = recruitRepository.findPageWithKeywordOnLatestOrder(searchForm.getKeyword(), pageable); // 수정순
        } else if (searchForm.getCondition() == null) {
            recruits = recruitRepository.findPageWithKeyword(searchForm.getKeyword(), pageable); // 생성순
        }
        model.addAttribute("recruits", recruits);
        model.addAttribute("isPopularity", false);

        return "recruit/list";
    }

    @GetMapping("/{id}")
    public String readRecruit(@LoginMember MemberSessionResponseDto loginMember, @PathVariable("id") Long recruitId, Model model) {
        if (loginMember == null) {
            return "redirect:/member/signIn";
        }
        RecruitResponseDto recruit = recruitService.findRecruitDto(recruitId);
        recruitRepository.addView(recruit.getId()); // 조회수 증가 -> 추후 조회수 중복 고려
        model.addAttribute("recruit", recruit);

        return "recruit/detail";
    }
}
