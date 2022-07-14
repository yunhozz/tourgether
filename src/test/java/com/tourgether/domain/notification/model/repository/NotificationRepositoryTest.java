package com.tourgether.domain.notification.model.repository;

import com.tourgether.domain.member.model.Member;
import com.tourgether.domain.member.model.repository.MemberRepository;
import com.tourgether.domain.notification.model.dto.NotificationQueryDto;
import com.tourgether.domain.notification.model.Notification;
import com.tourgether.enums.NotificationType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class NotificationRepositoryTest {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void findByReceiverId() throws Exception {
        //given
        Member receiver = createMember("email", "pw");
        memberRepository.save(receiver);

        Notification notification1 = createNotification(receiver, "message1", NotificationType.OFFER, false);
        Notification notification2 = createNotification(receiver, "message2", NotificationType.BOOKMARK, false);
        Notification notification3 = createNotification(receiver, "message3", NotificationType.CHAT, false);
        notificationRepository.save(notification1);
        notificationRepository.save(notification2);
        notificationRepository.save(notification3);

        //when
        List<Notification> result = notificationRepository.findWithReceiverId(receiver.getId());

        //then
        assertThat(result.size()).isEqualTo(3);
        assertThat(result).contains(notification1, notification2, notification3);
    }

    @Test
    void findWithReceiverIdReadOrNot() throws Exception {
        //given
        Member receiver = createMember("email", "pw");
        memberRepository.save(receiver);

        Notification notification1 = createNotification(receiver, "message1", NotificationType.OFFER, true);
        Notification notification2 = createNotification(receiver, "message2", NotificationType.BOOKMARK, true);
        Notification notification3 = createNotification(receiver, "message3", NotificationType.CHAT, false);
        notificationRepository.save(notification1);
        notificationRepository.save(notification2);
        notificationRepository.save(notification3);

        //when
        List<Notification> result = notificationRepository.findWithReceiverIdReadOrNot(receiver.getId(), true);

        //then
        assertThat(result.size()).isEqualTo(2);
        assertThat(result).contains(notification1, notification2);
    }
    
    @Test
    void deleteAlreadyChecked() throws Exception {
        //given
        Member receiver = createMember("email", "pw");
        memberRepository.save(receiver);

        Notification notification1 = createNotification(receiver, "message1", NotificationType.OFFER, true);
        Notification notification2 = createNotification(receiver, "message2", NotificationType.BOOKMARK, true);
        Notification notification3 = createNotification(receiver, "message3", NotificationType.CHAT, false);
        notificationRepository.save(notification1);
        notificationRepository.save(notification2);
        notificationRepository.save(notification3);
        
        //when
        notificationRepository.deleteAlreadyChecked(List.of(notification1.getId(), notification2.getId(), notification3.getId()));
        List<Notification> result = notificationRepository.findAll();

        //then
        assertThat(result.size()).isEqualTo(1);
        assertThat(result).extracting("message").contains("message3");
    }

    @Test
    void findSimplePage() throws Exception {
        //given
        Member receiver = createMember("email", "pw");
        memberRepository.save(receiver);
        for (int i = 1; i <= 20; i++) {
            Notification notification = createNotification(receiver, "message" + i, NotificationType.OFFER, false);
            notificationRepository.save(notification);
            Thread.sleep(10);
        }
        PageRequest pageable = PageRequest.of(0, 5);

        //when
        Page<NotificationQueryDto> result = notificationRepository.findSimplePage(receiver.getId(), pageable);

        //then
        assertThat(result.getTotalPages()).isEqualTo(4);
        assertThat(result).extracting("message").contains("message20", "message19", "message18", "message17", "message16");
    }

    private Member createMember(String email, String password) {
        return Member.builder()
                .email(email)
                .password(password)
                .build();
    }

    private Notification createNotification(Member receiver, String message, NotificationType type, boolean isChecked) {
        return Notification.builder()
                .receiver(receiver)
                .message(message)
                .type(type)
                .isChecked(isChecked)
                .build();
    }
}