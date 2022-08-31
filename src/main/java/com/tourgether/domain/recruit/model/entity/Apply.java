package com.tourgether.domain.recruit.model.entity;

import com.tourgether.domain.BaseTime;
import com.tourgether.domain.member.model.entity.Member;
import com.tourgether.enums.ErrorCode;
import com.tourgether.exception.apply.AlreadyDecidedException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Apply extends BaseTime {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recruit_id")
    private Recruit recruit;

    @Column(length = 100)
    private String message;

    @Column(columnDefinition = "tinyint")
    private int status; // 0 : 지원, 1 : 지원 수락, 2: 지원 거절

    public Apply(Member member, Recruit recruit, String message, int status) {
        this.member = member;
        this.recruit = recruit;
        this.message = message;
        this.status = status;
    }

    public void accept() {
        if (status != 0) {
            throw new AlreadyDecidedException(ErrorCode.ALREADY_DECIDED);
        }
        status = 1;
    }
}
