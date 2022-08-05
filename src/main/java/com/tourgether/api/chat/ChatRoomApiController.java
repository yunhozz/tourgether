package com.tourgether.api.chat;

import com.tourgether.domain.chat.service.ChatRoomService;
import com.tourgether.domain.chat.dto.request.ChatRoomRequestDto;
import com.tourgether.domain.chat.dto.response.ChatRoomResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ChatRoomApiController {

    private final ChatRoomService chatRoomService;

    @GetMapping("/chatroom/{chatRoomId}")
    public ChatRoomResponseDto getChatRoom(@PathVariable String chatRoomId) {
        return chatRoomService.findChatRoomDto(Long.valueOf(chatRoomId));
    }

    @GetMapping("/chatroom/list")
    public List<ChatRoomResponseDto> getChatRoomList() {
        return chatRoomService.findChatRoomDtoList();
    }

    @PostMapping("/chatroom/create")
    public ResponseEntity<Long> createChatRoom(@RequestBody ChatRoomRequestDto chatRoomRequestDto) {
        return ResponseEntity.ok(chatRoomService.makeChatRoom(chatRoomRequestDto));
    }

    @PatchMapping("/chatroom/{chatRoomId}/update")
    public String updateChatRoom(@PathVariable String chatRoomId, @RequestParam String title) {
        chatRoomService.updateTitle(Long.valueOf(chatRoomId), title);
        return "update complete";
    }

    @DeleteMapping("/chatroom/{chatRoomId}/delete")
    public String deleteChatRoom(@PathVariable String chatRoomId) {
        chatRoomService.deleteChatRoom(Long.valueOf(chatRoomId));
        return "chat room(id : " + chatRoomId + ") is deleted.";
    }
}
