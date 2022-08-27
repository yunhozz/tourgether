package com.tourgether.domain.recruit.model.entity;

import com.tourgether.domain.BaseTime;
import com.tourgether.domain.member.model.entity.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseTime {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recruit_id")
    private Recruit recruit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_id")
    private Member writer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Comment parent; // self join

    @OneToMany(mappedBy = "parent", orphanRemoval = true)
    private List<Comment> children = new ArrayList<>();

    @Column(length = 500)
    private String content;

    private Comment(Member writer, String content) {
        this.writer = writer;
        this.content = content;
    }

    public static Comment createComment(Recruit recruit, Member writer, String content) {
        Comment parent = new Comment(writer, content);
        parent.setRecruit(recruit);

        return parent;
    }

    public static Comment createCommentChild(Recruit recruit, Member writer, Comment parent, String content) {
        Comment child = new Comment(writer, content);
        child.setRecruit(recruit);
        child.setParent(parent);

        return child;
    }

    public void update(String content) {
        this.content = content;
    }

    // 연관관계 메소드
    private void setRecruit(Recruit recruit) {
        this.recruit = recruit;
        recruit.getComments().add(this);
    }

    // 연관관계 메소드
    private void setParent(Comment parent) {
        this.parent = parent;
        parent.getChildren().add(this);
    }
}
