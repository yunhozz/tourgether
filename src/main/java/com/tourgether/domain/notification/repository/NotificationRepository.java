package com.tourgether.domain.notification.repository;

import com.tourgether.domain.notification.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long>, EmitterRepository {
}
