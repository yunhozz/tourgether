package com.tourgether.api;

import com.tourgether.domain.notification.model.repository.NotificationRepository;
import com.tourgether.domain.notification.service.NotificationService;
import com.tourgether.util.auth.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/notifications/{notificationId}")
    public ResponseEntity<NotificationResponseDto> readNotification(@PathVariable String notificationId) {
        return ResponseEntity.ok(notificationService.readNotification(Long.valueOf(notificationId)));
    }

    @GetMapping("/notifications")
    public ResponseEntity<List<NotificationResponseDto>> getNotifications(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(notificationService.findNotificationDtoListWithReceiverId(userDetails.getMember().getId()));
    }

    @GetMapping("/notifications/page")
    public ResponseEntity<Page<NotificationQueryDto>> getNotificationPage(@AuthenticationPrincipal UserDetailsImpl userDetails, @PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(notificationRepository.findSimplePage(userDetails.getMember().getId(), pageable));
    }

    @PostMapping(value = "/notifications/connect", produces = "text/event-stream")
    public ResponseEntity<Void> connect(@RequestHeader(value = "Last-Event-ID", required = false, defaultValue = "") String lastEventId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        notificationService.connect(userDetails.getMember().getId(), lastEventId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/notifications")
    public ResponseEntity<Long> send(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestParam String receiverId, @RequestBody NotificationRequestDto notificationRequestDto) {
        return ResponseEntity.ok(notificationService.sendNotification(userDetails.getMember().getId(), Long.valueOf(receiverId), notificationRequestDto));
    }

    @PatchMapping("/notifications")
    public ResponseEntity<Void> readNotificationAll(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<NotificationResponseDto> notifications = notificationService.findNotificationDtoListWithReceiverIdReadOrNot(userDetails.getMember().getId(), false);
        List<Long> ids = notifications.stream().map(NotificationResponseDto::getId).toList();
        notificationService.readNotifications(ids);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/notifications")
    public ResponseEntity<Void> deleteNotification(@RequestParam String notificationId) {
        notificationRepository.deleteNotification(Long.valueOf(notificationId));
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/notifications/checked")
    public ResponseEntity<Void> deleteNotificationChecked(@RequestParam List<Long> notificationIds) {
        notificationRepository.deleteAlreadyChecked(notificationIds);
        return ResponseEntity.noContent().build();
    }
}
