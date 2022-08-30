package com.tourgether.api;

import com.tourgether.domain.recruit.model.repository.RecruitRepository;
import com.tourgether.domain.recruit.service.RecruitService;
import com.tourgether.enums.ErrorCode;
import com.tourgether.enums.SearchCondition;
import com.tourgether.dto.ErrorResponseDto;
import com.tourgether.util.auth.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.tourgether.dto.RecruitDto.*;
import static com.tourgether.enums.SearchCondition.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class RecruitApiController {

    private final RecruitService recruitService;
    private final RecruitRepository recruitRepository;

    @GetMapping("/recruit/{recruitId}")
    public ResponseEntity<RecruitResponseDto> getRecruit(@PathVariable String recruitId) {
        return ResponseEntity.ok(recruitService.findRecruitDto(Long.valueOf(recruitId)));
    }

    @GetMapping("/recruit")
    public ResponseEntity<List<RecruitResponseDto>> getRecruits() {
        return ResponseEntity.ok(recruitService.findRecruitDtoList());
    }

    @GetMapping("/recruit/page")
    public ResponseEntity<Page<RecruitQueryDto>> getRecruitsPage(@RequestParam(defaultValue = "LATEST", required = false) SearchCondition condition, Pageable pageable) {
        if (condition.equals(MODIFIED)) {
            return ResponseEntity.ok(recruitRepository.findPageWithModified(pageable));
        }
        if (condition.equals(POPULARITY)) {
            return ResponseEntity.ok(recruitRepository.findPageWithPopularity(pageable));
        }
        return ResponseEntity.ok(recruitRepository.findPageWithCreated(pageable));
    }

    @GetMapping("/recruit/search")
    public ResponseEntity<Object> getPageWithKeyword(@RequestParam(required = false) String keyword, @RequestParam(defaultValue = "LATEST", required = false) SearchCondition condition, Pageable pageable) {
        if (!StringUtils.hasText(keyword)) {
            return ResponseEntity.badRequest().body(new ErrorResponseDto(ErrorCode.KEYWORD_NOT_ENTERED));
        }
        if (condition.equals(ACCURACY)) {
            return ResponseEntity.ok(recruitRepository.findPageWithKeywordOnAccuracyOrder(keyword, pageable));
        }
        return ResponseEntity.ok(recruitRepository.findPageWithKeywordOnLatestOrder(keyword, pageable));
    }

    @PostMapping("/recruit")
    public ResponseEntity<Long> createRecruit(@AuthenticationPrincipal UserDetailsImpl userDetails, @Valid @RequestBody RecruitRequestDto recruitRequestDto) {
        return ResponseEntity.ok(recruitService.makeRecruit(userDetails.getMember().getId(), recruitRequestDto));
    }

    @PatchMapping("/recruit")
    public ResponseEntity<Void> updateRecruit(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestParam String recruitId, @Valid @RequestBody UpdateForm updateForm) {
        recruitService.updateRecruit(Long.valueOf(recruitId), userDetails.getMember().getId(), updateForm.getTitle(), updateForm.getContent());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/recruit")
    public ResponseEntity<Void> deleteRecruit(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestParam String recruitId) {
        recruitService.deleteRecruit(Long.valueOf(recruitId), userDetails.getMember().getId());
        return ResponseEntity.noContent().build();
    }
}
