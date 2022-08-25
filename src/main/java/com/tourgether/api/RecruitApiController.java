package com.tourgether.api;

import com.tourgether.domain.recruit.model.repository.RecruitRepository;
import com.tourgether.domain.recruit.service.RecruitService;
import com.tourgether.util.auth.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.tourgether.dto.RecruitDto.*;

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

    @GetMapping("/recruit/list")
    public ResponseEntity<List<RecruitResponseDto>> getRecruits() {
        return ResponseEntity.ok(recruitService.findRecruitDtoList());
    }

    @GetMapping("/page/recruits-created")
    public ResponseEntity<Page<RecruitQueryDto>> getPageWithCreated(Pageable pageable) {
        return ResponseEntity.ok(recruitRepository.findPageWithCreated(pageable));
    }

    @GetMapping("/page/recruits-modified")
    public ResponseEntity<Page<RecruitQueryDto>> getPageWithModified(Pageable pageable) {
        return ResponseEntity.ok(recruitRepository.findPageWithModified(pageable));
    }

    @GetMapping("/page/recruits-popularity")
    public ResponseEntity<Page<RecruitQueryDto>> getPageWithPopularity(Pageable pageable) {
        return ResponseEntity.ok(recruitRepository.findPageWithPopularity(pageable));
    }

    @GetMapping("/page/recruits-created/search")
    public ResponseEntity<?> getPageWithKeyword(@RequestParam(required = false) String keyword, Pageable pageable, HttpServletResponse response) {
        if (keyword.isEmpty()) {
            try {
                handleRedirectPage(response);
            } catch (IOException e) {
                return ResponseEntity.badRequest().build();
            }
        }
        return ResponseEntity.ok(recruitRepository.findPageWithKeyword(keyword, pageable));
    }

    @GetMapping("/page/recruits-modified/search")
    public ResponseEntity<?> getPageWithKeywordOnLatestOrder(@RequestParam(required = false) String keyword, Pageable pageable, HttpServletResponse response) {
        if (keyword.isEmpty()) {
            try {
                handleRedirectPage(response);
            } catch (IOException e) {
                return ResponseEntity.badRequest().build();
            }
        }
        return ResponseEntity.ok(recruitRepository.findPageWithKeywordOnLatestOrder(keyword, pageable));
    }

    @GetMapping("/page/recruits-accuracy/search")
    public ResponseEntity<?> getPageWithKeywordOnAccuracyOrder(@RequestParam(required = false) String keyword, Pageable pageable, HttpServletResponse response) {
        if (keyword.isEmpty()) {
            try {
                handleRedirectPage(response);
            } catch (IOException e) {
                return ResponseEntity.badRequest().build();
            }
        }
        return ResponseEntity.ok(recruitRepository.findPageWithKeywordOnAccuracyOrder(keyword, pageable));
    }

    @PostMapping("/recruit/create")
    public ResponseEntity<Long> createRecruit(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody RecruitRequestDto recruitRequestDto) {
        return ResponseEntity.ok(recruitService.makeRecruit(userDetails.getMember().getId(), recruitRequestDto));
    }

    @PatchMapping("/recruit/{recruitId}/update")
    public ResponseEntity<?> updateRecruit(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable String recruitId, @RequestBody UpdateForm updateForm) {
        RecruitResponseDto recruit = recruitService.findRecruitDto(Long.valueOf(recruitId));
        if (!recruit.getWriterId().equals(userDetails.getMember().getId())) {
            return new ResponseEntity<>("수정 권한이 없습니다.", HttpStatus.BAD_REQUEST);
        }
        recruitService.updateRecruit(Long.valueOf(recruitId), recruit.getWriterId(), updateForm.getTitle(), updateForm.getContent());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/recruit/re-direct")
    private void handleRedirectPage(HttpServletResponse response) throws IOException {
        response.sendRedirect("/api/page/recruits-created");
    }
}
