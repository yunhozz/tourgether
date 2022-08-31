package com.tourgether.api;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static com.tourgether.dto.ChatDto.*;

@RestController
@RequiredArgsConstructor
public class ChatApiController {

    private final SimpMessagingTemplate template;

    /**
     * StompWebSocketConfig 에서 등록한 applicationDestinationPrefixes 와 @MessageMapping 의 경로가 합쳐진다. (/publish/chat/join)
     */
    @MessageMapping("/chat/join")
    public void join(@RequestBody ChatRequestDto chatRequestDto) {
        chatRequestDto.setMessage(chatRequestDto.getWriter() + "님이 입장하셨습니다.");
        template.convertAndSend("/subscribe/chat/room/" + chatRequestDto.getChatroomId(), chatRequestDto);
    }

    @MessageMapping("/chat/message")
    public void message(@RequestBody ChatRequestDto chatRequestDto) {
        template.convertAndSend("/subscribe/chat/room/" + chatRequestDto.getChatroomId(), chatRequestDto);
    }
}
