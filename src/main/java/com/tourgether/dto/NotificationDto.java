package com.tourgether.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.tourgether.domain.member.model.entity.Member;
import com.tourgether.domain.notification.model.entity.Notification;
import com.tourgether.enums.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class NotificationDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class NotificationRequestDto {

        @NotBlank(message = "메세지를 입력해주세요.")
        private String message;

        @NotNull(message = "알림 타입을 입력해주세요.")
        private NotificationType type;

        private String redirectUrl;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class NotificationResponseDto {

        private Long id;
        private Long senderId;
        private Long receiverId;
        private String message;
        private NotificationType type;
        private String redirectUrl;
        private boolean isChecked;
        private LocalDateTime createdDate;
        private LocalDateTime lastModifiedDate;

        public NotificationResponseDto(Notification notification) {
            id = notification.getId();
            senderId = notification.getSender().getId();
            receiverId = notification.getReceiver().getId();
            message = notification.getMessage();
            type = notification.getType();
            redirectUrl = notification.getRedirectUrl();
            isChecked = notification.isChecked();
            createdDate = notification.getCreatedDate();
            lastModifiedDate = notification.getLastModifiedDate();
        }
    }

    @Getter
    @NoArgsConstructor
    public static class NotificationQueryDto {

        // notification
        private Long id;
        private String message;
        private NotificationType type;
        private String redirectUrl;
        private boolean isChecked;
        private LocalDateTime createdDate;

        // member
        private Long senderId;
        private Long receiverId;
        private String nickname;
        private String profileImgUrl;

        @QueryProjection
        public NotificationQueryDto(Long id, String message, NotificationType type, String redirectUrl, boolean isChecked, LocalDateTime createdDate, Long senderId, Long receiverId, String nickname, String profileImgUrl) {
            this.id = id;
            this.message = message;
            this.type = type;
            this.redirectUrl = redirectUrl;
            this.isChecked = isChecked;
            this.createdDate = createdDate;
            this.senderId = senderId;
            this.receiverId = receiverId;
            this.nickname = nickname;
            this.profileImgUrl = profileImgUrl;
        }
    }
}
