package com.tourgether.domain.chat.service;

import com.tourgether.domain.chat.dto.request.ChatRequestDto;
import com.tourgether.domain.chat.model.entity.Chat;
import com.tourgether.domain.chat.model.entity.ChatRoom;
import com.tourgether.domain.chat.model.repository.ChatRepository;
import com.tourgether.domain.chat.model.repository.ChatRoomRepository;
import com.tourgether.domain.chat.dto.response.ChatResponseDto;
import com.tourgether.domain.member.model.entity.Member;
import com.tourgether.domain.member.model.repository.MemberRepository;
import com.tourgether.enums.ErrorCode;
import com.tourgether.exception.chat.ChatNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final MemberRepository memberRepository;

    public Long makeChat(ChatForm chatForm, Long senderId, Long chatRoomId) {
        Member sender = memberRepository.getReferenceById(senderId);
        ChatRoom chatRoom = chatRoomRepository.getReferenceById(chatRoomId);
        ChatRequestDto chatRequestDto = ChatRequestDto.builder()
                .sender(sender)
                .chatRoom(chatRoom)
                .message(chatForm.getMessage())
                .type(chatForm.getType())
                .build();

        return chatRepository.save(chatRequestDto.toEntity()).getId();
    }

    @Transactional(readOnly = true)
    public ChatResponseDto findChatDto(Long id) {
        return new ChatResponseDto(findChat(id));
    }

    @Transactional(readOnly = true)
    public List<ChatResponseDto> findChatDtoList() {
        return chatRepository.findAll().stream()
                .map(ChatResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    private Chat findChat(Long id) {
        return chatRepository.findById(id)
                .orElseThrow(() -> new ChatNotFoundException("This chat is null: " + id, ErrorCode.CHAT_NOT_FOUND));
    }
}
