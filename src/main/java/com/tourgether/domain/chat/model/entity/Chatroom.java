package com.tourgether.domain.chat.model.entity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tourgether.domain.BaseTime;
import com.tourgether.domain.member.model.entity.Member;
import com.tourgether.enums.MessageType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import javax.persistence.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.tourgether.dto.ChatDto.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Chatroom extends BaseTime {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "chatroom")
    private List<RoomMember> roomMembers = new ArrayList<>();

    @Column(length = 50)
    private String title;

    @Transient
    public static final Set<WebSocketSession> sessions = new HashSet<>();

    public Chatroom(Member member, String title) {
        this.member = member;
        this.title = title;
    }

    public void handleMessage(WebSocketSession session, ChatRequestDto chatRequestDto, ObjectMapper objectMapper) {
        if (chatRequestDto.getType().equals(MessageType.JOIN)) {
            sessions.add(session);
            chatRequestDto.setMessage(chatRequestDto.getWriter() + "님이 입장했습니다.");
        }
        send(chatRequestDto, objectMapper);
    }

    public void updateTitle(String title) {
        this.title = title;
    }

    private <T> void send(T messageObject, ObjectMapper objectMapper) {
        sessions.parallelStream().forEach(session -> {
            try {
                TextMessage message = new TextMessage(objectMapper.writeValueAsString(messageObject));
                session.sendMessage(message);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
