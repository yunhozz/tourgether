package com.tourgether.domain.recruit.model.entity;

import com.tourgether.domain.TimeEntity;
import com.tourgether.domain.member.model.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Bookmark extends TimeEntity {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recruit_id")
    private Recruit recruit;

    public Bookmark(Member member, Recruit recruit) {
        this.member = member;
        this.recruit = recruit;
    }

    public void deleteRecruit() {
        recruit = null;
    }
}
