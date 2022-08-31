package com.tourgether.domain.chat.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tourgether.domain.chat.model.entity.Chatroom;
import com.tourgether.domain.chat.model.repository.ChatroomRepository;
import com.tourgether.enums.ErrorCode;
import com.tourgether.exception.chat.ChatRoomNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import static com.tourgether.dto.ChatDto.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class ChatHandler extends TextWebSocketHandler {

    private final ChatroomRepository chatroomRepository;
    private final ObjectMapper objectMapper;

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        ChatRequestDto chatRequestDto = objectMapper.readValue(payload, ChatRequestDto.class);
        Long chatroomId = chatRequestDto.getChatroomId();
        log.info("payload = {}", payload);

        Chatroom chatroom = chatroomRepository.findById(chatroomId)
                .orElseThrow(() -> new ChatRoomNotFoundException(ErrorCode.CHATROOM_NOT_FOUND));
        chatroom.handleMessage(session, chatRequestDto, objectMapper);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        Chatroom.sessions.remove(session);
    }
}
