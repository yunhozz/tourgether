package com.tourgether.domain.notification.repository;

import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class EmitterRepositoryImpl implements EmitterRepository {

    Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();
    Map<String, Object> eventCaches = new ConcurrentHashMap<>();

    @Override
    public SseEmitter save(String emitterId, SseEmitter emitter) {
        emitters.put(emitterId, emitter);
        return emitter;
    }

    @Override
    public void saveEventCache(String eventCacheId, Object event) {
        eventCaches.put(eventCacheId, event);
    }

    @Override
    public Map<String, SseEmitter> findEmittersWithMemberId(String memberId) {
        return emitters.entrySet().stream()
                .filter(e -> e.getKey().startsWith(memberId))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @Override
    public Map<String, Object> findEventCachesWithMemberId(String memberId) {
        return eventCaches.entrySet().stream()
                .filter(e -> e.getKey().startsWith(memberId))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @Override
    public void deleteById(String id) {
        emitters.remove(id);
    }

    @Override
    public void deleteEmittersWithMemberId(String memberId) {
        emitters.forEach(
                (key, emitter) -> {
                    if (key.startsWith(memberId)) {
                        emitters.remove(key);
                    }
                }
        );
    }

    @Override
    public void deleteEventCachesWithMemberId(String memberId) {
        eventCaches.forEach(
                (key, object) -> {
                    if (key.startsWith(memberId)) {
                        eventCaches.remove(key);
                    }
                }
        );
    }
}
