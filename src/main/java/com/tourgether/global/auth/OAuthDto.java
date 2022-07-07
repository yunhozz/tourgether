package com.tourgether.global.auth;

import com.tourgether.domain.member.Member;
import com.tourgether.global.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
@Builder
@AllArgsConstructor
public class OAuthDto {

    private String name;
    private String email;
    private String profileImgUrl;
    private String nameAttributeKey;
    private Map<String, Object> attributes;

    public OAuthDto(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
        if (registrationId.equals("kakao")) {
            ofKakao(userNameAttributeName, attributes); // kakao
        }
        if (registrationId.equals("naver")) {
            ofNaver(userNameAttributeName, attributes); // naver
        }
        if (registrationId.equals("google")) {
            ofGoogle(userNameAttributeName, attributes); // google
        }
    }

    public static OAuthDto ofKakao(String userNameAttributeName, Map<String, Object> attributes) {
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> kakaoProfile = (Map<String, Object>) kakaoAccount.get("profile");

        return OAuthDto.builder()
                .name((String) kakaoProfile.get("nickname"))
                .email((String) kakaoAccount.get("email"))
                .profileImgUrl((String) kakaoProfile.get("profile_image_url"))
                .nameAttributeKey(userNameAttributeName)
                .attributes(attributes)
                .build();
    }

    public static OAuthDto ofNaver(String userNameAttributeName, Map<String, Object> attributes) {
        Map<String, Object> naverAccount = (Map<String, Object>) attributes.get("naver_account");
        Map<String, Object> naverProfile = (Map<String, Object>) naverAccount.get("profile");

        return OAuthDto.builder()
                .name((String) naverProfile.get("nickname"))
                .email((String) naverAccount.get("email"))
                .profileImgUrl((String) naverProfile.get("profile_image_url"))
                .nameAttributeKey(userNameAttributeName)
                .attributes(attributes)
                .build();
    }

    public static OAuthDto ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
        Map<String, Object> googleAccount = (Map<String, Object>) attributes.get("google_account");
        Map<String, Object> googleProfile = (Map<String, Object>) googleAccount.get("profile");

        return OAuthDto.builder()
                .name((String) googleProfile.get("nickname"))
                .email((String) googleAccount.get("email"))
                .profileImgUrl((String) googleProfile.get("profile_image_url"))
                .nameAttributeKey(userNameAttributeName)
                .attributes(attributes)
                .build();
    }

    public Member toEntity() {
        return Member.builder()
                .email(email)
                .name(name)
                .profileImgUrl(profileImgUrl)
                .role(Role.USER)
                .build();
    }
}
