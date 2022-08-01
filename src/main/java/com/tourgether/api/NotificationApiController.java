package com.tourgether.api;

import com.tourgether.domain.notification.model.dto.NotificationQueryDto;
import com.tourgether.domain.notification.model.dto.NotificationRequestDto;
import com.tourgether.domain.notification.model.dto.NotificationResponseDto;
import com.tourgether.domain.notification.model.repository.NotificationRepository;
import com.tourgether.domain.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class NotificationApiController {

    private final NotificationService notificationService;
    private final NotificationRepository notificationRepository;
    private final AsyncTaskExecutor asyncTaskExecutor;

    @CrossOrigin // 모든 도메인, 모든 요청방식에 대해 허용
    @GetMapping("/sse")
    public SseEmitter handleSse() {
        SseEmitter emitter = new SseEmitter();
        asyncTaskExecutor.submit(() -> {
            try {
                emitter.send(HttpStatus.PROCESSING);
                Thread.sleep(1000);
                emitter.send(HttpStatus.OK);

            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        return emitter;
    }

    // 편의상 유저의 id 를 직접 받게 하였지만, 실제 적용시에는 access-token 을 적용
    @GetMapping(value = "/notification/connect/{receiverId}", produces = "text/event-stream")
    public ResponseEntity<SseEmitter> connect(@RequestHeader(value = "Last-Event-ID", required = false, defaultValue = "") String lastEventId,
                                              @PathVariable String receiverId) {
        return ResponseEntity.ok(notificationService.connect(Long.valueOf(receiverId), lastEventId));
    }

    @PostMapping("/notification/send")
    public ResponseEntity<NotificationResponseDto> send(@Valid NotificationRequestDto notificationRequestDto, @RequestParam String receiverId) {
        Long notificationId = notificationService.sendNotification(notificationRequestDto, Long.valueOf(receiverId));
        return ResponseEntity.ok(notificationService.findNotificationDto(notificationId));
    }

    @GetMapping("/notifications")
    public List<NotificationResponseDto> getNotifications() {
        return notificationService.findNotificationDtoList();
    }

    @GetMapping("/page/notifications")
    public Page<NotificationQueryDto> getNotificationsPage(@RequestParam("receiver") Long receiverId, @PageableDefault(size = 10) Pageable pageable) {
        return notificationRepository.findSimplePage(receiverId, pageable);
    }
}
