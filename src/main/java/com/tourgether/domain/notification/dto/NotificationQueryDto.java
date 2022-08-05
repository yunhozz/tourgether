package com.tourgether.domain.notification.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.tourgether.enums.NotificationType;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class NotificationQueryDto {

    // notification
    private Long id;
    private String message;
    private NotificationType type;
    private String redirectUrl;
    private boolean isChecked;
    private LocalDateTime createdDate;

    // member
    private Long receiverId;
    private String name;
    private String nickname;
    private String profileImgUrl;

    @QueryProjection
    public NotificationQueryDto(Long id, String message, NotificationType type, String redirectUrl, boolean isChecked, LocalDateTime createdDate, Long receiverId, String name, String nickname, String profileImgUrl) {
        this.id = id;
        this.message = message;
        this.type = type;
        this.redirectUrl = redirectUrl;
        this.isChecked = isChecked;
        this.createdDate = createdDate;
        this.receiverId = receiverId;
        this.name = name;
        this.nickname = nickname;
        this.profileImgUrl = profileImgUrl;
    }
}
