package org.example.springboot.connfig.auth;

import lombok.RequiredArgsConstructor;
import org.example.springboot.connfig.auth.dto.OAuthAttributes;
import org.example.springboot.connfig.auth.dto.SessionUser;
import org.example.springboot.domain.user.User;
import org.example.springboot.domain.user.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;

//구글 로그인 이후 가져온 사용자의 정보를 기반으로 가입 및 정보수정, 세션 저장 등의 기능 지원
@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate =
                new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        //현재 로그인 진행중인 서비스를 구분하는 코드. ex)구글 로그인인지 네이버 로그인인지

        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName();
        //OAuth2 로그인 진행 시 키가 되는 필드값. 구글의 경우 기본적으로 "sub" 코드 지원

        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());
        //OAuth2UserService 를 통해 가져온 OAuth2User 의 attribute 를 담은 클래스

        User user = saveOrUpdate(attributes);
        httpSession.setAttribute("user", new SessionUser(user));
        /*
        세션에 사용자 정보를 저장하기 위한 Dto 클래스
        직렬화 : 자바에서 사용되는 객체 또는 데이터를 다른 컴퓨터의 자바 시스템에서고 사용할 수 있도록
                바이트 스트림 형태로 연속적인 데이터로 변환하는 포맷 변환 기술
        세션에 User 클래스를 저장할 시 에러 -> User 클래스에 직렬화를 구현하지 않았기 때문
        User 클래스(엔티티)에 직렬화 구현 시 성능 이슈, 부수 효과 발생
        따라서 직렬화 기능을 가진 세션 Dto 하나를 추가 생성
        */
        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey()
        );
    }

    private User saveOrUpdate(OAuthAttributes attributes){
        User user = userRepository.findByEmail(attributes.getEmail()) //이메일로 User 찾기
                .map(entity -> entity.update(attributes.getName(), attributes.getPicture())) //있을 경우 항상 update
                .orElse(attributes.toEntity()); //없을 경우 생성
        return userRepository.save(user);
    }
}
