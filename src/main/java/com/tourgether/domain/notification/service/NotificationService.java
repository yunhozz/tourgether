package com.tourgether.domain.notification.service;

import com.tourgether.domain.member.Member;
import com.tourgether.domain.member.repository.MemberRepository;
import com.tourgether.domain.notification.Notification;
import com.tourgether.domain.notification.dto.NotificationRequestDto;
import com.tourgether.domain.notification.dto.NotificationResponseDto;
import com.tourgether.domain.notification.repository.EmitterRepository;
import com.tourgether.domain.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final EmitterRepository emitterRepository;
    private final MemberRepository memberRepository;

    private static final Long DEFAULT_TIMEOUT = 60L * 60 * 1000;

    // 클라이언트와 서버 sse 연결
    public SseEmitter connect(Long receiverId, String lastEventId) {
        String emitterId = receiverId + "_" + System.currentTimeMillis();
        SseEmitter emitter = emitterRepository.save(emitterId, new SseEmitter(DEFAULT_TIMEOUT));

        // sse emitter 의 시간 초과 및 네트워크 오류를 포함한 모든 이유로 비동기 요청이 정상 동작하지 않을 때 저장해둔 SseEmitter 를 삭제
        emitter.onCompletion(() -> complete(emitterId));
        emitter.onTimeout(() -> timeout(emitterId));
        sendToClient(emitter, emitterId, "EventStream Created. [receiverId=" + receiverId + "]"); // 더미 데이터 전송 (503 에러 방지)

        // lastEventId 가 발생한 시점 이후로 발생한 이벤트 캐시를 클라이언트에 전송
        if (!lastEventId.isEmpty()) {
            Map<String, Object> events = emitterRepository.findEventCachesWithMemberId(String.valueOf(receiverId));
            events.entrySet().stream()
                    .filter(e -> e.getKey().compareTo(lastEventId) > 0)
                    .forEach(e -> sendToClient(emitter, e.getKey(), e.getValue()));
        }
        return emitter;
    }

    // 알림 보내기
    public Long sendNotification(NotificationRequestDto notificationRequestDto, Long receiverId) {
        Member receiver = memberRepository.findById(receiverId)
                .orElseThrow(() -> new IllegalStateException("This member is null : " + receiverId));
        notificationRequestDto.setReceiver(receiver);
        Notification notification = notificationRequestDto.toEntity();
        notificationRepository.save(notification);

        Map<String, SseEmitter> emitters = emitterRepository.findEmittersWithMemberId(String.valueOf(receiverId));
        emitters.forEach(
                (key, emitter) -> {
                    emitterRepository.saveEventCache(key, notification);
                    sendToClient(emitter, String.valueOf(receiverId), new NotificationResponseDto(notification));
                }
        );
        return notification.getId();
    }

    // 전체 알림 읽기 처리
    public void readNotifications(Long receiverId) {
        List<Notification> notifications = notificationRepository.findByReceiverId(receiverId);
        notifications.forEach(Notification::check);
    }

    // 읽은 알림 삭제
    public void deleteNotificationsAlreadyChecked(Long receiverId) {
        notificationRepository.deleteAlreadyChecked(receiverId);
    }

    @Transactional(readOnly = true)
    public NotificationResponseDto findNotificationDto(Long id) {
        return new NotificationResponseDto(findNotification(id));
    }

    @Transactional(readOnly = true)
    public List<NotificationResponseDto> findNotificationDtoList() {
        return notificationRepository.findAll().stream()
                .map(NotificationResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<NotificationResponseDto> findNotificationDtoListWithReceiverIdReadOrNot(Long receiverId, boolean check) {
        return notificationRepository.findWithReceiverIdReadOrNot(receiverId, check).stream()
                .map(NotificationResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    private Notification findNotification(Long id) {
        return notificationRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("This notification is null : " + id));
    }

    private void sendToClient(SseEmitter emitter, String id, Object data) {
        try {
            emitter.send(
                    SseEmitter.event()
                            .id(id)
                            .name("sse")
                            .data(data)
            );
        } catch (Exception e) {
            emitterRepository.deleteById(id);
            System.out.println("Connection Error!");
        }
    }

    private void complete(String emitterId) {
        System.out.println("emitter completed");
        emitterRepository.deleteById(emitterId);
    }

    private void timeout(String emitterId) {
        System.out.println("emitter timeout");
        emitterRepository.deleteById(emitterId);
    }
}