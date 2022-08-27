package com.tourgether.domain.recruit.model.entity;

import com.tourgether.domain.BaseTime;
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
public class Recruit extends BaseTime {

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

    private int view;

    @Builder
    private Recruit(Member writer, String title, String content, int view) {
        this.writer = writer;
        this.title = title;
        this.content = content;
        this.view = view;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
