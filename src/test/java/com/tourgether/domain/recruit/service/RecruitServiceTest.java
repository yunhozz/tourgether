package com.tourgether.domain.recruit.service;

import com.tourgether.domain.member.model.dto.MemberRequestDto;
import com.tourgether.domain.member.service.MemberService;
import com.tourgether.domain.recruit.controller.UpdateForm;
import com.tourgether.domain.recruit.model.dto.request.RecruitRequestDto;
import com.tourgether.domain.recruit.model.dto.response.RecruitResponseDto;
import com.tourgether.enums.Role;
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
class RecruitServiceTest {

    @Autowired
    private RecruitService recruitService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private BookmarkService bookmarkService;

    @Test
    void makeRecruit() throws Exception {
        //given
        MemberRequestDto memberRequestDto = createMemberDto("email", "123", "yunho", "pyh");
        Long userId = memberService.join(memberRequestDto);
        RecruitRequestDto recruitRequestDto1 = createRecruitDto("title1", "content1");
        RecruitRequestDto recruitRequestDto2 = createRecruitDto("title2", "content2");
        RecruitRequestDto recruitRequestDto3 = createRecruitDto("title3", "content3");

        //when
        recruitService.makeRecruit(recruitRequestDto1, userId);
        recruitService.makeRecruit(recruitRequestDto2, userId);
        recruitService.makeRecruit(recruitRequestDto3, userId);
        List<RecruitResponseDto> result = recruitService.findRecruitDtoList();

        //then
        assertThat(result.size()).isEqualTo(3);
        assertThat(result).extracting("writerId").containsOnly(userId);
        assertThat(result).extracting("title").contains("title1", "title2", "title3");
    }

    @Test
    void updateRecruit() throws Exception {
        //given
        MemberRequestDto memberRequestDto = createMemberDto("email", "123", "yunho", "pyh");
        Long userId = memberService.join(memberRequestDto);
        RecruitRequestDto recruitRequestDto1 = createRecruitDto("title1", "content1");
        RecruitRequestDto recruitRequestDto2 = createRecruitDto("title2", "content2");
        RecruitRequestDto recruitRequestDto3 = createRecruitDto("title3", "content3");

        //when
        Long id1 = recruitService.makeRecruit(recruitRequestDto1, userId);
        Long id2 = recruitService.makeRecruit(recruitRequestDto2, userId);
        Long id3 = recruitService.makeRecruit(recruitRequestDto3, userId);

        UpdateForm updateForm = new UpdateForm("update-title", "update-content");
        recruitService.updateRecruit(id1, updateForm);
        List<RecruitResponseDto> result = recruitService.findRecruitDtoList();

        //then
        assertThat(result.size()).isEqualTo(3);
        assertThat(result.get(0).getTitle()).isEqualTo("update-title");
        assertThat(result.get(0).getContent()).isEqualTo("update-content");
    }

    @Test
    void deleteRecruit() throws Exception {
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

    private RecruitRequestDto createRecruitDto(String title, String content) {
        return new RecruitRequestDto(title, content);
    }
}