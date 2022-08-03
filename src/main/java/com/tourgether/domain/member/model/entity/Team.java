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

    public Team(String name, String introduction) {
        this.name = name;
        this.introduction = introduction;
    }

    // 정보 변경
    public void update(String name, String introduction) {
        this.name = name;
        this.introduction = introduction;
    }
}
