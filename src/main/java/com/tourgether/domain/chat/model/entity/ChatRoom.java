package com.tourgether.domain.chat.model.entity;

import com.tourgether.domain.TimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatRoom extends TimeEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 50)
    private String title;

    public ChatRoom(String title) {
        this.title = title;
    }
}
