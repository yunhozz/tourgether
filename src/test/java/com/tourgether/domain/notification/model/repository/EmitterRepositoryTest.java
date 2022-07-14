package com.tourgether.domain.notification.model.repository;

import com.tourgether.domain.notification.model.Notification;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class EmitterRepositoryTest {

    @Autowired
    private EmitterRepositoryImpl emitterRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    private final static Long DEFAULT_TIMEOUT = 60L * 60 * 1000;

    @Test
    void save() throws Exception {
        //given
        String memberId = "USER_ID";
        String emitterId = memberId + System.currentTimeMillis();
        SseEmitter emitter = new SseEmitter(DEFAULT_TIMEOUT);

        //then
        assertDoesNotThrow(() -> emitterRepository.save(emitterId, emitter));
    }

    @Test
    void saveEventCache() throws Exception {
        //given
        String memberId = "USER_ID";
        String eventCacheId = memberId + System.currentTimeMillis();
        Notification notification = Notification.builder()
                .receiver(null)
                .message("test")
                .build();

        notificationRepository.save(notification);

        //then
        assertDoesNotThrow(() -> emitterRepository.saveEventCache(eventCacheId, notification));
    }

    @Test
    void findEmittersWithMemberId() throws Exception {
        //given
        String memberId = "USER_ID";
        for (int i = 0; i < 5; i++) {
            String emitterId = memberId + System.currentTimeMillis();
            SseEmitter emitter = new SseEmitter(DEFAULT_TIMEOUT);
            emitterRepository.save(emitterId, emitter);
            Thread.sleep(10);
        }

        //when
        Map<String, SseEmitter> result = emitterRepository.findEmittersWithMemberId("USER_ID");

        //then
        assertThat(result.size()).isEqualTo(5);
    }

    @Test
    void findEventCachesWithMemberId() throws Exception {
        //given
        String memberId = "USER_ID";
        for (int i = 0; i < 5; i++) {
            String eventCacheId = memberId + System.currentTimeMillis();
            Notification notification = Notification.builder()
                    .receiver(null)
                    .message("test" + i)
                    .build();

            notificationRepository.save(notification);
            emitterRepository.saveEventCache(eventCacheId, notification);
            Thread.sleep(10);
        }

        //when
        Map<String, Object> result = emitterRepository.findEventCachesWithMemberId("USER_ID");

        //then
        assertThat(result.size()).isEqualTo(5);
        assertThat(result.values()).extracting("message").contains("test0", "test1", "test2", "test3", "test4");
    }

    @Test
    void deleteById() throws Exception {
        //given
        String memberId = "USER_ID";
        String deleteId = null;

        for (int i = 0; i < 5; i++) {
            String emitterId = memberId + System.currentTimeMillis();
            if (i == 2) {
                deleteId = emitterId;
            }
            SseEmitter emitter = new SseEmitter(DEFAULT_TIMEOUT);
            emitterRepository.save(emitterId, emitter);
            Thread.sleep(10);
        }

        //when
        emitterRepository.deleteById(deleteId);
        Map<String, SseEmitter> result = emitterRepository.findEmittersWithMemberId(memberId);

        //then
        assertThat(result.size()).isEqualTo(4);
        assertThat(result.get(deleteId)).isNull();
    }

    @Test
    void deleteEmittersWithMemberId() throws Exception {
        //given
        String memberId = "USER_ID";
        for (int i = 0; i < 5; i++) {
            String emitterId = memberId + System.currentTimeMillis();
            SseEmitter emitter = new SseEmitter(DEFAULT_TIMEOUT);
            emitterRepository.save(emitterId, emitter);
            Thread.sleep(10);
        }

        //when
        emitterRepository.deleteEmittersWithMemberId(memberId);
        Map<String, SseEmitter> result = emitterRepository.findEmittersWithMemberId(memberId);

        //then
        assertThat(result).isEmpty();
    }

    @Test
    void deleteEventCachesWithMemberId() throws Exception {
        //given
        String memberId = "USER_ID";
        for (int i = 0; i < 5; i++) {
            String eventCacheId = memberId + System.currentTimeMillis();
            Notification notification = Notification.builder()
                    .receiver(null)
                    .message("test" + i)
                    .build();

            notificationRepository.save(notification);
            emitterRepository.saveEventCache(eventCacheId, notification);
            Thread.sleep(10);
        }

        //when
        emitterRepository.deleteEventCachesWithMemberId(memberId);
        Map<String, Object> result = emitterRepository.findEventCachesWithMemberId(memberId);

        //then
        assertThat(result).isEmpty();
    }
}