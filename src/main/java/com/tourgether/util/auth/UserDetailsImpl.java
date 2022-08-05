package com.tourgether.util.auth;

import com.tourgether.domain.member.model.entity.Member;
import com.tourgether.domain.member.model.entity.auth.MemberAuthority;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
public class UserDetailsImpl implements UserDetails {

    private final Long id;
    private final Set<MemberAuthority> authorities;
    private final String oauth2Id;
    private final String email;
    private final String password;
    private final String name;
    private final String nickname;
    private final String profileImgUrl;

    public UserDetailsImpl(Member member) {
        id = member.getId();
        authorities = member.getAuthorities();
        oauth2Id = member.getOAuth2Id();
        email = member.getEmail();
        password = member.getPassword();
        name = member.getName();
        nickname = member.getNickname();
        profileImgUrl = member.getProfileImgUrl();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        List<String> roles = this.authorities.stream()
                .map(memberAuthority -> memberAuthority.getAuthority().getAuthorityName())
                .toList();

        for (String role : roles) {
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return authorities;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
