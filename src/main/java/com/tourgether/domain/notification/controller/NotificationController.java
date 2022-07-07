package com.tourgether.domain.notification.controller;

import com.tourgether.domain.notification.dto.NotificationResponseDto;
import com.tourgether.domain.notification.service.NotificationService;
import com.tourgether.global.dto.MemberSessionResponseDto;
import com.tourgether.global.ui.LoginMember;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping
    public String readNotifications(@LoginMember MemberSessionResponseDto loginMember, Model model) {
        if (loginMember == null) {
            return "redirect:/member/sign-in";
        }
        List<NotificationResponseDto> notifications = notificationService.findNotificationDtoListWithReceiverIdReadOrNot(loginMember.getId(), false);
        model.addAttribute("notifications", notifications);
        model.addAttribute("isNew", true);

        notificationService.readNotifications(loginMember.getId()); // 읽기 처리
        return "notification/list";
    }

    @GetMapping("/old")
    public String readOldNotifications(@LoginMember MemberSessionResponseDto loginMember, Model model) {
        if (loginMember == null) {
            return "redirect:/member/sign-in";
        }
        List<NotificationResponseDto> notifications = notificationService.findNotificationDtoListWithReceiverIdReadOrNot(loginMember.getId(), true);
        model.addAttribute("notifications", notifications);
        model.addAttribute("isNew", false);

        return "notification/list";
    }

    @GetMapping("/delete")
    public String deleteNotifications(@LoginMember MemberSessionResponseDto loginMember) {
        if (loginMember == null) {
            return "redirect:/member/sign-in";
        }
        notificationService.deleteNotificationsAlreadyChecked(loginMember.getId());
        return "redirect:/notifications";
    }
}
