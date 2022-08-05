package com.tourgether.domain.member.dto;

import com.tourgether.domain.member.model.entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class MemberRequestDto {

    @NotBlank
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String name;

    @NotBlank
    private String nickname;

    private String profileImgUrl;

    @Builder
    private MemberRequestDto(String email, String password, String name, String nickname, String profileImgUrl) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.profileImgUrl = profileImgUrl;
    }

    public Member toEntity() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return Member.builder()
                .email(email)
                .password(encoder.encode(password))
                .name(name)
                .nickname(nickname)
                .profileImgUrl(profileImgUrl)
                .build();
    }
}
