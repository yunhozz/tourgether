package com.tourgether.domain.chat.service;

import com.tourgether.domain.chat.model.entity.Chatroom;
import com.tourgether.domain.chat.model.entity.RoomMember;
import com.tourgether.domain.chat.model.repository.ChatroomRepository;
import com.tourgether.domain.chat.model.repository.RoomMemberRepository;
import com.tourgether.domain.member.model.entity.Member;
import com.tourgether.domain.member.model.repository.MemberRepository;
import com.tourgether.enums.ErrorCode;
import com.tourgether.exception.chat.ChatRoomNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.tourgether.dto.ChatDto.*;

@Service
@Transactional
@RequiredArgsConstructor
public class ChatroomService {

    private final ChatroomRepository chatroomRepository;
    private final MemberRepository memberRepository;
    private final RoomMemberRepository roomMemberRepository;

    public Long createChatroom(ChatroomRequestDto chatroomRequestDto, Long userId) {
        Member member = memberRepository.getReferenceById(userId);
        Chatroom chatroom = new Chatroom(member, chatroomRequestDto.getTitle());
        RoomMember roomMember = new RoomMember(chatroom, member);

        roomMemberRepository.save(roomMember);
        return chatroomRepository.save(chatroom).getId();
    }

    public void updateTitle(Long chatroomId, String title) {
        findChatroom(chatroomId).updateTitle(title);
    }

    @Transactional(readOnly = true)
    public ChatroomResponseDto findChatroomDto(Long chatroomId) {
        return new ChatroomResponseDto(findChatroom(chatroomId));
    }

    @Transactional(readOnly = true)
    public List<ChatroomResponseDto> findChatroomDtoList() {
        return chatroomRepository.findAll().stream()
                .map(ChatroomResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    private Chatroom findChatroom(Long chatroomId) {
        return chatroomRepository.findById(chatroomId)
                .orElseThrow(() -> new ChatRoomNotFoundException("This chatroom is null: " + chatroomId, ErrorCode.CHATROOM_NOT_FOUND));
    }
}
