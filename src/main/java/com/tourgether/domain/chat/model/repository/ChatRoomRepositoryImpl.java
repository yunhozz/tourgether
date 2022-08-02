package com.tourgether.domain.chat.model.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tourgether.domain.chat.model.dto.response.ChatRoomMemberResponseDto;
import com.tourgether.domain.chat.model.dto.response.ChatRoomResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.tourgether.domain.chat.model.entity.QChatRoom.*;
import static com.tourgether.domain.chat.model.entity.QChatRoomMember.*;
import static com.tourgether.domain.member.model.entity.QMember.*;

@Repository
@RequiredArgsConstructor
public class ChatRoomRepositoryImpl implements ChatRoomRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<ChatRoomResponseDto> findWithUserId(Long userId) {
        List<ChatRoomMemberResponseDto> chatRoomMemberList = queryFactory
                .select(Projections.constructor(
                        ChatRoomMemberResponseDto.class,
                        chatRoomMember.id,
                        member.id,
                        chatRoom.id,
                        chatRoomMember.createdDate,
                        chatRoomMember.lastModifiedDate
                ))
                .from(chatRoomMember)
                .join(chatRoomMember.member, member)
                .join(chatRoomMember.chatRoom, chatRoom)
                .where(member.id.eq(userId))
                .fetch();

        List<Long> chatRoomIds = chatRoomMemberList.stream()
                .map(ChatRoomMemberResponseDto::getChatRoomId)
                .toList();

        return queryFactory
                .select(Projections.constructor(
                        ChatRoomResponseDto.class,
                        chatRoom.id,
                        chatRoom.title,
                        chatRoom.createdDate,
                        chatRoom.lastModifiedDate
                ))
                .from(chatRoom)
                .where(chatRoom.id.in(chatRoomIds))
                .orderBy(chatRoom.lastModifiedDate.desc())
                .fetch();
    }
}
