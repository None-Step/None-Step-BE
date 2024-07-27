package site.nonestep.idontwantwalk.auth.preferences;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import site.nonestep.idontwantwalk.auth.util.JsonUtils;

import java.io.IOException;

//Spring Security 내에서 전역적으로 사용되는 EntryPoint
//authenticate 과정에서 에러가 발생하면 (anonymous user일 경우) ExceptionTranslationFilter에서 넘어옴
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {

        if (authException instanceof InsufficientAuthenticationException) {
            JsonUtils.writeJsonExceptionResponse(response, HttpStatus.BAD_REQUEST,"소셜 로그인 부분 오류");
        }

    }
}
