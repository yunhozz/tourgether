package com.tourgether.domain.member.model.entity;

import com.tourgether.domain.BaseTime;
import com.tourgether.domain.recruit.model.entity.Recruit;
import com.tourgether.enums.Position;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecruitMember extends BaseTime {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recruit_id")
    private Recruit recruit;

    @Enumerated(EnumType.STRING)
    private Position position; // LEADER, MEMBER

    public RecruitMember(Member member, Recruit recruit, Position position) {
        this.member = member;
        this.recruit = recruit;
        this.position = position;
    }
}
