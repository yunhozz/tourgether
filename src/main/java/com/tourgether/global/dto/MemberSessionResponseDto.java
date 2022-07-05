package com.tourgether.global.dto;

import com.tourgether.domain.member.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class MemberSessionResponseDto implements Serializable {

    private Long id;
    private String email;
    private String password;
    private String name;
    private String nickname;
    private String profileUrl;
    private String auth;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;

    public MemberSessionResponseDto(Member member) {
        id = member.getId();
        email = member.getEmail();
        password = member.getPassword();
        name = member.getName();
        nickname = member.getNickname();
        profileUrl = member.getProfileUrl();
        auth = member.getAuth();
        createdDate = member.getCreatedDate();
        lastModifiedDate = member.getLastModifiedDate();
    }
}
