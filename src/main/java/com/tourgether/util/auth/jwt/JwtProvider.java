package com.tourgether.util.auth.jwt;

import com.tourgether.domain.member.model.entity.auth.MemberAuthority;
import com.tourgether.domain.member.service.UserDetailsServiceImpl;
import com.tourgether.util.auth.UserDetailsImpl;
import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.Base64UrlCodec;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.tourgether.dto.TokenDto.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtProvider {

    @Value("spring.jwt.secret")
    private String secretKey;
    private final String ROLES = "roles";
    private final Long ACCESSTOKEN_VALID_MILLISECOND = 60 * 60 * 1000L; // 1 hour
    private final Long REFRESHTOKEN_VALID_MILLISECOND = 14 * 24 * 60 * 60 * 1000L; // 14 day
    private final UserDetailsServiceImpl userDetailsService;

    @PostConstruct
    protected void init() {
        secretKey = Base64UrlCodec.BASE64URL.encode(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    // jwt 생성
    public TokenResponseDto createTokenDto(Long userId, Set<MemberAuthority> authorities) {
        List<String> roles = authorities.stream()
                .map(memberAuthority -> memberAuthority.getAuthority().getAuthorityName())
                .collect(Collectors.toList());
        Claims claims = Jwts.claims().setSubject(String.valueOf(userId));
        claims.put(ROLES, roles);
        Date now = new Date();

        String accessToken = Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + ACCESSTOKEN_VALID_MILLISECOND))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();

        String refreshToken = Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setExpiration(new Date(now.getTime() + REFRESHTOKEN_VALID_MILLISECOND))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();

        return TokenResponseDto.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .accessTokenExpireDate(ACCESSTOKEN_VALID_MILLISECOND)
                .build();
    }

    // jwt로 인증정보 조회
    public Authentication getAuthentication(String token) {
        Claims claims;
        // jwt 토큰 복호화해서 가져오기
        try {
            claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            claims = e.getClaims();
        }
        // 권한 정보가 없는 경우
//        if (claims.get(ROLES) == null) {
//            throw new CAuthenticationEntryPointException();
//        }
        log.info("userId: {}", claims.getSubject());
        UserDetailsImpl userDetails = userDetailsService.loadUserByUsername(claims.getSubject());

        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // 헤더에서 토큰 가져오기
    public String resolveToken(HttpServletRequest request) {
        return request.getHeader("X-AUTH-TOKEN");
    }

    public boolean validationToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.error("잘못된 Jwt 서명입니다.");
        } catch (ExpiredJwtException e) {
            log.error("만료된 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            log.error("지원하지 않는 토큰입니다.");
        } catch (IllegalArgumentException e) {
            log.error("잘못된 토큰입니다.");
        }
        return false;
    }
}
