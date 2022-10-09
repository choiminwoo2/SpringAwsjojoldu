package com.jojoldu.webservice.config.auth;

import com.jojoldu.webservice.config.auth.dto.SessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Component
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {

    private final HttpSession httpSession;


    /**
     *
     * @param parameter
     * @param @LoginUser
     * @LoginUser 어노테이션이 붙어 있으며,
     * SessionUser.class 인 경우 true 반환
     * 컨트롤러 메서드의 특정 파라미터를 지원하는지 확인함.
     *
     *
     * @return boolean
     */
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean isLoginUserAnnotation = parameter.getParameterAnnotation(LoginUser.class) != null;
        boolean isUserClass = SessionUser.class.
                equals(parameter.getParameterType());
        return isLoginUserAnnotation && isUserClass;
    }

    /**
     *
     * @param parameter
     * @param mavContainer
     * @param webRequest
     * @param binderFactory
     * 파라미터에 전달할 객체를 생성한다.
     *
     * @return 세션의 user값을 리턴
     * @throws Exception
     */
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        return  httpSession.getAttribute("user");
    }
}
