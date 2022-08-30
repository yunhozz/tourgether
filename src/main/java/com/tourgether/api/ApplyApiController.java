package com.tourgether.api;

import com.tourgether.domain.recruit.service.ApplyService;
import com.tourgether.dto.ErrorResponseDto;
import com.tourgether.enums.ErrorCode;
import com.tourgether.util.auth.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.tourgether.dto.ApplyDto.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ApplyApiController {

    private final ApplyService applyService;

    @GetMapping("/apply/{applyId}")
    public ResponseEntity<ApplyResponseDto> getApply(@RequestParam String applyId) {
        return ResponseEntity.ok(applyService.findApplyDto(Long.valueOf(applyId)));
    }

    @GetMapping("/apply")
    public ResponseEntity<List<ApplyResponseDto>> getApplyList(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(applyService.findApplyDtoListByUserId(userDetails.getMember().getId()));
    }

    @PostMapping("/apply")
    public ResponseEntity<Long> makeApply(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestParam String recruitId, @Valid @RequestBody ApplyRequestDto applyRequestDto) {
        return ResponseEntity.ok(applyService.makeApply(applyRequestDto, userDetails.getMember().getId(), Long.valueOf(recruitId)));
    }

    @PostMapping("/apply/response")
    public ResponseEntity<Object> responseToApply(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestParam String applyId, @RequestParam(required = false) Boolean flag) {
        if (flag == null) {
            return ResponseEntity.badRequest().body(new ErrorResponseDto(ErrorCode.ACCEPTANCE_NOT_INSERTED));
        }
        return ResponseEntity.ok().build();
    }
}
