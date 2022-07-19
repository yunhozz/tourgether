package com.tourgether.api;

import com.tourgether.domain.notification.model.dto.NotificationQueryDto;
import com.tourgether.domain.notification.model.dto.NotificationResponseDto;
import com.tourgether.domain.notification.model.repository.NotificationRepository;
import com.tourgether.domain.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class NotificationApiController {

    private final NotificationService notificationService;
    private final NotificationRepository notificationRepository;

    @GetMapping(value = "/connect/{receiverId}", produces = "text/event-stream")
    public SseEmitter connect(@RequestHeader(value = "Last-Event-ID", required = false, defaultValue = "") String lastEventId,
                              @PathVariable("receiverId") Long receiverId) {
        return notificationService.connect(receiverId, lastEventId);
    }

    @GetMapping("/notifications")
    public List<NotificationResponseDto> getNotifications() {
        return notificationService.findNotificationDtoList();
    }

    @GetMapping("/page/notifications")
    public Page<NotificationQueryDto> getNotificationsPage(@RequestParam("receiverId") Long receiverId, @PageableDefault(size = 10) Pageable pageable) {
        return notificationRepository.findSimplePage(receiverId, pageable);
    }
}
