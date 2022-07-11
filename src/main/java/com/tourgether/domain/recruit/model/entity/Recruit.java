package com.tourgether.domain.recruit.model.entity;

import com.tourgether.domain.TimeEntity;
import com.tourgether.domain.member.model.entity.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Recruit extends TimeEntity {

    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(mappedBy = "recruit", orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_id")
    private Member writer;

    private String title;

    @Column(length = 2000)
    private String content;

    @Builder
    private Recruit(Member writer, String title, String content) {
        this.writer = writer;
        this.title = title;
        this.content = content;
    }
}
