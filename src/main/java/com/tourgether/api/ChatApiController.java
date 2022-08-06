package com.tourgether.api;

import com.tourgether.domain.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.tourgether.dto.ChatDto.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ChatApiController {

    private final ChatService chatService;

    @GetMapping("/chat/{chatId}")
    public ChatResponseDto getChat(@PathVariable String chatId) {
        return chatService.findChatDto(Long.valueOf(chatId));
    }

    @GetMapping("/chat/list")
    public List<ChatResponseDto> getChatList() {
        return chatService.findChatDtoList();
    }

    @PostMapping("/chat/create")
    public ResponseEntity<Long> createChat(@RequestBody ChatRequestDto chatRequestDto) {
        return ResponseEntity.ok(chatService.makeChat(chatRequestDto));
    }
}
