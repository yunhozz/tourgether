package com.tourgether.api;

import com.tourgether.domain.recruit.model.repository.RecruitRepository;
import com.tourgether.domain.recruit.service.RecruitService;
import com.tourgether.enums.SearchCondition;
import com.tourgether.util.auth.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.tourgether.dto.RecruitDto.*;
import static com.tourgether.enums.SearchCondition.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class RecruitApiController {

    private final RecruitService recruitService;
    private final RecruitRepository recruitRepository;

    @GetMapping("/recruits/{recruitId}")
    public ResponseEntity<RecruitResponseDto> getRecruit(@PathVariable String recruitId) {
        return ResponseEntity.ok(recruitService.findRecruitDto(Long.valueOf(recruitId)));
    }

    @GetMapping("/recruits")
    public ResponseEntity<List<RecruitResponseDto>> getRecruits() {
        return ResponseEntity.ok(recruitService.findRecruitDtoList());
    }

    @GetMapping("/recruits/page")
    public ResponseEntity<Page<RecruitQueryDto>> getRecruitsPage(@RequestParam SearchCondition condition, Pageable pageable) {
        if (condition.equals(LATEST_ORDER)) {
            return ResponseEntity.ok(recruitRepository.findPageWithCreated(pageable));
        }
        if (condition.equals(MODIFIED_ORDER)) {
            return ResponseEntity.ok(recruitRepository.findPageWithModified(pageable));
        }
        if (condition.equals(POPULARITY_ORDER)) {
            return ResponseEntity.ok(recruitRepository.findPageWithPopularity(pageable));
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/recruits/search")
    public ResponseEntity<Page<RecruitQueryDto>> getPageWithKeyword(@RequestParam(required = false) String keyword, @RequestParam(required = false) SearchCondition condition, Pageable pageable) {
        if (keyword.isEmpty() || condition == null) {
            return ResponseEntity.badRequest().build();
        }
        if (condition.equals(LATEST_ORDER)) {
            return ResponseEntity.ok(recruitRepository.findPageWithKeywordOnLatestOrder(keyword, pageable));
        }
        if (condition.equals(ACCURACY_ORDER)) {
            return ResponseEntity.ok(recruitRepository.findPageWithKeywordOnAccuracyOrder(keyword, pageable));
        }
        return null;
    }

    @PostMapping("/recruits")
    public ResponseEntity<Long> createRecruit(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody RecruitRequestDto recruitRequestDto) {
        return ResponseEntity.ok(recruitService.makeRecruit(userDetails.getMember().getId(), recruitRequestDto));
    }

    @PatchMapping("/recruits")
    public ResponseEntity<Void> updateRecruit(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestParam String recruitId, @RequestBody UpdateForm updateForm) {
        RecruitResponseDto recruit = recruitService.findRecruitDto(Long.valueOf(recruitId));
        if (!recruit.getWriterId().equals(userDetails.getMember().getId())) {
            return ResponseEntity.badRequest().build();
        }
        recruitService.updateRecruit(Long.valueOf(recruitId), recruit.getWriterId(), updateForm.getTitle(), updateForm.getContent());
        return ResponseEntity.ok().build();
    }
}
