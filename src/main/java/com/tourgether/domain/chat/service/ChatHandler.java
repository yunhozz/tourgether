package com.tourgether.domain.chat.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class ChatHandler extends TextWebSocketHandler {

    private static final List<WebSocketSession> sessions = new ArrayList<>();

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        log.info(session.getId() + " : " + message);
        for (WebSocketSession webSocketSession : sessions) {
            webSocketSession.sendMessage(new TextMessage(session.getPrincipal().getName() + " : " + message.getPayload()));
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
        log.info(session.getPrincipal().getName() + ": 님이 입장하셨습니다.");
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session);
        log.info(session.getPrincipal().getName() + ": 님이 퇴장하셨습니다.");
    }
}
