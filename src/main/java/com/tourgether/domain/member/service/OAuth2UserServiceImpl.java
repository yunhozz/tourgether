package com.tourgether.domain.member.service;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.tourgether.domain.member.model.Member;
import com.tourgether.domain.member.model.repository.MemberRepository;
import com.tourgether.ui.auth.OAuthDto;
import com.tourgether.dto.MemberSessionResponseDto;
import com.tourgether.ui.SessionConstants;
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
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;

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

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(member.getRole().getKey())),
                oAuthDto.getAttributes(),
                oAuthDto.getNameAttributeKey()
        );
    }

    public String getKakaoAccessToken(String code) throws IOException {
        String accessToken = "";
        String refreshToken = "";
        String reqURL = "https://kauth.kakao.com/oauth/token";

        try{
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // POST 요청을 위해 기본값이 false 인 setDoOutput 을 true 로
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            // POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            StringBuilder sb = new StringBuilder();
            sb.append("grant_type=authorization_code");
            sb.append("&client_id=fadeb1cea8077be1a20d8cc98139a990"); // TODO REST_API_KEY 입력
            sb.append("&redirect_uri=http://localhost:9000/app/users/kakao"); // TODO 인가코드 받은 redirect_uri 입력
            sb.append("&code=").append(code);
            bw.write(sb.toString());
            bw.flush();

            // 결과 코드가 200이라면 성공
            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);
            // 요청을 통해 얻은 JSON 타입의 Response 메세지 읽어오기
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println("response body : " + result);

            // Gson 라이브러리에 포함된 클래스로 JSON파싱 객체 생성
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);
            accessToken = element.getAsJsonObject().get("access_token").getAsString();
            refreshToken = element.getAsJsonObject().get("refresh_token").getAsString();

            System.out.println("access_token : " + accessToken);
            System.out.println("refresh_token : " + refreshToken);
            br.close();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return accessToken;
    }

    public void createKakaoUser(String token) throws IOException {
        String reqURL = "https://kapi.kakao.com/v2/user/me";

        // access_token 을 이용하여 사용자 정보 조회
        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Authorization", "Bearer " + token); // 전송할 header 작성, access_token 전송

            // 결과 코드가 200이라면 성공
            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);

            // 요청을 통해 얻은 JSON 타입의 Response 메세지 읽어오기
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder result = new StringBuilder();
            String line = "";

            while ((line = br.readLine()) != null) {
                result.append(line);
            }
            System.out.println("response body : " + result);

            // Gson 라이브러리로 JSON 파싱
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result.toString());
            int id = element.getAsJsonObject().get("id").getAsInt();
            boolean hasEmail = element.getAsJsonObject().get("kakao_account").getAsJsonObject().get("has_email").getAsBoolean();
            String email = "";

            if(hasEmail){
                email = element.getAsJsonObject().get("kakao_account").getAsJsonObject().get("email").getAsString();
            }
            System.out.println("id : " + id);
            System.out.println("email : " + email);
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Member saveOrUpdate(OAuthDto attributes) {
        Member member = memberRepository.findByEmail(attributes.getEmail())
                .map(m -> m.update(attributes.getName(), attributes.getProfileImgUrl()))
                .orElse(attributes.toEntity());

        return memberRepository.save(member);
    }
}
