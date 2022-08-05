package com.tourgether.api;

import com.tourgether.domain.recruit.dto.RecruitQueryDto;
import com.tourgether.domain.recruit.dto.response.RecruitResponseDto;
import com.tourgether.domain.recruit.model.repository.RecruitRepository;
import com.tourgether.domain.recruit.service.RecruitService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class RecruitApiController {

    private final RecruitService recruitService;
    private final RecruitRepository recruitRepository;

    @GetMapping("/recruit/{recruitId}")
    public RecruitResponseDto getRecruit(@PathVariable String recruitId) {
        return recruitService.findRecruitDto(Long.valueOf(recruitId));
    }

    @GetMapping("/recruit/list")
    public List<RecruitResponseDto> getRecruits() {
        return recruitService.findRecruitDtoList();
    }

    @GetMapping("/page/recruits-created")
    public Page<RecruitQueryDto> getPageWithCreated(Pageable pageable) {
        return recruitRepository.findPageWithCreated(pageable);
    }

    @GetMapping("/page/recruits-modified")
    public Page<RecruitQueryDto> getPageWithModified(Pageable pageable) {
        return recruitRepository.findPageWithModified(pageable);
    }

    @GetMapping("/page/recruits-popularity")
    public Page<RecruitQueryDto> getPageWithPopularity(Pageable pageable) {
        return recruitRepository.findPageWithPopularity(pageable);
    }

    @GetMapping("/page/recruits-created/search")
    public Page<RecruitQueryDto> getPageWithKeyword(@RequestParam String keyword, Pageable pageable) {
        return recruitRepository.findPageWithKeyword(keyword, pageable);
    }

    @GetMapping("/page/recruits-modified/search")
    public Page<RecruitQueryDto> getPageWithKeywordOnLatestOrder(@RequestParam String keyword, Pageable pageable) {
        return recruitRepository.findPageWithKeywordOnLatestOrder(keyword, pageable);
    }

    @GetMapping("/page/recruits-accuracy/search")
    public Page<RecruitQueryDto> getPageWithKeywordOnAccuracyOrder(@RequestParam String keyword, Pageable pageable) {
        return recruitRepository.findPageWithKeywordOnAccuracyOrder(keyword, pageable);
    }
}
