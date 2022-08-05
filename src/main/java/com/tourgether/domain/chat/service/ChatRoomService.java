package com.tourgether.domain.chat.service;

import com.tourgether.domain.chat.model.entity.ChatRoom;
import com.tourgether.domain.chat.model.entity.RoomMember;
import com.tourgether.domain.chat.model.repository.ChatRoomRepository;
import com.tourgether.domain.chat.model.repository.RoomMemberRepository;
import com.tourgether.domain.chat.dto.request.ChatRoomRequestDto;
import com.tourgether.domain.chat.dto.response.ChatRoomResponseDto;
import com.tourgether.domain.member.model.entity.Member;
import com.tourgether.domain.member.model.repository.MemberRepository;
import com.tourgether.enums.ErrorCode;
import com.tourgether.exception.chat.ChatRoomNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final RoomMemberRepository roomMemberRepository;
    private final MemberRepository memberRepository;

    public Long makeChatRoom(ChatRoomRequestDto chatRoomRequestDto, Long userId) {
        Member member = memberRepository.getReferenceById(userId);
        ChatRoom chatRoom = chatRoomRequestDto.toEntity(member);
        RoomMember roomMember = RoomMember.createRoomMember(member, chatRoom);

        roomMemberRepository.save(roomMember);
        return chatRoomRepository.save(chatRoom).getId();
    }

    public void updateTitle(Long id, String title) {
        ChatRoom chatRoom = findChatRoom(id);
        chatRoom.updateTitle(title);
    }

    public void deleteChatRoom(Long id) {
        ChatRoom chatRoom = findChatRoom(id);
        chatRoomRepository.delete(chatRoom); // orphan remove : RoomMember
    }

    @Transactional(readOnly = true)
    public ChatRoomResponseDto findChatRoomDto(Long id) {
        return new ChatRoomResponseDto(findChatRoom(id));
    }

    @Transactional(readOnly = true)
    public List<ChatRoomResponseDto> findChatRoomDtoList() {
        return chatRoomRepository.findAll().stream()
                .map(ChatRoomResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    private ChatRoom findChatRoom(Long id) {
        return chatRoomRepository.findById(id)
                .orElseThrow(() -> new ChatRoomNotFoundException("This chat room is null: " + id, ErrorCode.CHATROOM_NOT_FOUND));
    }
}
