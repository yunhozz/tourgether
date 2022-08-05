package com.tourgether.domain.notification.service.dto;

import com.tourgether.domain.member.model.entity.Member;
import com.tourgether.domain.notification.model.entity.Notification;
import com.tourgether.enums.NotificationType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class NotificationRequestDto {

    @NotNull
    private Member receiver;

    @NotBlank
    private String message;

    @NotNull
    private NotificationType type;

    private String redirectUrl;

    @NotNull
    private Boolean isChecked;

    @Builder
    private NotificationRequestDto(Member receiver, String message, NotificationType type, String redirectUrl, Boolean isChecked) {
        this.receiver = receiver;
        this.message = message;
        this.type = type;
        this.redirectUrl = redirectUrl;
        this.isChecked = isChecked;
    }

    public Notification toEntity() {
        return Notification.builder()
                .receiver(receiver)
                .message(message)
                .type(type)
                .redirectUrl(redirectUrl)
                .isChecked(isChecked)
                .build();
    }
}
