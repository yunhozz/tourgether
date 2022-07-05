package com.tourgether.domain.notification.repository;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;

public interface EmitterRepository {

    SseEmitter save(String emitterId, SseEmitter emitter);
    void saveEventCache(String eventCacheId, Object event);
    Map<String, SseEmitter> findEmittersWithMemberId(String memberId);
    Map<String, Object> findEventCachesWithMemberId(String memberId);
    void deleteById(String id);
    void deleteEmittersWithMemberId(String memberId);
    void deleteEventCachesWithMemberId(String memberId);
}
