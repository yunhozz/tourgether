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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;
    private final NotificationRepository notificationRepository;

    @GetMapping
    public String getNotifications(@LoginMember MemberSessionResponseDto loginMember, Model model) {
        if (loginMember == null) {
            return "redirect:/member/sign-in";
        }
        List<NotificationResponseDto> notifications = notificationService.findNotificationDtoListWithReceiverIdReadOrNot(loginMember.getId(), false);
        model.addAttribute("notifications", notifications);
        model.addAttribute("isNew", true);

        return "notification/list";
    }

    @GetMapping("/old")
    public String getOldNotifications(@LoginMember MemberSessionResponseDto loginMember, Model model) {
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
