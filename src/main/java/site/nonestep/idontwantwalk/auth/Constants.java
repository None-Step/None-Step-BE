package site.nonestep.idontwantwalk.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import site.nonestep.idontwantwalk.config.AuthConfig;

@Component
public class Constants {

    // 회원가입 전이라 Token값이 없어서 일부러 보안을 풀어둔 링크들 모음
    public static final String[] SECURITY_HTTP_EXCLUDE_URIS = {"/nonestep/member/access-token", "/nonestep/member/signup",
            "/nonestep/member/phone", "/nonestep/member/login/callback/**", "/nonestep/member/idcheck",
            "/nonestep/member/login", "/nonestep/member/idfind", "/nonestep/member/pwfind",
            "/nonestep/member/login/**", "/nonestep/resources/**", "/nonestep/swagger*/**", "favicon.ico",
            "/nonestep/webjars/**", "/nonestep/swagger-ui/**", "/nonestep/subway/**",
            "/nonestep/v3/api-docs/**", "/nonestep/swagger-ui/**", "/nonestep/swagger-resources/**"};

    //Authorization == JWT 사용을 위함
    // 해더에서 허용할 부분 설정
    // CORS =  서버가 다른 origin의 브라우저에게 자신의 자원이 로드될 수 있도록 헤더에 표시해주는 방법
    public static final String[] CORS_HEADER_URIS = {"Authorization", "Refresh", "content-type"};


    public static final String FIRST_OAUTH2_URL = "/member/login";
    public static final String SECOND_OAUTH2_AFTER_SPRING_LOGIN_URL = "/member/login/callback/*";
    public static String SERVER_URL;
    public static String FRONT_REDIRECT_URL;


    @Autowired
    public void setDefaultRedirectUrl(AuthConfig authConfig) {
        FRONT_REDIRECT_URL = authConfig.getRedirectUrl();  //frontend에서 받을 곳
    }

    @Autowired
    public void setServerUrl(AuthConfig authConfig) {
        SERVER_URL = authConfig.getServerUrl();
    }
}
