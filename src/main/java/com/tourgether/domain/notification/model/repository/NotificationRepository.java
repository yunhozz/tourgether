package com.tourgether.domain.notification.model.repository;

import com.tourgether.domain.notification.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long>, NotificationRepositoryCustom {

    @Query("select n from Notification n join fetch n.receiver r where r.id = :receiverId order by n.createdDate desc")
    List<Notification> findWithReceiverId(@Param("receiverId") Long receiverId);

    @Query("select n from Notification n join fetch n.receiver r where r.id = :receiverId and n.isChecked = :check order by n.createdDate desc")
    List<Notification> findWithReceiverIdReadOrNot(@Param("receiverId") Long receiverId, @Param("check") boolean check);

    @Modifying(clearAutomatically = true)
    @Query("delete from Notification n where n.id = :id")
    void deleteNotification(@Param("id") Long id);

    @Modifying(clearAutomatically = true)
    @Query("delete from Notification n where n.id in :ids and n.isChecked = true")
    void deleteAlreadyChecked(@Param("ids") List<Long> ids);
}
