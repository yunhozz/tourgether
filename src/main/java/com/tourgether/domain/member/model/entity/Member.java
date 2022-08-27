package com.tourgether.domain.member.model.entity;

import com.tourgether.domain.BaseTime;
import com.tourgether.domain.member.model.entity.auth.MemberAuthority;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTime {

    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(mappedBy = "member")
    private Set<MemberAuthority> authorities = new HashSet<>();

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

    @Builder
    private Member(Set<MemberAuthority> authorities, String oAuth2Id, String email, String password, String name, String nickname, String profileImgUrl) {
        this.authorities = authorities;
        this.oAuth2Id = oAuth2Id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.profileImgUrl = profileImgUrl;
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
