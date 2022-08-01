package com.tourgether.domain.member.model.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Authority {

    @Id
    @Column(length = 50)
    private String authorityName; // ROLE_ADMIN, ROLE_USER

    public Authority(String authorityName) {
        this.authorityName = authorityName;
    }
}
