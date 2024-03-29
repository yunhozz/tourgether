package com.tourgether.domain.member.service;

import com.tourgether.domain.member.model.entity.Member;
import com.tourgether.domain.member.model.repository.MemberRepository;
import com.tourgether.util.auth.OAuthDto;
import com.tourgether.util.SessionConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.stream.Collectors;

import static com.tourgether.dto.MemberDto.*;

@Service
@Transactional
@RequiredArgsConstructor
public class OAuth2UserServiceImpl implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final MemberRepository memberRepository;
    private final HttpSession session;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);
        String registrationId = userRequest.getClientRegistration().getRegistrationId(); // OAuth2 서비스 id (kakao, naver, google)
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName(); // OAuth2 로그인 진행 시 키가 되는 필드 값(PK)

        //OAuth2UserService
        OAuthDto oAuthDto = OAuthDto.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());
        Member member = saveOrUpdate(oAuthDto);
        session.setAttribute(SessionConstants.LOGIN_MEMBER, new MemberSessionResponseDto(member));
        String auth = member.getAuthorities().stream()
                .map(memberAuthority -> memberAuthority.getAuthority().getAuthorityName())
                .collect(Collectors.joining(","));

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(auth)),
                oAuthDto.getAttributes(),
                oAuthDto.getNameAttributeKey()
        );
    }

    private Member saveOrUpdate(OAuthDto attributes) {
        Member member = memberRepository.findByEmail(attributes.getEmail())
                .map(m -> m.update(attributes.getName(), attributes.getProfileImgUrl()))
                .orElse(attributes.toEntity());

        return memberRepository.save(member);
    }
}
