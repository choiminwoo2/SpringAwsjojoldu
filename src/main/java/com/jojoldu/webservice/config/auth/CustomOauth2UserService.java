package com.jojoldu.webservice.config.auth;

import com.jojoldu.webservice.config.auth.dto.OAuthAttributes;
import com.jojoldu.webservice.config.auth.dto.SessionUser;
import com.jojoldu.webservice.domain.user.User;
import com.jojoldu.webservice.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
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

@RequiredArgsConstructor
@Service
public class CustomOauth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User>
                delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User= delegate.loadUser(userRequest);

        //로그인 중인 서비스의 레지스터 번호를 구분하는 코드
        String registrationId = userRequest.
                getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.
                getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint()
                    .getUserNameAttributeName();
        // OAuthAttributes DTO를 이용하여 Ouath 유저정보를 담는다.
        OAuthAttributes attributes = OAuthAttributes.
                of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        //유저를 확인하고 이메일이 있다면 업데이트 한다. 그것이 아니라면
        // 새로운 유저 데이터를 엔티티에 저장한다
        User user = saveOrUpdate(attributes);
        httpSession.setAttribute("user", new SessionUser(user));

        return new DefaultOAuth2User(
                Collections.singleton(new
                        SimpleGrantedAuthority(user.getRoleKey())),
                        attributes.getAttributes(),
                        attributes.getNameAttributeKey()
        );


    }
    private User saveOrUpdate(OAuthAttributes attributes){
        User user = userRepository.findByEmail(attributes.getEmail())
                .map(entity -> entity.update(attributes.getName(), attributes.getPicture()))
                .orElse(attributes.toEntity());

        return userRepository.save(user);
    }
}
