package com.tourgether.domain.recruit.service;

import com.tourgether.domain.member.model.entity.Member;
import com.tourgether.domain.member.model.entity.RecruitMember;
import com.tourgether.domain.member.model.repository.MemberRepository;
import com.tourgether.domain.member.model.repository.RecruitMemberRepository;
import com.tourgether.domain.notification.service.NotificationService;
import com.tourgether.domain.recruit.model.entity.Apply;
import com.tourgether.domain.recruit.model.entity.Recruit;
import com.tourgether.domain.recruit.model.repository.ApplyRepository;
import com.tourgether.domain.recruit.model.repository.RecruitRepository;
import com.tourgether.enums.ErrorCode;
import com.tourgether.enums.NotificationType;
import com.tourgether.enums.Position;
import com.tourgether.exception.apply.AlreadyApplyException;
import com.tourgether.exception.apply.ApplyNotFoundException;
import com.tourgether.exception.recruit.RecruitNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.tourgether.dto.ApplyDto.*;
import static com.tourgether.dto.NotificationDto.*;

@Service
@Transactional
@RequiredArgsConstructor
public class ApplyService {

    private final ApplyRepository applyRepository;
    private final MemberRepository memberRepository;
    private final RecruitRepository recruitRepository;
    private final RecruitMemberRepository recruitMemberRepository;
    private final NotificationService notificationService;

    public Long makeApply(ApplyRequestDto applyRequestDto, Long userId, Long recruitId) {
        Member member = memberRepository.getReferenceById(userId);
        applyRepository.findByMember(member).forEach(apply -> {
            if (apply.getRecruit().getId().equals(recruitId) && apply.getStatus() == 0) {
                throw new AlreadyApplyException(ErrorCode.ALREADY_APPLY);
            }
        });
        Recruit recruit = recruitRepository.findById(recruitId)
                .orElseThrow(() -> new RecruitNotFoundException(ErrorCode.RECRUIT_NOT_FOUND));
        Apply apply = new Apply(member, recruit, applyRequestDto.getMessage(), 0);

        NotificationRequestDto notificationRequestDto =
                new NotificationRequestDto(member.getNickname() + "님이 " + recruit.getTitle() + "에 지원하셨어요!", NotificationType.OFFER, null);
        notificationService.sendNotification(userId, recruit.getWriter().getId(), notificationRequestDto);

        return applyRepository.save(apply).getId();
    }

    public void applyResponse(Long applyId, boolean check) {
        Apply apply = findApply(applyId);
        NotificationRequestDto notificationRequestDto;

        if (check) {
            apply.accept();
            RecruitMember recruitMember = new RecruitMember(apply.getMember(), apply.getRecruit(), Position.MEMBER);
            recruitMemberRepository.save(recruitMember);
            notificationRequestDto = new NotificationRequestDto(apply.getRecruit().getWriter().getName() + "님이 지원에 수락하셨어요!", NotificationType.RESPONSE, null);
        } else {
            apply.reject();
            notificationRequestDto = new NotificationRequestDto(apply.getRecruit().getWriter().getName() + "님이 지원에 거절하셨어요!", NotificationType.RESPONSE, null);
        }
        notificationService.sendNotification(apply.getRecruit().getWriter().getId(), apply.getMember().getId(), notificationRequestDto);
    }

    @Transactional(readOnly = true)
    public ApplyResponseDto findApplyDto(Long applyId) {
        return new ApplyResponseDto(findApply(applyId));
    }

    @Transactional(readOnly = true)
    public List<ApplyResponseDto> findApplyDtoListByUserId(Long userId) {
        Member member = memberRepository.getReferenceById(userId);
        return applyRepository.findByMember(member).stream()
                .map(ApplyResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    private Apply findApply(Long applyId) {
        return applyRepository.findById(applyId)
                .orElseThrow(() -> new ApplyNotFoundException(ErrorCode.APPLY_NOT_FOUND));
    }
}
