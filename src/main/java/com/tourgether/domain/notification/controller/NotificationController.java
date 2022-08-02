package com.tourgether.domain.notification.controller;

import com.tourgether.domain.notification.model.dto.NotificationResponseDto;
import com.tourgether.domain.notification.model.repository.NotificationRepository;
import com.tourgether.domain.notification.service.NotificationService;
import com.tourgether.dto.MemberSessionResponseDto;
import com.tourgether.ui.login.LoginMember;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;
    private final NotificationRepository notificationRepository;

    @GetMapping("/notifications")
    public String getNotifications(@LoginMember MemberSessionResponseDto loginMember, Model model) {
        if (loginMember == null) {
            return "redirect:/member/login";
        }
        List<NotificationResponseDto> notifications = notificationService.findNotificationDtoListWithReceiverIdReadOrNot(loginMember.getId(), false);
        model.addAttribute("notifications", notifications);

        return "notification/list";
    }

    @GetMapping("/notifications/old")
    public String getOldNotifications(@LoginMember MemberSessionResponseDto loginMember, Model model) {
        if (loginMember == null) {
            return "redirect:/member/login";
        }
        List<NotificationResponseDto> notifications = notificationService.findNotificationDtoListWithReceiverIdReadOrNot(loginMember.getId(), true);
        model.addAttribute("notifications", notifications);

        return "notification/list";
    }

    @GetMapping("/notifications/{notificationId}")
    public String readNotification(@LoginMember MemberSessionResponseDto loginMember, @PathVariable String notificationId, Model model) {
        if (loginMember == null) {
            return "redirect:/member/login";
        }
        NotificationResponseDto notification = notificationService.readNotification(Long.valueOf(notificationId)); // 읽기 처리
        if (!notification.getRedirectUrl().isEmpty()) {
            model.addAttribute("isUrl", true);
            return "redirect:" + notification.getRedirectUrl();
        }
        return "notification/detail";
    }

    @GetMapping("/notifications/{notificationId}/delete")
    public String deleteNotification(@PathVariable String notificationId) {
        notificationRepository.deleteNotification(Long.valueOf(notificationId));
        return "redirect:/notifications";
    }

    @GetMapping("/notifications/delete-all")
    public String deleteCheckedNotifications(@RequestParam List<Long> ids) {
        notificationRepository.deleteAlreadyChecked(ids);
        return "redirect:/notifications";
    }
}
