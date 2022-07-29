package com.tourgether.domain.chat.model.repository;

import com.tourgether.domain.chat.model.dto.response.ChatRoomResponseDto;
import com.tourgether.domain.chat.model.entity.ChatRoom;
import com.tourgether.domain.chat.model.entity.ChatRoomMember;
import com.tourgether.domain.member.model.entity.Member;
import com.tourgether.domain.member.model.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@Commit
class ChatRoomRepositoryTest {

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Autowired
    private ChatRoomMemberRepository chatRoomMemberRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void findWithUserId() throws Exception {
        //given
        Member member1 = createMember("email1", "111", "yunho1", "pyh1");
        Member member2 = createMember("email2", "222", "yunho2", "pyh2");
        memberRepository.save(member1);
        memberRepository.save(member2);

        ChatRoom chatRoom1 = createChatRoom("room1");
        ChatRoom chatRoom2 = createChatRoom("room2");
        ChatRoom chatRoom3 = createChatRoom("room3");
        chatRoomRepository.save(chatRoom1);
        chatRoomRepository.save(chatRoom2);
        chatRoomRepository.save(chatRoom3);

        ChatRoomMember chatRoomMember1 = createChatRoomMember(member1, chatRoom1);
        ChatRoomMember chatRoomMember2 = createChatRoomMember(member1, chatRoom2);
        ChatRoomMember chatRoomMember3 = createChatRoomMember(member1, chatRoom3);
        ChatRoomMember chatRoomMember4 = createChatRoomMember(member2, chatRoom1);
        ChatRoomMember chatRoomMember5 = createChatRoomMember(member2, chatRoom2);
        chatRoomMemberRepository.save(chatRoomMember1);
        chatRoomMemberRepository.save(chatRoomMember2);
        chatRoomMemberRepository.save(chatRoomMember3);
        chatRoomMemberRepository.save(chatRoomMember4);
        chatRoomMemberRepository.save(chatRoomMember5);

        //when
        List<ChatRoomResponseDto> result1 = chatRoomRepository.findWithUserId(member1.getId());
        List<ChatRoomResponseDto> result2 = chatRoomRepository.findWithUserId(member2.getId());

        //then
        assertThat(result1.size()).isEqualTo(3);
        assertThat(result2.size()).isEqualTo(2);
        assertThat(result1).extracting("title").contains("room1", "room2", "room3");
        assertThat(result2).extracting("title").contains("room1", "room2");
    }

    private ChatRoom createChatRoom(String title) {
        return new ChatRoom(title);
    }

    private ChatRoomMember createChatRoomMember(Member member, ChatRoom chatRoom) {
        return new ChatRoomMember(member, chatRoom);
    }

    private Member createMember(String email, String password, String name, String nickname) {
        return Member.builder()
                .email(email)
                .password(password)
                .name(name)
                .nickname(nickname)
                .build();
    }
}