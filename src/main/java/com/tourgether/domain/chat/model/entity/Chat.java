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
    private Member writer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chatroom_id")
    private Chatroom chatroom;

    @Column(length = 500)
    private String message;

    private MessageType type; // ENTER, TALK

    @Builder
    private Chat(Member writer, Chatroom chatroom, String message, MessageType type) {
        this.writer = writer;
        this.chatroom = chatroom;
        this.message = message;
        this.type = type;
    }
}
