package com.tourgether.domain.chat.model.entity;

import com.tourgether.domain.TimeEntity;
import com.tourgether.domain.member.model.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Chat extends TimeEntity {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    private Member sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_id")
    private ChatRoom chatRoom;

    @Column(length = 500)
    private String message;

    @Builder
    private Chat(Member sender, ChatRoom chatRoom, String message) {
        this.sender = sender;
        this.chatRoom = chatRoom;
        this.message = message;
    }
}
