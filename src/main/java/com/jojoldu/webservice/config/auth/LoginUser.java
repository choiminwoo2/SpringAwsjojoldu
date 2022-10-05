package com.jojoldu.webservice.config.auth;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
//메소드의 파라미터로 선언된 객체만 사용이 가능하게 함!
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
/**
 * @interface
 *
 * 어노테이션 클래스로 지정함!.
 * LoginUser라는 어노테이션이 생성 됨!
 */
public @interface LoginUser {
}
