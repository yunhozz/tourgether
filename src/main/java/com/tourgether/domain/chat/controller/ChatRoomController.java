package com.tourgether.domain.chat.controller;

import com.tourgether.domain.chat.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatRoomController {

    private final ChatRoomService chatRoomService;
}
