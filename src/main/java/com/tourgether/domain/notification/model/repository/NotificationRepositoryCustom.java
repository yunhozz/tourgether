package com.tourgether.domain.notification.model.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import static com.tourgether.dto.NotificationDto.*;

public interface NotificationRepositoryCustom {

    Page<NotificationQueryDto> findSimplePage(Long receiverId, Pageable pageable);
}
