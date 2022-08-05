package com.tourgether.domain.notification.service.dto;

import com.tourgether.domain.notification.model.entity.Notification;
import com.tourgether.enums.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class NotificationResponseDto {

    private Long id;
    private Long receiverId;
    private String message;
    private NotificationType type;
    private String redirectUrl;
    private boolean isChecked;
    private LocalDateTime createdDate;

    public NotificationResponseDto(Notification notification) {
        id = notification.getId();
        receiverId = notification.getReceiver().getId();
        message = notification.getMessage();
        type = notification.getType();
        redirectUrl = notification.getRedirectUrl();
        isChecked = notification.isChecked();
        createdDate = notification.getCreatedDate();
    }
}
