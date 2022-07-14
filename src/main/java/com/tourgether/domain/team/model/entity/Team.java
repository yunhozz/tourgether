package com.tourgether.domain.team.model.entity;

import com.tourgether.domain.TimeEntity;
import com.tourgether.domain.member.model.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Team extends TimeEntity {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(length = 10)
    private String name;

    @Column(columnDefinition = "tinyint")
    private int numOfMember;
}
