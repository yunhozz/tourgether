package com.tourgether.domain.member.dto;

import com.tourgether.domain.member.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
@Builder
@AllArgsConstructor
public class MemberRequestDto {

    @NotBlank
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String name;

    @NotBlank
    private String nickname;

    private String profileUrl;

    @NotBlank
    private String auth;

    public Member toEntity() {
        return Member.builder()
                .email(email)
                .password(password)
                .name(name)
                .nickname(nickname)
                .profileUrl(profileUrl)
                .auth(auth)
                .build();
    }
}
