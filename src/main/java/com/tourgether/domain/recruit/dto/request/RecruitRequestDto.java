package com.tourgether.domain.recruit.dto.request;

import com.tourgether.domain.member.model.entity.Member;
import com.tourgether.domain.recruit.model.entity.Recruit;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RecruitRequestDto {

    @NotNull
    private Long writerId;

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    @NotNull
    private Integer view;

    public Recruit toEntity(Member writer) {
        return Recruit.builder()
                .writer(writer)
                .title(title)
                .content(content)
                .view(view)
                .build();
    }
}
