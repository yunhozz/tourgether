package com.tourgether.domain.notification.dto;

import com.tourgether.domain.notification.Notification;
import com.tourgether.global.enums.NotificationType;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class NotificationResponseDto {

    private Long id;
    private Long receiverId;
    private String message;
    private NotificationType type;
    private Boolean isChecked;
    private LocalDateTime createdDate;

    public NotificationResponseDto(Notification notification) {
        id = notification.getId();
        receiverId = notification.getReceiver().getId();
        message = notification.getMessage();
        type = notification.getType();
        isChecked = notification.isChecked();
        createdDate = notification.getCreatedDate();
    }
}
