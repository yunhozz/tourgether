package com.tourgether.domain.recruit.service.dto.request;

import com.tourgether.domain.member.model.entity.Member;
import com.tourgether.domain.recruit.model.entity.Recruit;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
public class RecruitRequestDto {

    @Setter
    private Member writer;

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    public RecruitRequestDto(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public Recruit toEntity() {
        return Recruit.builder()
                .writer(writer)
                .title(title)
                .content(content)
                .view(0)
                .build();
    }
}
