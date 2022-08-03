package com.tourgether.domain.notification.service;

import com.tourgether.domain.member.model.dto.request.MemberRequestDto;
import com.tourgether.domain.member.service.MemberService;
import com.tourgether.domain.notification.model.dto.NotificationRequestDto;
import com.tourgether.domain.notification.model.dto.NotificationResponseDto;
import com.tourgether.enums.NotificationType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class NotificationServiceTest {

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private MemberService memberService;

    @Test
    void connect() throws Exception {
        //given
        MemberRequestDto memberDto = createMemberDto(String.valueOf(System.currentTimeMillis()), "email", "123", "java", "spring");
        Long receiverId = memberService.join(memberDto);
        String lastEventId = "";

        //then
        assertDoesNotThrow(() -> notificationService.connect(receiverId, lastEventId));
    }

    @Test
    void sendNotification() throws Exception {
        //given
        MemberRequestDto memberDto = createMemberDto(String.valueOf(System.currentTimeMillis()), "email", "123", "java", "spring");
        NotificationRequestDto notificationDto1 = createNotificationDto("message1", NotificationType.OFFER);
        NotificationRequestDto notificationDto2 = createNotificationDto("message2", NotificationType.CHAT);
        NotificationRequestDto notificationDto3 = createNotificationDto("message3", NotificationType.BOOKMARK);
        Long receiverId = memberService.join(memberDto);
        String lastEventId = "";

        //when
        notificationService.connect(receiverId, lastEventId);

        //then
        assertDoesNotThrow(() -> notificationService.sendNotification(notificationDto1, receiverId));
        assertDoesNotThrow(() -> notificationService.sendNotification(notificationDto2, receiverId));
        assertDoesNotThrow(() -> notificationService.sendNotification(notificationDto3, receiverId));
    }

    @Test
    void readNotification() throws Exception {
        //given
        MemberRequestDto memberDto = createMemberDto(String.valueOf(System.currentTimeMillis()), "email", "123", "java", "spring");
        NotificationRequestDto notificationDto1 = createNotificationDto("message1", NotificationType.OFFER);
        NotificationRequestDto notificationDto2 = createNotificationDto("message2", NotificationType.CHAT);
        NotificationRequestDto notificationDto3 = createNotificationDto("message3", NotificationType.BOOKMARK);
        Long receiverId = memberService.join(memberDto);
        String lastEventId = "";

        //when
        notificationService.connect(receiverId, lastEventId);
        Long id1 = notificationService.sendNotification(notificationDto1, receiverId);
        Long id2 = notificationService.sendNotification(notificationDto2, receiverId);
        Long id3 = notificationService.sendNotification(notificationDto3, receiverId);

        notificationService.readNotification(id2);
        List<NotificationResponseDto> result = notificationService.findNotificationDtoList();

        //then
        assertThat(result.size()).isEqualTo(3);
        assertThat(result).extracting("receiverId").containsOnly(receiverId);
        assertThat(result.get(0).isChecked()).isFalse();
        assertThat(result.get(1).isChecked()).isTrue();
        assertThat(result.get(2).isChecked()).isFalse();
    }

    @Test
    void readNotifications() throws Exception {
        //given
        MemberRequestDto memberDto = createMemberDto(String.valueOf(System.currentTimeMillis()), "email", "123", "java", "spring");
        NotificationRequestDto notificationDto1 = createNotificationDto("message1", NotificationType.OFFER);
        NotificationRequestDto notificationDto2 = createNotificationDto("message2", NotificationType.CHAT);
        NotificationRequestDto notificationDto3 = createNotificationDto("message3", NotificationType.BOOKMARK);
        Long receiverId = memberService.join(memberDto);
        String lastEventId = "";

        //when
        notificationService.connect(receiverId, lastEventId);
        notificationService.sendNotification(notificationDto1, receiverId);
        notificationService.sendNotification(notificationDto2, receiverId);
        notificationService.sendNotification(notificationDto3, receiverId);

        notificationService.readNotifications(receiverId);
        List<NotificationResponseDto> result = notificationService.findNotificationDtoList();

        //then
        assertThat(result.size()).isEqualTo(3);
        assertThat(result).extracting("receiverId").containsOnly(receiverId);
        assertThat(result).extracting("isChecked").containsOnly(true);
    }

    @Test
    void findNotificationDtoListWithReceiverIdReadOrNot() throws Exception {
        //given
        MemberRequestDto memberDto = createMemberDto(String.valueOf(System.currentTimeMillis()), "email", "123", "java", "spring");
        NotificationRequestDto notificationDto1 = createNotificationDto("message1", NotificationType.OFFER);
        NotificationRequestDto notificationDto2 = createNotificationDto("message2", NotificationType.CHAT);
        NotificationRequestDto notificationDto3 = createNotificationDto("message3", NotificationType.BOOKMARK);
        Long receiverId = memberService.join(memberDto);
        String lastEventId = "";

        //when
        notificationService.connect(receiverId, lastEventId);
        Long id1 = notificationService.sendNotification(notificationDto1, receiverId);
        Long id2 = notificationService.sendNotification(notificationDto2, receiverId);
        Long id3 = notificationService.sendNotification(notificationDto3, receiverId);

        NotificationResponseDto notification1 = notificationService.readNotification(id1);
        NotificationResponseDto notification2 = notificationService.readNotification(id2);
        List<NotificationResponseDto> result = notificationService.findNotificationDtoListWithReceiverIdReadOrNot(receiverId, true);

        //then
        assertThat(result.size()).isEqualTo(2);
        assertThat(result).extracting("isChecked").containsOnly(true);
        assertThat(result).extracting("id").contains(id1, id2);
    }

    private MemberRequestDto createMemberDto(String oAuth2Id, String email, String password, String name, String nickname) {
        return new MemberRequestDto(oAuth2Id, email, password, name, nickname, null);
    }

    private NotificationRequestDto createNotificationDto(String message, NotificationType type) {
        return NotificationRequestDto.builder()
                .message(message)
                .type(type)
                .redirectUrl(null)
                .isChecked(false)
                .build();
    }
}