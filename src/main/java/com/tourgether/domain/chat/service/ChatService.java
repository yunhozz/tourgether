package com.tourgether.domain.chat.service;

import com.tourgether.domain.chat.model.dto.request.ChatRequestDto;
import com.tourgether.domain.chat.model.dto.request.ChatRoomMemberRequestDto;
import com.tourgether.domain.chat.model.dto.request.ChatRoomRequestDto;
import com.tourgether.domain.chat.model.dto.response.ChatResponseDto;
import com.tourgether.domain.chat.model.dto.response.ChatRoomMemberResponseDto;
import com.tourgether.domain.chat.model.dto.response.ChatRoomResponseDto;
import com.tourgether.domain.chat.model.entity.Chat;
import com.tourgether.domain.chat.model.entity.ChatRoom;
import com.tourgether.domain.chat.model.entity.ChatRoomMember;
import com.tourgether.domain.chat.model.repository.ChatRepository;
import com.tourgether.domain.chat.model.repository.ChatRoomMemberRepository;
import com.tourgether.domain.chat.model.repository.ChatRoomRepository;
import com.tourgether.domain.member.model.Member;
import com.tourgether.domain.member.model.repository.MemberRepository;
import com.tourgether.enums.ErrorCode;
import com.tourgether.exception.MemberNotFoundException;
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
    private final ChatRoomMemberRepository chatRoomMemberRepository;
    private final MemberRepository memberRepository;

    public Long makeChatRoom(ChatRoomRequestDto chatRoomRequestDto, Long userId) {
        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new MemberNotFoundException("This member is null: " + userId, ErrorCode.MEMBER_NOT_FOUND));
        ChatRoom chatRoom = chatRoomRequestDto.toEntity();
        ChatRoomMemberRequestDto chatRoomMemberRequestDto = new ChatRoomMemberRequestDto(member, chatRoom);

        chatRoomMemberRepository.save(chatRoomMemberRequestDto.toEntity()); // cascade: persist chatroom
        return chatRoom.getId();
    }

    public Long sendMessage(ChatRequestDto chatRequestDto, Long userId, Long chatRoomId) {
        Member sender = memberRepository.findById(userId)
                .orElseThrow(() -> new MemberNotFoundException("This member is null: " + userId, ErrorCode.MEMBER_NOT_FOUND));
        ChatRoom chatRoom = findChatRoom(chatRoomId);

        chatRequestDto.setSender(sender);
        chatRequestDto.setChatRoom(chatRoom);
        Chat chat = chatRepository.save(chatRequestDto.toEntity());

        return chat.getId();
    }

    @Transactional(readOnly = true)
    public ChatResponseDto findChatDto(Long id) {
        return new ChatResponseDto(findChat(id));
    }

    @Transactional(readOnly = true)
    public ChatRoomResponseDto findChatRoomDto(Long id) {
        return new ChatRoomResponseDto(findChatRoom(id));
    }

    @Transactional(readOnly = true)
    public ChatRoomMemberResponseDto findChatRoomMemberDto(Long id) {
        return new ChatRoomMemberResponseDto(findChatRoomMember(id));
    }

    @Transactional(readOnly = true)
    public List<ChatResponseDto> findChatDtoList() {
        return chatRepository.findAll().stream()
                .map(ChatResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ChatRoomResponseDto> findChatRoomDtoList() {
        return chatRoomRepository.findAll().stream()
                .map(ChatRoomResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ChatRoomMemberResponseDto> findChatRoomMemberDtoList() {
        return chatRoomMemberRepository.findAll().stream()
                .map(ChatRoomMemberResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    private Chat findChat(Long id) {
        return chatRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("This chat is null: " + id));
    }

    @Transactional(readOnly = true)
    private ChatRoom findChatRoom(Long id) {
        return chatRoomRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("This chatroom is null: " + id));
    }

    @Transactional(readOnly = true)
    private ChatRoomMember findChatRoomMember(Long id) {
        return chatRoomMemberRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("This chatroom member is null: " + id));
    }
}
