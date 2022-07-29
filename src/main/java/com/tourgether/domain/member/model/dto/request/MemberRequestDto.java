package com.tourgether.domain.member.model.dto.request;

import com.tourgether.domain.member.model.entity.Member;
import com.tourgether.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Builder
@AllArgsConstructor
public class MemberRequestDto {

    @NotBlank
    private String oAuth2Id;

    @NotBlank
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String name;

    @NotBlank
    private String nickname;

    private String profileImgUrl;

    @NotNull
    private Role role;

    public Member toEntity() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return Member.builder()
                .oAuth2Id(oAuth2Id)
                .email(email)
                .password(encoder.encode(password))
                .name(name)
                .nickname(nickname)
                .profileImgUrl(profileImgUrl)
                .role(Role.USER)
                .build();
    }
}
