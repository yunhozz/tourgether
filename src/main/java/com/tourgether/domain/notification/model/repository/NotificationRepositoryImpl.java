package com.tourgether.domain.notification.model.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tourgether.domain.notification.model.dto.NotificationQueryDto;
import com.tourgether.domain.notification.model.dto.QNotificationQueryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.tourgether.domain.member.model.entity.QMember.*;
import static com.tourgether.domain.notification.model.QNotification.*;

@Repository
@RequiredArgsConstructor
public class NotificationRepositoryImpl implements NotificationRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<NotificationQueryDto> findSimplePage(Long receiverId, Pageable pageable) {
        List<NotificationQueryDto> notifications = queryFactory
                .select(new QNotificationQueryDto(
                        notification.id,
                        notification.message,
                        notification.type,
                        notification.redirectUrl,
                        notification.isChecked,
                        notification.createdDate,
                        member.id,
                        member.name,
                        member.nickname,
                        member.profileImgUrl
                ))
                .from(notification)
                .join(notification.receiver, member)
                .where(member.id.eq(receiverId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(notification.createdDate.desc())
                .fetch();

        Long count = queryFactory
                .select(notification.count())
                .from(notification)
                .fetchOne();

        return new PageImpl<>(notifications, pageable, count);
    }
}
