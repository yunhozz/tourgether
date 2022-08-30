package com.tourgether.domain.chat.controller;

import com.tourgether.domain.chat.service.ChatroomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static com.tourgether.dto.ChatDto.*;

@Controller
@RequiredArgsConstructor
public class ChatroomController {

    private final ChatroomService chatroomService;
    private final AtomicInteger seq = new AtomicInteger(0);

    @GetMapping("/chat/rooms")
    public String getChatroomList(Model model) {
        List<ChatroomResponseDto> chatroomList = chatroomService.findChatroomDtoList();
        model.addAttribute("rooms", chatroomList);

        return "chat/room-list";
    }

    @GetMapping("/chat/rooms/{chatroomId}")
    public String getChatroom(@PathVariable String chatroomId, Model model) {
        ChatroomResponseDto chatroom = chatroomService.findChatroomDto(Long.valueOf(chatroomId));
        model.addAttribute("room", chatroom);
        model.addAttribute("member", "member" + seq.incrementAndGet());

        return "chat/room";
    }

    @GetMapping("/chat-stomp/rooms/{chatroomId}")
    public String getStompChatroom(@PathVariable String chatroomId, Model model) {
        ChatroomResponseDto chatroom = chatroomService.findChatroomDto(Long.valueOf(chatroomId));
        model.addAttribute("room", chatroom);
        model.addAttribute("member", "member" + seq.incrementAndGet());

        return "chat-stomp/room";
    }
}
