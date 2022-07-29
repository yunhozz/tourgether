package com.tourgether.domain.member.model.entity;

import com.tourgether.domain.TimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Team extends TimeEntity {

    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(mappedBy = "team")
    private List<Member> members = new ArrayList<>();

    @Column(length = 10)
    private String name;

    @Column(length = 1000)
    private String introduction;

    @Column(columnDefinition = "tinyint")
    private int numOfMember;

    public Team(String name, String introduction, int numOfMember) {
        this.name = name;
        this.introduction = introduction;
        this.numOfMember = numOfMember;
    }

    public static Team createTeam(Member member, String name, String introduction) {
        Team team = new Team(name, introduction, 1);
        member.updateTeam(team);

        return team;
    }

    public void increaseNum() {
        numOfMember++;
    }

    public void decreaseNum() {
        if (numOfMember == 0) {
            throw new IllegalStateException("Number of people cannot be less than zero");
        }
        numOfMember--;
    }
}
