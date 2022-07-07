package com.tourgether.domain.notification;

import com.tourgether.domain.TimeEntity;
import com.tourgether.domain.member.Member;
import com.tourgether.global.enums.NotificationType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notification extends TimeEntity {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id")
    private Member receiver;

    private String message;

    @Enumerated(EnumType.STRING)
    private NotificationType type;

    private boolean isChecked;

    @Builder
    private Notification(Member receiver, String message, NotificationType type, boolean isChecked) {
        this.receiver = receiver;
        this.message = message;
        this.type = type;
        this.isChecked = isChecked;
    }

    public void check() {
        isChecked = true;
    }
}
