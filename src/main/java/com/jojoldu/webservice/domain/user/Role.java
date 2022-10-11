package com.jojoldu.webservice.domain.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

//ENUM ROLE 주는 enum!.
// 유의점 : String으로 해야 DB가 변경되어도 문제가 없을 가능성이 높다!.
@Getter
@RequiredArgsConstructor
public enum Role {

    GUEST("ROLE_GUEST", "손님"),
    USER("ROLE_USER", "일반 사용자");

    private final String key;
    private final String title;
}
