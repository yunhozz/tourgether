package com.tourgether.dto;

import com.tourgether.domain.member.model.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Optional;

@Getter
@AllArgsConstructor
public class MemberSessionResponseDto implements Serializable {

    private Long id;
    private Long teamId;
    private String email;
    private String password;
    private String name;
    private String nickname;
    private String profileImgUrl;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;

    public MemberSessionResponseDto(Member member) {
        id = member.getId();
        Optional.ofNullable(member.getTeam()).ifPresent(team -> teamId = team.getId());
        email = member.getEmail();
        password = member.getPassword();
        name = member.getName();
        nickname = member.getNickname();
        profileImgUrl = member.getProfileImgUrl();
        createdDate = member.getCreatedDate();
        lastModifiedDate = member.getLastModifiedDate();
    }
}
