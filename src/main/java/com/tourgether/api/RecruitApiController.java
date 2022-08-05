package com.tourgether.api;

import com.tourgether.domain.recruit.service.dto.RecruitQueryDto;
import com.tourgether.domain.recruit.service.dto.response.RecruitResponseDto;
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
    public RecruitResponseDto findRecruit(@PathVariable String recruitId) {
        return recruitService.findRecruitDto(Long.valueOf(recruitId));
    }

    @GetMapping("/recruits")
    public List<RecruitResponseDto> findRecruits() {
        return recruitService.findRecruitDtoList();
    }

    @GetMapping("/page/recruits-created")
    public Page<RecruitQueryDto> findPageWithCreated(Pageable pageable) {
        return recruitRepository.findPageWithCreated(pageable);
    }

    @GetMapping("/page/recruits-modified")
    public Page<RecruitQueryDto> findPageWithModified(Pageable pageable) {
        return recruitRepository.findPageWithModified(pageable);
    }

    @GetMapping("/page/recruits-popularity")
    public Page<RecruitQueryDto> findPageWithPopularity(Pageable pageable) {
        return recruitRepository.findPageWithPopularity(pageable);
    }

    @GetMapping("/page/recruits-created/search")
    public Page<RecruitQueryDto> findPageWithKeyword(@RequestParam String keyword, Pageable pageable) {
        return recruitRepository.findPageWithKeyword(keyword, pageable);
    }

    @GetMapping("/page/recruits-modified/search")
    public Page<RecruitQueryDto> findPageWithKeywordOnLatestOrder(String keyword, Pageable pageable) {
        return recruitRepository.findPageWithKeywordOnLatestOrder(keyword, pageable);
    }

    @GetMapping("/page/recruits-accuracy/search")
    public Page<RecruitQueryDto> findPageWithKeywordOnAccuracyOrder(String keyword, Pageable pageable) {
        return recruitRepository.findPageWithKeywordOnAccuracyOrder(keyword, pageable);
    }
}
