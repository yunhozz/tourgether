package com.tourgether.domain.notification.model.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tourgether.domain.member.model.entity.QMember;
import com.tourgether.dto.QNotificationDto_NotificationQueryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.tourgether.domain.notification.model.entity.QNotification.*;
import static com.tourgether.dto.NotificationDto.*;

@Repository
@RequiredArgsConstructor
public class NotificationRepositoryImpl implements NotificationRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<NotificationQueryDto> findSimplePage(Long receiverId, Pageable pageable) {
        QMember sender = new QMember("sender");
        QMember receiver = new QMember("receiver");

        List<NotificationQueryDto> notifications = queryFactory
                .select(new QNotificationDto_NotificationQueryDto(
                        notification.id,
                        notification.message,
                        notification.type,
                        notification.redirectUrl,
                        notification.isChecked,
                        notification.createdDate,
                        sender.id,
                        receiver.id,
                        sender.nickname,
                        sender.profileImgUrl
                ))
                .from(notification)
                .join(notification.sender, sender)
                .join(notification.receiver, receiver)
                .where(receiver.id.eq(receiverId))
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
