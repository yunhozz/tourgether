package com.tourgether.domain.member.model;

import com.tourgether.domain.TimeEntity;
import com.tourgether.enums.Role;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends TimeEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String oAuth2Id;

    @Column(length = 50, unique = true)
    private String email;

    private String password;

    @Column(length = 20)
    private String name;

    @Column(length = 20, unique = true)
    private String nickname;

    private String profileImgUrl;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder
    private Member(String oAuth2Id, String email, String password, String name, String nickname, String profileImgUrl, Role role) {
        this.oAuth2Id = oAuth2Id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.profileImgUrl = profileImgUrl;
        this.role = role;
    }

    public Member update(String name, String profileImgUrl) {
        this.name = name;
        this.profileImgUrl = profileImgUrl;

        return this;
    }

    // 비밀번호 수정
    public void updatePassword(String password) {
        this.password = password;
    }

    // 이름, 닉네임, 프로필 사진 수정
    public void updateInfo(String name, String nickname, String profileImgUrl) {
        this.name = name;
        this.nickname = nickname;
        this.profileImgUrl = profileImgUrl;
    }
}
