package com.tourgether.api;

import com.tourgether.api.dto.Response;
import com.tourgether.domain.recruit.model.repository.RecruitRepository;
import com.tourgether.domain.recruit.service.RecruitService;
import com.tourgether.util.auth.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.tourgether.dto.RecruitDto.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class RecruitApiController {

    private final RecruitService recruitService;
    private final RecruitRepository recruitRepository;

    @GetMapping("/recruit/{recruitId}")
    public Response getRecruit(@PathVariable String recruitId) {
        return Response.success(recruitService.findRecruitDto(Long.valueOf(recruitId)));
    }

    @GetMapping("/recruit/list")
    public Response getRecruits() {
        return Response.success(recruitService.findRecruitDtoList());
    }

    @GetMapping("/page/recruits-created")
    public Response getPageWithCreated(Pageable pageable) {
        return Response.success(recruitRepository.findPageWithCreated(pageable));
    }

    @GetMapping("/page/recruits-modified")
    public Response getPageWithModified(Pageable pageable) {
        return Response.success(recruitRepository.findPageWithModified(pageable));
    }

    @GetMapping("/page/recruits-popularity")
    public Response getPageWithPopularity(Pageable pageable) {
        return Response.success(recruitRepository.findPageWithPopularity(pageable));
    }

    @GetMapping("/page/recruits-created/search")
    public Response getPageWithKeyword(@RequestParam(required = false) String keyword, Pageable pageable, HttpServletResponse response) {
        if (keyword.isEmpty()) {
            try {
                handleRedirectPage(response);
            } catch (IOException e) {
                return Response.failure(404, e.getMessage());
            }
        }
        return Response.success(recruitRepository.findPageWithKeyword(keyword, pageable));
    }

    @GetMapping("/page/recruits-modified/search")
    public Response getPageWithKeywordOnLatestOrder(@RequestParam(required = false) String keyword, Pageable pageable, HttpServletResponse response) {
        if (keyword.isEmpty()) {
            try {
                handleRedirectPage(response);
            } catch (IOException e) {
                return Response.failure(404, e.getMessage());
            }
        }
        return Response.success(recruitRepository.findPageWithKeywordOnLatestOrder(keyword, pageable));
    }

    @GetMapping("/page/recruits-accuracy/search")
    public Response getPageWithKeywordOnAccuracyOrder(@RequestParam(required = false) String keyword, Pageable pageable, HttpServletResponse response) {
        if (keyword.isEmpty()) {
            try {
                handleRedirectPage(response);
            } catch (IOException e) {
                return Response.failure(404, e.getMessage());
            }
        }
        return Response.success(recruitRepository.findPageWithKeywordOnAccuracyOrder(keyword, pageable));
    }

    @PostMapping("/recruit/create")
    public Response createRecruit(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody RecruitRequestDto recruitRequestDto) {
        Long recruitId = recruitService.makeRecruit(userDetails.getMember().getId(), recruitRequestDto);
        return Response.success(recruitService.findRecruitDto(recruitId));
    }

    @PatchMapping("/recruit/{recruitId}/update")
    public Response updateRecruit(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable String recruitId, @RequestBody UpdateForm updateForm) {
        RecruitResponseDto recruit = recruitService.findRecruitDto(Long.valueOf(recruitId));
        if (recruit.getWriterId() != userDetails.getMember().getId()) {
            return Response.failure(404, "수정 권한이 없습니다.");
        }
        recruitService.updateRecruit(Long.valueOf(recruitId), recruit.getWriterId(), updateForm.getTitle(), updateForm.getContent());
        return Response.success(recruitService.findRecruitDto(Long.valueOf(recruitId)));
    }

    @GetMapping("/recruit/re-direct")
    private void handleRedirectPage(HttpServletResponse response) throws IOException {
        response.sendRedirect("/api/page/recruits-created");
    }
}
