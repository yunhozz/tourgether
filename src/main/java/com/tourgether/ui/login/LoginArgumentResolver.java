package com.tourgether.ui.login;

import com.tourgether.dto.MemberSessionResponseDto;
import com.tourgether.ui.SessionConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpSession;

@Component
@RequiredArgsConstructor
public class LoginArgumentResolver implements HandlerMethodArgumentResolver {

    private final HttpSession session;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean isLoginMemberAnnotation = parameter.getParameterAnnotation(LoginMember.class) != null;
        boolean isMemberDto = MemberSessionResponseDto.class.equals(parameter.getParameterType());

        return isLoginMemberAnnotation && isMemberDto;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        return session.getAttribute(SessionConstants.LOGIN_MEMBER);
    }
}
