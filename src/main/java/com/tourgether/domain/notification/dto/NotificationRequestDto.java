package com.tourgether.domain.notification.dto;

import com.tourgether.domain.member.Member;
import com.tourgether.domain.notification.Notification;
import com.tourgether.global.enums.NotificationType;
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

    @NotNull
    private Boolean isChecked;

    @Builder
    private NotificationRequestDto(String message, NotificationType type, Boolean isChecked) {
        this.message = message;
        this.type = type;
        this.isChecked = isChecked;
    }

    public Notification toEntity() {
        return Notification.builder()
                .receiver(receiver)
                .message(message)
                .type(type)
                .isChecked(isChecked)
                .build();
    }
}
