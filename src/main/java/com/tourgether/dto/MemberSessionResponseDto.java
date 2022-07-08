package com.tourgether.dto;

import com.tourgether.domain.member.model.entity.Member;
import com.tourgether.enums.Role;
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
    private String profileImgUrl;
    private Role role;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;

    public MemberSessionResponseDto(Member member) {
        id = member.getId();
        email = member.getEmail();
        password = member.getPassword();
        name = member.getName();
        nickname = member.getNickname();
        profileImgUrl = member.getProfileImgUrl();
        role = member.getRole();
        createdDate = member.getCreatedDate();
        lastModifiedDate = member.getLastModifiedDate();
    }
}