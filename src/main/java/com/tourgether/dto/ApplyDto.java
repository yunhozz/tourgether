package com.tourgether.dto;

import com.tourgether.domain.recruit.model.entity.Apply;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

public class ApplyDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ApplyRequestDto {

        @NotBlank(message = "지원 동기를 입력해주세요.")
        private String message;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ApplyResponseDto {

        private Long id;
        private Long userId;
        private Long recruitId;
        private String message;
        private Integer status;
        private LocalDateTime createdDate;
        private LocalDateTime lastModifiedDate;

        public ApplyResponseDto(Apply apply) {
            id = apply.getId();
            userId = apply.getMember().getId();
            recruitId = apply.getRecruit().getId();
            message = apply.getMessage();
            status = apply.getStatus();
            createdDate = apply.getCreatedDate();
            lastModifiedDate = apply.getLastModifiedDate();
        }
    }
}
