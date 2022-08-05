package com.tourgether.domain.chat.model.entity;

import com.tourgether.domain.TimeEntity;
import com.tourgether.domain.member.model.entity.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoomMember extends TimeEntity {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_id")
    private ChatRoom chatRoom;

    private RoomMember(Member member) {
        this.member = member;
    }

    public static RoomMember createRoomMember(Member member, ChatRoom chatRoom) {
        RoomMember roomMember = new RoomMember(member);
        roomMember.setChatRoom(chatRoom);

        return roomMember;
    }

    // 연관관계 메소드
    private void setChatRoom(ChatRoom chatRoom) {
        this.chatRoom = chatRoom;
        chatRoom.getRoomMembers().add(this);
    }
}
