package com.tourgether.domain.member.model.entity;

import com.tourgether.domain.TimeEntity;
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

    @Column(length = 10)
    private String name;

    @Column(length = 1000)
    private String introduction;

    private String propensities; // "propensityA,propensityB,propensityC ..."

    private Team(String name, String introduction) {
        this.name = name;
        this.introduction = introduction;
    }

    public static Team createTeam(String name, String introduction, String... propensities) {
        Team team = new Team(name, introduction);
        team.updatePropensity(propensities);

        return team;
    }

    // 정보 변경
    public void update(String name, String introduction) {
        this.name = name;
        this.introduction = introduction;
    }
}
