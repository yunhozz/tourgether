package com.tourgether.domain.notification.controller;

import com.tourgether.enums.NotificationType;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class NotificationForm {

    @NotBlank
    private String message;

    @NotNull
    private NotificationType type;

    private String redirectUrl;

    private boolean isChecked;
}
