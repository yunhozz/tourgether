package com.tourgether.api;

import com.tourgether.api.dto.Response;
import com.tourgether.domain.notification.model.repository.NotificationRepository;
import com.tourgether.domain.notification.service.NotificationService;
import com.tourgether.util.auth.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.tourgether.dto.NotificationDto.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class NotificationApiController {

    private final NotificationService notificationService;
    private final NotificationRepository notificationRepository;

    @GetMapping("/notification/read/{notificationId}")
    public Response readNotification(@PathVariable String notificationId) {
        return Response.success(notificationService.readNotification(Long.valueOf(notificationId)));
    }

    @GetMapping("/notification/read/all")
    public Response readNotificationAll(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<NotificationResponseDto> notifications = notificationService.findNotificationDtoListWithReceiverIdReadOrNot(userDetails.getMember().getId(), false);
        List<Long> ids = notifications.stream().map(NotificationResponseDto::getId).toList();
        notificationService.readNotifications(ids);

        return Response.success(notificationService.findNotificationDtoList());
    }

    @GetMapping("/notification/list")
    public Response getNotifications(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return Response.success(notificationService.findNotificationDtoListWithReceiverId(userDetails.getMember().getId()));
    }

    @GetMapping("/notification/receiver-list")
    public Response getNotificationsPage(@AuthenticationPrincipal UserDetailsImpl userDetails, @PageableDefault(size = 10) Pageable pageable) {
        return Response.success(notificationRepository.findSimplePage(userDetails.getMember().getId(), pageable));
    }

    // 편의상 유저의 id 를 직접 받게 하였지만, 실제 적용시에는 access-token 을 적용
    @GetMapping(value = "/notification/connect", produces = "text/event-stream")
    public Response connect(@RequestHeader(value = "Last-Event-ID", required = false, defaultValue = "") String lastEventId,
                            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        notificationService.connect(userDetails.getMember().getId(), lastEventId);
        return Response.success();
    }

    @PostMapping("/notification/send")
    public Response send(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestParam String receiverId, @RequestBody NotificationRequestDto notificationRequestDto) {
        Long notificationId = notificationService.sendNotification(userDetails.getMember().getId(), Long.valueOf(receiverId), notificationRequestDto);
        return Response.success(notificationService.findNotificationDto(notificationId));
    }

    @DeleteMapping("/notification/delete")
    public Response deleteNotification(@RequestParam String notificationId) {
        notificationRepository.deleteNotification(Long.valueOf(notificationId));
        return Response.success();
    }

    @DeleteMapping("/notification/delete-checked")
    public Response deleteNotificationChecked(@RequestParam List<Long> notificationIds) {
        notificationRepository.deleteAlreadyChecked(notificationIds);
        return Response.success();
    }
}
