package com.tourgether.util.auth;

import com.tourgether.domain.member.model.entity.Member;
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

    public static OAuthDto of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
        if (registrationId.equals("kakao")) {
            return ofKakao(userNameAttributeName, attributes); // kakao
        }
        if (registrationId.equals("naver")) {
            return ofNaver(userNameAttributeName, attributes); // naver
        }
        if (registrationId.equals("google")) {
            return ofGoogle(userNameAttributeName, attributes); // google
        }
        return null;
    }

    private static OAuthDto ofKakao(String userNameAttributeName, Map<String, Object> attributes) {
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

    private static OAuthDto ofNaver(String userNameAttributeName, Map<String, Object> attributes) {
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

    private static OAuthDto ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
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
                .build();
    }
}
