package com.tourgether.domain.notification.dto;

import com.tourgether.domain.member.model.entity.Member;
import com.tourgether.domain.notification.model.entity.Notification;
import com.tourgether.enums.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NotificationRequestDto {

    @NotNull
    private Long receiverId;

    @NotBlank
    private String message;

    @NotNull
    private NotificationType type;

    private String redirectUrl;

    @NotNull
    private Boolean isChecked;

    public Notification toEntity(Member receiver) {
        return Notification.builder()
                .receiver(receiver)
                .message(message)
                .type(type)
                .redirectUrl(redirectUrl)
                .isChecked(isChecked)
                .build();
    }
}
