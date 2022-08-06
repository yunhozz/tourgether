package com.tourgether.dto;

import com.tourgether.domain.member.model.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

public class MemberDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MemberRequestDto {

        @NotBlank
        private String email;

        @NotBlank
        private String password;

        @NotBlank
        private String name;

        @NotBlank
        private String nickname;

        private String profileImgUrl;

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

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MemberResponseDto {

        private Long id;
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

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MemberSessionResponseDto implements Serializable {

        private Long id;
        private String email;
        private String password;
        private String name;
        private String nickname;
        private String profileImgUrl;
        private LocalDateTime createdDate;
        private LocalDateTime lastModifiedDate;

        public MemberSessionResponseDto(Member member) {
            id = member.getId();
            email = member.getEmail();
            password = member.getPassword();
            name = member.getName();
            nickname = member.getNickname();
            profileImgUrl = member.getProfileImgUrl();
            createdDate = member.getCreatedDate();
            lastModifiedDate = member.getLastModifiedDate();
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoginForm {

        @NotBlank
        private String email;

        @NotBlank
        private String password;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateForm {

        @NotNull
        private Long userId;

        @NotBlank
        private String name;

        @NotBlank
        private String nickname;

        private String profileUrl;

        public UpdateForm(Long userId) {
            this.userId = userId;
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PasswordForm {

        @NotNull
        private Long userId;

        @NotBlank
        private String originalPw;

        @NotBlank
        private String newPw;

        public PasswordForm(Long userId) {
            this.userId = userId;
        }
    }
}
