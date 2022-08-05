package com.tourgether.domain.recruit.service.dto.request;

import com.tourgether.domain.member.model.entity.Member;
import com.tourgether.domain.recruit.model.entity.Recruit;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class RecruitRequestDto {

    @NotBlank
    private Member writer;

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    private int view;

    @Builder
    private RecruitRequestDto(Member writer, String title, String content, int view) {
        this.writer = writer;
        this.title = title;
        this.content = content;
        this.view = view;
    }

    public Recruit toEntity() {
        return Recruit.builder()
                .writer(writer)
                .title(title)
                .content(content)
                .view(view)
                .build();
    }
}
