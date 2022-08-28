package com.tourgether.api;

import com.tourgether.domain.recruit.service.ApplyService;
import com.tourgether.util.auth.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.tourgether.dto.ApplyDto.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ApplyApiController {

    private final ApplyService applyService;

    @GetMapping("/applies/{applyId}")
    public ResponseEntity<ApplyResponseDto> getApply(@RequestParam String applyId) {
        return ResponseEntity.ok(applyService.findApplyDto(Long.valueOf(applyId)));
    }

    @GetMapping("/applies")
    public ResponseEntity<List<ApplyResponseDto>> getApplyList(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(applyService.findApplyDtoListByUserId(userDetails.getMember().getId()));
    }

    @PostMapping("/applies")
    public ResponseEntity<Long> makeApply(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestParam String recruitId, @RequestBody ApplyRequestDto applyRequestDto) {
        return ResponseEntity.ok(applyService.makeApply(applyRequestDto, userDetails.getMember().getId(), Long.valueOf(recruitId)));
    }

    @PostMapping("/applies/response")
    public ResponseEntity<Void> responseToApply(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestParam String applyId, @RequestParam Boolean flag) {
        return ResponseEntity.ok().build();
    }
}
