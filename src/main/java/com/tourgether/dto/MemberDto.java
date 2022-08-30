package com.tourgether.dto;

import com.tourgether.domain.member.model.entity.Member;
import com.tourgether.domain.member.model.entity.RecruitMember;
import com.tourgether.enums.Position;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;

public class MemberDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MemberRequestDto {

        @Email(message = "이메일을 입력해주세요.")
        private String email;

        @NotBlank(message = "비밀번호를 입력해주세요.")
        private String password;

        @NotBlank(message = "이름을 입력해주세요.")
        private String name;

        @NotBlank(message = "닉네임을 입력해주세요.")
        private String nickname;

        private String profileImgUrl;
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
    public static class RecruitMemberResponseDto {

        private Long id;
        private Long userId;
        private Long recruitId;
        private Position position;
        private LocalDateTime createdDate;
        private LocalDateTime lastModifiedDate;

        public RecruitMemberResponseDto(RecruitMember recruitMember) {
            id = recruitMember.getId();
            userId = recruitMember.getMember().getId();
            recruitId = recruitMember.getRecruit().getId();
            position = recruitMember.getPosition();
            createdDate = recruitMember.getCreatedDate();
            lastModifiedDate = recruitMember.getLastModifiedDate();
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoginForm {

        @Email(message = "이메일을 입력해주세요.")
        private String email;

        @NotBlank(message = "비밀번호를 입력해주세요.")
        private String password;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateForm {

        @NotBlank(message = "이름을 입력해주세요.")
        private String name;

        @NotBlank(message = "닉네임을 입력해주세요.")
        private String nickname;

        private String profileUrl;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PasswordForm {

        @NotBlank(message = "기존 비밀번호를 입력해주세요.")
        private String originalPw;

        @NotBlank(message = "변경하실 비밀번호를 입력해주세요.")
        private String newPw;
    }
}
