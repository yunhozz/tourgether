package com.tourgether.domain.chat.model.entity;

import com.tourgether.domain.BaseTime;
import com.tourgether.domain.member.model.entity.Member;
import com.tourgether.enums.MessageType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Chat extends BaseTime {

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

    @Enumerated(EnumType.STRING)
    private MessageType type; // ENTER, TALK, EXIT

    @Builder
    private Chat(Member sender, ChatRoom chatRoom, String message, MessageType type) {
        this.sender = sender;
        this.chatRoom = chatRoom;
        this.message = message;
        this.type = type;
    }
}
