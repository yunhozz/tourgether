package com.tourgether.api;

import com.tourgether.domain.notification.model.repository.NotificationRepository;
import com.tourgether.domain.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

import static com.tourgether.dto.NotificationDto.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class NotificationApiController {

    private final NotificationService notificationService;
    private final NotificationRepository notificationRepository;

    @GetMapping("/notification/read/{notificationId}")
    public NotificationResponseDto readNotification(@PathVariable String notificationId) {
        return notificationService.readNotification(Long.valueOf(notificationId));
    }

    @GetMapping("/notification/read/all")
    public List<NotificationResponseDto> readNotificationAll(@RequestParam String receiverId) {
        List<NotificationResponseDto> notifications = notificationService.findNotificationDtoListWithReceiverIdReadOrNot(Long.valueOf(receiverId), false);
        List<Long> ids = notifications.stream()
                .map(NotificationResponseDto::getId).toList();

        notificationService.readNotifications(ids);
        return notificationService.findNotificationDtoList();
    }

    @GetMapping("/notification/list")
    public List<NotificationResponseDto> getNotifications() {
        return notificationService.findNotificationDtoList();
    }

    @GetMapping("/notification/page/{receiverId}")
    public Page<NotificationQueryDto> getNotificationsPage(@PathVariable String receiverId, @PageableDefault(size = 10) Pageable pageable) {
        return notificationRepository.findSimplePage(Long.valueOf(receiverId), pageable);
    }

    // 편의상 유저의 id 를 직접 받게 하였지만, 실제 적용시에는 access-token 을 적용
    @GetMapping(value = "/notification/connect/{receiverId}", produces = "text/event-stream")
    public ResponseEntity<SseEmitter> connect(@RequestHeader(value = "Last-Event-ID", required = false, defaultValue = "") String lastEventId,
                                              @PathVariable String receiverId) {
        return ResponseEntity.ok(notificationService.connect(Long.valueOf(receiverId), lastEventId));
    }

    @PostMapping("/notification/send")
    public ResponseEntity<Long> send(@RequestBody NotificationRequestDto notificationRequestDto) {
        return ResponseEntity.ok(notificationService.sendNotification(notificationRequestDto));
    }
}
