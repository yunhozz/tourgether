package com.tourgether.domain.member.model.dto.response;

import com.tourgether.domain.member.model.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Optional;

@Getter
@AllArgsConstructor
public class MemberResponseDto {

    private Long id;
    private Long teamId;
    private String oAuth2Id;
    private String email;
    private String password;
    private String name;
    private String nickname;
    private String profileImgUrl;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;

    public MemberResponseDto(Member member) {
        id = member.getId();
        Optional.ofNullable(member.getTeam()).ifPresent(team -> teamId = team.getId());
        oAuth2Id = member.getOAuth2Id();
        email = member.getEmail();
        password = member.getPassword();
        name = member.getName();
        nickname = member.getNickname();
        profileImgUrl = member.getProfileImgUrl();
        createdDate = member.getCreatedDate();
        lastModifiedDate = member.getLastModifiedDate();
    }
}
