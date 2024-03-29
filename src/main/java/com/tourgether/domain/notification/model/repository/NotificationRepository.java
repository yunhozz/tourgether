package com.tourgether.domain.notification.model.repository;

import com.tourgether.domain.member.model.entity.Member;
import com.tourgether.domain.notification.model.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long>, NotificationRepositoryCustom {

    List<Notification> findByReceiverOrderByCreatedDateDesc(Member receiver);

    @Query("select n from Notification n join fetch n.receiver r where r.id = :receiverId and n.isChecked = :check order by n.createdDate desc")
    List<Notification> findWithReceiverIdReadOrNot(@Param("receiverId") Long receiverId, @Param("check") boolean check);

    @Modifying(clearAutomatically = true)
    @Query("delete from Notification n where n.id in :ids and n.isChecked = true")
    void deleteAlreadyChecked(@Param("ids") List<Long> ids);
}
