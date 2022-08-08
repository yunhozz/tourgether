package com.tourgether.domain.recruit.model.entity;

import com.tourgether.domain.TimeEntity;
import com.tourgether.domain.member.model.entity.Member;
import com.tourgether.domain.recruit.model.entity.Recruit;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Apply extends TimeEntity {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recruit_id")
    private Recruit recruit;

    @Column(length = 100)
    private String message;

    public Apply(Member member, Recruit recruit, String message) {
        this.member = member;
        this.recruit = recruit;
        this.message = message;
    }
}
