package com.tourgether.domain.member;

import com.tourgether.domain.TimeEntity;
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

    @Column(length = 50, unique = true)
    private String email;

    @Column(length = 20)
    private String password;

    @Column(length = 20)
    private String name;

    @Column(length = 20)
    private String nickname;

    private String profileUrl;

    private String auth; // 쉼표로 구분

    @Builder
    private Member(String email, String password, String name, String nickname, String profileUrl, String auth) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.profileUrl = profileUrl;
        this.auth = auth;
    }

    public void updatePassword(String password) {
        this.password = password;
    }

    public void updateInfo(String name, String nickname, String profileUrl) {
        this.name = name;
        this.nickname = nickname;
        this.profileUrl = profileUrl;
    }
}
