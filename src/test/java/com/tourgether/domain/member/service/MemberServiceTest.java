package com.tourgether.domain.member.service;

import com.tourgether.domain.member.controller.form.UpdateForm;
import com.tourgether.domain.member.model.dto.MemberRequestDto;
import com.tourgether.domain.member.model.dto.MemberResponseDto;
import com.tourgether.dto.MemberSessionResponseDto;
import com.tourgether.enums.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Test
    void join() throws Exception {
        //given
        MemberRequestDto memberRequestDto = createMemberDto("email", "123", "yunho", "nickname");

        //when
        Long memberId = memberService.join(memberRequestDto);
        MemberResponseDto result = memberService.findMemberDto(memberId);

        //then
        assertThat(memberId).isEqualTo(result.getId());
    }

    @Test
    void joinFail() throws Exception {
        //given
        MemberRequestDto memberRequestDto = createMemberDto("email1", "123", "yunho1", "nickname1");
        MemberRequestDto memberRequestDtoEmailDuplicated = createMemberDto("email1", "456", "yunho2", "nickname2");
        MemberRequestDto memberRequestDtoNicknameDuplicated = createMemberDto("email2", "789", "yunho3", "nickname1");

        //when
        memberService.join(memberRequestDto);
        try {
            memberService.join(memberRequestDtoEmailDuplicated);
            memberService.join(memberRequestDtoNicknameDuplicated);
        } catch (Exception e) {
            e.printStackTrace();
            assertThat(e).hasMessage("이메일이 중복된 회원이 존재합니다.");
            assertThat(e).hasMessage("닉네임이 중복된 회원이 존재합니다.");
        }
    }

    @Test
    void login() throws Exception {
        //given
        MemberRequestDto memberRequestDto = createMemberDto("email", "123", "yunho", "nickname");
        memberService.join(memberRequestDto);

        //when
        UserDetails userDetails = userDetailsService.loadUserByUsername(memberRequestDto.getEmail());
        MemberSessionResponseDto member = memberService.login(userDetails, "123");

        //then
        assertThat(member.getEmail()).isEqualTo(userDetails.getUsername());
    }

    @Test
    void loginFail() throws Exception {
        //given
        MemberRequestDto memberRequestDto = createMemberDto("email", "123", "yunho", "nickname");
        memberService.join(memberRequestDto);

        //when
        UserDetails userDetails = userDetailsService.loadUserByUsername(memberRequestDto.getEmail());
        try {
            memberService.login(userDetails, "789");
        } catch (Exception e) {
            e.printStackTrace();
            assertThat(e).hasMessage("비밀번호가 다릅니다.");
        }
    }

    @Test
    void updatePassword() throws Exception {
        //given
        MemberRequestDto memberRequestDto = createMemberDto("email", "123", "yunho", "nickname");
        Long userId = memberService.join(memberRequestDto);

        //when
        memberService.updatePassword(userId, "123", "789");
        MemberResponseDto member = memberService.findMemberDto(userId);

        //then
        assertThat(member.getEmail()).isEqualTo("email");
        assertThat(member.getPassword()).isEqualTo("789");
    }

    @Test
    void updatePasswordFail() throws Exception {
        //given
        MemberRequestDto memberRequestDto = createMemberDto("email", "123", "yunho", "nickname");
        Long userId = memberService.join(memberRequestDto);

        //when
        try {
            memberService.updatePassword(userId, "456", "789");
        } catch (Exception e) {
            e.printStackTrace();
            assertThat(e).hasMessage("비밀번호가 다릅니다.");
        }
    }

    @Test
    void updateInfo() throws Exception {
        //given
        MemberRequestDto memberRequestDto = createMemberDto("email", "123", "yunho", "nickname");
        Long userId = memberService.join(memberRequestDto);

        //when
        UpdateForm updateForm = new UpdateForm("yunho park", "updated nickname", "profileUrl");
        memberService.updateInfo(userId, updateForm);
        MemberResponseDto member = memberService.findMemberDto(userId);

        //then
        assertThat(member.getName()).isEqualTo("yunho park");
        assertThat(member.getNickname()).isEqualTo("updated nickname");
        assertThat(member.getProfileImgUrl()).isEqualTo("profileUrl");
    }

    @Test
    void withdraw() throws Exception {
        //given
        MemberRequestDto memberRequestDto = createMemberDto("email", "123", "yunho", "nickname");
        Long userId = memberService.join(memberRequestDto);

        //when
        memberService.withdraw(userId, "123");

        //then
    }

    @Test
    void withdrawFail() throws Exception {
        //given


        //when

        //then
    }

    private MemberRequestDto createMemberDto(String email, String password, String name, String nickname) {
        return MemberRequestDto.builder()
                .email(email)
                .password(password)
                .name(name)
                .nickname(nickname)
                .role(Role.USER)
                .build();
    }
}