package com.tourgether.domain.notification.model.repository;

import com.tourgether.domain.notification.dto.NotificationQueryDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NotificationRepositoryCustom {

    Page<NotificationQueryDto> findSimplePage(Long receiverId, Pageable pageable);
}
