package com.tourgether.domain.notification.model.dto;

import com.tourgether.domain.member.model.entity.Member;
import com.tourgether.domain.notification.model.entity.Notification;
import com.tourgether.enums.NotificationType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
public class NotificationRequestDto {

    @Setter
    private Member receiver;

    @NotBlank
    private String message;

    @NotNull
    private NotificationType type;

    private String redirectUrl;

    @NotNull
    private Boolean isChecked;

    @Builder
    private NotificationRequestDto(String message, NotificationType type, String redirectUrl, Boolean isChecked) {
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
