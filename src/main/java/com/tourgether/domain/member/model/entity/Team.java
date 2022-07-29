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

    @Column(columnDefinition = "tinyint")
    private int numOfMember;

    public Team(String name) {
        this.name = name;
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
