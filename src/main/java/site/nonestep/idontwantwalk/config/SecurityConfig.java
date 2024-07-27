package site.nonestep.idontwantwalk.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.DefaultLoginPageConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.config.annotation.web.configurers.RequestCacheConfigurer;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestRedirectFilter;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import site.nonestep.idontwantwalk.auth.jwt.JsonWebTokenCheckFilter;
import site.nonestep.idontwantwalk.auth.jwt.JsonWebTokenProvider;
import site.nonestep.idontwantwalk.auth.oauth.CustomOAuth2Provider;
import site.nonestep.idontwantwalk.auth.preferences.*;
import site.nonestep.idontwantwalk.member.repository.MemberRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static site.nonestep.idontwantwalk.auth.Constants.*;

@Configuration
@EnableWebSecurity(debug = false)
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthConfig authConfig;
    private final MemberRepository memberRepository;


    private final JsonWebTokenProvider jsonWebTokenProvider;

    @Bean
    public AntPathMatcher antPathMatcher() {
        return new AntPathMatcher();
    }
    // (0)[Constants] SECURITY_WEB_EXCLUDE_URIS에 설정한 url들은 스프링 시큐리티를 적용하지 않는다.
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                .requestMatchers(SECURITY_HTTP_EXCLUDE_URIS);
    }

    //(1) CORS 세팅 부분 (CORS_HEADER_URIS에서 세팅한 값들은 CORS를 허용한다)
    // 백, 프론트 모두 구현이 완료 된 뒤, 한 서버에 동일하게 올라가면 풀어둔 cors 설정을 해제한 것을 재설정한다.(지금 풀어뒀음)
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // configuration.setAllowedOrigins(Arrays.asList는 한 줄 추가 더 하면 X
        // 그냥 괄호안에 무제한으로 추가하기. 사실 백엔드 링크는 넣을 필요 X 혹시몰라 일단 넣음
        configuration.setAllowCredentials(true);
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:5137", "https://localhost:5137", "https://nonestep.site", "http://nonestep.site"
                , "https://www.nonestep.site", "http://www.nonestep.site"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD"));
        configuration.setExposedHeaders(Arrays.asList("Authorization", "Sequence", "Nickname", "Image"));
        configuration.setAllowedHeaders(Arrays.asList("X-Requested-With", "Origin", "Content-Type", "Accept",
                "Authorization", "Access-Control-Allow-Credentials", "Access-Control-Allow-Headers", "Access-Control-Allow-Methods",
                "Access-Control-Allow-Origin", "Access-Control-Expose-Headers", "Access-Control-Max-Age",
                "Access-Control-Request-Headers", "Access-Control-Request-Method", "Age", "Allow", "Alternates",
                "Content-Range", "Content-Disposition", "Content-Description"));
