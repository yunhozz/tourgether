package com.tourgether.domain.notification.model.entity;

import com.tourgether.domain.BaseTime;
import com.tourgether.domain.member.model.entity.Member;
import com.tourgether.enums.NotificationType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notification extends BaseTime {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    private Member sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id")
    private Member receiver;

    private String message;

    @Enumerated(EnumType.STRING)
    private NotificationType type;

    private String redirectUrl;

    private boolean isChecked;

    @Builder
    private Notification(Member sender, Member receiver, String message, NotificationType type, String redirectUrl, boolean isChecked) {
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
        this.type = type;
        this.redirectUrl = redirectUrl;
        this.isChecked = isChecked;
    }

    public void check() {
        isChecked = true;
    }
}
