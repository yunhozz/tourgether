package com.tourgether.domain.chat.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tourgether.domain.chat.model.entity.ChatRoom;
import com.tourgether.domain.chat.model.repository.ChatRoomRepository;
import com.tourgether.domain.chat.service.dto.request.ChatRequestDto;
import com.tourgether.enums.ErrorCode;
import com.tourgether.exception.chat.ChatRoomNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class ChatHandler extends TextWebSocketHandler {

    private final ChatRoomRepository chatRoomRepository;
    private final ObjectMapper objectMapper;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("session : {}", session.getId());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        ChatRequestDto chatRequestDto = objectMapper.readValue(payload, ChatRequestDto.class);
        Long chatRoomId = chatRequestDto.getChatRoom().getId();
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new ChatRoomNotFoundException("This chat room is null: " + chatRoomId, ErrorCode.CHATROOM_NOT_FOUND));

        log.info("chat room id : {}", chatRoomId);
        log.info("payload : {}", payload);
        chatRoom.handleMessage(session, chatRequestDto, objectMapper);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        List<ChatRoom> chatRooms = chatRoomRepository.findAll();
        chatRooms.forEach(chatRoom -> chatRoom.getSessions().remove(session));
    }
}