//        configuration.setAllowedOriginPatterns(Collections.singletonList("http://localhost:3009"));
        // configuration.setAllowedOrigins(Arrays.asList랑 조금 다름
        configuration.setAllowedOriginPatterns(Arrays.asList("http://localhost:5137", "https://localhost:5137","https://nonestep.site","http://nonestep.site"
                ,"https://www.nonestep.site","http://www.nonestep.site"));
        configuration.setMaxAge(60L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }


    //(2-1) OAuth2[인증키]
    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {

        List<ClientRegistration> registrationList = new ArrayList<>();

        //kakao client registratioCn
        Map<String, Credentials> credentialsMap = authConfig.getCredentials();
        registrationList.add(CustomOAuth2Provider.KAKAO.getBuilder("kakao")
                .clientId(credentialsMap.get("kakao").getId()).build());

        //naver client registration
        registrationList.add(CustomOAuth2Provider.NAVER.getBuilder("naver")
                .clientId(credentialsMap.get("naver").getId())
                .clientSecret(credentialsMap.get("naver").getSecret()).build());

        return new InMemoryClientRegistrationRepository(registrationList);
    }

    //(2-2) OAuth2[인증과정 중의 저장소] 설정  -- 해당 class는 만든 것
    //Cross-Site Request Forgery(CSRF) 공격을 막기 위해서는 쿠키에 저장된 파라미터를 매개변수로 사용하여 인증에 대한 보안을 강화
    @Bean
    public CustomOAuth2CookieAuthorizationRepository<OAuth2AuthorizationRequest> oAuth2CookieAuthorizationRequestRepository() {
        return new CustomOAuth2CookieAuthorizationRepository<>(clientRegistrationRepository());
    }

    //(2-3) OAuth2 설정[OAuth2Login에 성공] -- 해당 class는 만든 것
    //			- CustomOAuth2User라고 각 소셜 로그인에 대한 설정 클래스를 통해, 인증 결과 해석
    //			- DB에 저장안되어 있으면, OAuth2LoginException throw -> CustomOAuth2UserFailureHandler에서 DB에 저장할 것임
    //			- CustomAuthenticatedUser 라는 인증 개체에 유저Sequence와 role 추가해서 보낸다. -> DefaultOAuth2User로 변환하여 필터 거치기.
    @Bean
    public CustomOAuth2UserService customOAuth2UserService() {
        return new CustomOAuth2UserService(memberRepository);
    }

    //(2-4) OAuth2 설정[성공시]  -- 해당 class는 만든 것
    //인증 과정 중의 저장소에서 사용하던 Cookie 삭제 & refresh 토큰 있으면 삭제
    //JWT에 시퀀스, role넣기. 그리고 SECURITY_AFTER_LOGIN로 설정된 URL로 이동
    @Bean
    public CustomOAuth2UserSuccessHandler customOAuth2UserSuccessHandler() {
        return new CustomOAuth2UserSuccessHandler(memberRepository);
    }

    //(2-5) OAuth2 설정[실패시]  -- 해당 class는 만든 것
    //인증 과정 중의 저장소에서 사용하던 Cookie 삭제
    //실패시 동작하기 때문에, 일반 에러면 -> 에러 출력 및 실패
    //				   OAuth2LoginException 에러면 -> 인증 회원가입을 시킨다. (CustomOAuth2UserService에서 DB에 없으면 OAuth2LoginException으로 가도록 설정되어 있기 때문)
    //회원가입까지 무사히 되면 -> JWT에 시퀀스, role넣기. 그리고 SECURITY_AFTER_LOGIN로 설정된 URL로 이동
    @Bean
    public CustomOAuth2UserFailureHandler customOAuth2UserFailureHandler() {
        return new CustomOAuth2UserFailureHandler(memberRepository);
    }


    //(3-1) JWT 설정[필터] (이 class에서 설정한 URL에서만 곳에서 JWT 토큰을 확인할 수 있다)  -- 해당 class는 만든 것
    //여기서는 /login url일 경우만 JWT토큰을 비활성화 했다. 이유는 해당 url을 통해 OAuth2를 진행해야 하기 때문이다.
    //나머지 URL에서 계속 토큰 보내면, 동작.
    // 잘못되거나 만료되면 에러 보내기
    @Bean
    public JsonWebTokenCheckFilter jsonWebTokenCheckFilter() {
        return new JsonWebTokenCheckFilter(antPathMatcher(),jsonWebTokenProvider,memberRepository);
    }

    //(3-2) JWT 설정[인증 에러 처리하기 위함] -- 해당 class는 만든 것
    //인증 시작할 때 마다 해당 부분 실행.
    //시작시에 InsufficientAuthenticationException 오류가 났다는 것은 -> JWT가 없다는 것. JWT없다고 오류 보내기
    @Bean
    public CustomAuthenticationEntryPoint customAuthenticationEntryPoint() {
        return new CustomAuthenticationEntryPoint();
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        //(1) CORS 설정
//        http.cors().configurationSource(corsConfigurationSource());
        http.cors(httpSecurityCorsConfigurer -> corsConfigurationSource());
        //기본 설정 해제와 경로 설정
        // http 부분은 거의 다 풀어놨는데, 뭘 잠궈야하는지 모른다...


        http
                .csrf(AbstractHttpConfigurer::disable)                                                            //Cross Site Request Forgery 설정 해제
                .formLogin(FormLoginConfigurer::disable)                                                        //Login Form 페이지 설정 해제
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))    //세션 정책 : 스프링 시큐리티가 생성하지도 않고 존재해도 사용하지 않음(JWT 같은토큰방식을 쓸때 사용하는 설정 )
                .requestCache(RequestCacheConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                                .requestMatchers(SECURITY_HTTP_EXCLUDE_URIS).permitAll()                            //(0)[Constants] SECURITY_HTTP_EXCLUDE_URIS 권한 허용
//                                .anyRequest().authenticated()                                                            //개발 끝나면 나머지 URL은 권한 필요
                                .anyRequest().permitAll()                                                            //개발 중에는 임시로 모든 요청 허용
                )
        ;

        //로그인 페이지 Disable
        // 다 풀어놨길래 나도 그냥 풀어놨음 이유는 알아보기 ㅎㅎ;;
        http.getConfigurer(DefaultLoginPageConfigurer.class).disable();

        //(2) oauth2 설정
        // normal 로그인을 제외한 소셜로그인 부분에서 oauth2가 작동하도록 구현(소셜 로그인)
        // 예외사항들을 모두 여기서 처리해야하므로 설정했음.
        // 성공시 리다이렉션되어서 로그인 처리 됨.
        http.oauth2Login(oauth2 -> oauth2
                .authorizationEndpoint(authorization -> authorization
                        .baseUri(FIRST_OAUTH2_URL)   //확정                                                                             // 해당 URL로 갈 경우, OAuth 실행 (ex: /user/login -> /user/login/kakao,/user/login/google.. 다 여기로)
                        .authorizationRequestRepository(oAuth2CookieAuthorizationRequestRepository())                                //(2-2) 인가 요청을 시작한 시점부터 인가 요청을 받는 시점까지 인증 데이터를 저장하기 위해 필요한 클래스
                )
                .userInfoEndpoint(userInfoEndpointConfig -> userInfoEndpointConfig.userService(customOAuth2UserService()))        //(2-3) OAuth2 로그인 성공 후 사용자 정보를 가져올 때 설정을 담당.	DB에 없는 로그인이면 우리가 만든 OAuth2LoginException으로 가게 설정.
                .loginProcessingUrl(SECOND_OAUTH2_AFTER_SPRING_LOGIN_URL)                                                    //  해당 주소가 호출되면 시큐리티가 낚아 채서(post로 오는것) 대신 로그인 진행 -> 컨트롤러를 안만들어도 된다.// 각 소셜 로그인에서 만든 폼에서 값을 받아온다. CustomOAuth2Provider 같이 볼 것.
                .clientRegistrationRepository(clientRegistrationRepository())                                                    //(2-1) 인증키 세팅
                .successHandler(customOAuth2UserSuccessHandler())                                                                //(2-4) OAuth2 설정[성공시]
                .failureHandler(customOAuth2UserFailureHandler())                                                                //(2-5) OAuth2 설정[실패시]
        );

        //(3) JWT 설정
        // 로그인 부분에서는 exception 처리를 해서 돌아가게 설정했음
        // 그 외 부분에서는 JWT 토근이 없거나, 잘못됐거나, 기간이 지나면 API가 작동하지 않게끔 코드 구현
        http.addFilterBefore(jsonWebTokenCheckFilter(), OAuth2AuthorizationRequestRedirectFilter.class)                                    //(3-1) 단순한 OAuth2AuthorizationRequestRedirectFilter 이전에 발생하는 request 필터. token 가지고 있는지, 유효한지, 확인 하기 위함
                .exceptionHandling(httpSecurityExceptionHandlingConfigurer -> customAuthenticationEntryPoint());
//                .exceptionHandling().authenticationEntryPoint(customAuthenticationEntryPoint());                                    //(3-2) Spring Security 내에서 전역적으로 사용되는 EntryPoint. authenticate 과정에서 에러가 발생하면 (anonymous user일 경우) ExceptionTranslationFilter에서 넘어옴

        return http.build();
    }
}
