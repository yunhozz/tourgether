package com.tourgether.domain.member.model.entity;

import com.tourgether.enums.Position;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TeamMember {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    @Enumerated(EnumType.STRING)
    private Position position; // POSITION_LEADER, POSITION_MEMBER

    public TeamMember(Member member, Team team, Position position) {
        this.member = member;
        this.team = team;
        this.position = position;
    }
}
