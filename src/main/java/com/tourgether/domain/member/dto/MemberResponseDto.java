package com.tourgether.domain.member.dto;

import com.tourgether.domain.member.Member;
import com.tourgether.global.enums.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class MemberResponseDto {

    private Long id;
    private String oAuth2Id;
    private String email;
    private String password;
    private String name;
    private String nickname;
    private String profileUrl;
    private Role role;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;

    public MemberResponseDto(Member member) {
        id = member.getId();
        oAuth2Id = member.getOAuth2Id();
        email = member.getEmail();
        password = member.getPassword();
        name = member.getName();
        nickname = member.getNickname();
        profileUrl = member.getProfileUrl();
        role = member.getRole();
        createdDate = member.getCreatedDate();
        lastModifiedDate = member.getLastModifiedDate();
    }
}
