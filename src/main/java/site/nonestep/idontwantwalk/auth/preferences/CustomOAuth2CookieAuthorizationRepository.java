package site.nonestep.idontwantwalk.auth.preferences;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.util.Assert;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import site.nonestep.idontwantwalk.auth.util.CookieUtils;
import site.nonestep.idontwantwalk.config.AuthConfig;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static site.nonestep.idontwantwalk.auth.Constants.FRONT_REDIRECT_URL;
public class CustomOAuth2CookieAuthorizationRepository<T extends OAuth2AuthorizationRequest> implements AuthorizationRequestRepository {

    public static final String OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME = "oauth2_auth_request";

    public static final String REDIRECT_URI_PARAM_COOKIE_NAME = "redirect_uri";

    private static final char PATH_DELIMITER = '/';

    private static final int cookieExpireSeconds = 180;

    private final ClientRegistrationRepository clientRegistrationRepository;

    @Autowired
    private AuthConfig authConfig;

    public CustomOAuth2CookieAuthorizationRepository(ClientRegistrationRepository clientRegistrationRepository) {
        this.clientRegistrationRepository = clientRegistrationRepository;
    }

    // 인증 쿠키 저장
    @Override
    public void saveAuthorizationRequest(OAuth2AuthorizationRequest authorizationRequest, HttpServletRequest request, HttpServletResponse response) {
        CookieUtils.addCookie(response, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME,
                CookieUtils.serialize(authorizationRequest), cookieExpireSeconds);
//        CookieUtils.addCookie(response, REDIRECT_URI_PARAM_COOKIE_NAME, authConfig.getRedirectUrl(), cookieExpireSeconds);
        CookieUtils.addCookie(response, REDIRECT_URI_PARAM_COOKIE_NAME, FRONT_REDIRECT_URL, cookieExpireSeconds);
    }



    // 인증 쿠키 조회
    @Override
    public OAuth2AuthorizationRequest loadAuthorizationRequest(HttpServletRequest request) {
        return Optional.ofNullable(
                        CookieUtils.getCookie(request, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME))
                .map(cookie -> CookieUtils.deserialize(cookie, OAuth2AuthorizationRequest.class))
                .orElse(null);
    }


    //쿠키의 authorizationRequest 삭제
    //쿠키에 AuthorizationRequest가 없는 상태에서 loginFilter가 이 메소드를 호출할 수 있으므로
    //request에서 적절한 AuthorizationRequest를 생성하여 반환해줘야 함.
    //AuthorizationRequest 생성 로직은 RequestResolver를 그대로 따라간다.
    @Override
    public OAuth2AuthorizationRequest removeAuthorizationRequest(HttpServletRequest request, HttpServletResponse response) {
        Assert.notNull(request, "request cannot be null");

        OAuth2AuthorizationRequest originalRequest = this.loadAuthorizationRequest(request);

//        log.info("{}", request.getRequestURI().split("/"));
        // 만약 쿠키에 original request가 없을 경우 카카오의 client registration에 맞는 authorizationRequest 생성
        // following logic is based on DefaultOAuth2AuthorizationRequestResolver.resolve()
        if (originalRequest == null) {
            String requestUri = request.getRequestURI();
            String registrationId = requestUri.substring(requestUri.lastIndexOf("/") + 1, requestUri.length());
            ClientRegistration clientRegistration = clientRegistrationRepository.findByRegistrationId(registrationId);
            if (clientRegistration == null) {
                throw new IllegalArgumentException("Invalid Client Registration with Id: " + registrationId);
            }

            OAuth2AuthorizationRequest.Builder builder = OAuth2AuthorizationRequest.authorizationCode()
                    .attributes((attrs) ->
                            attrs.put(OAuth2ParameterNames.REGISTRATION_ID,
                                    clientRegistration.getRegistrationId()));

            String redirectUriStr = expandRedirectUri(request, clientRegistration);

            builder.clientId(clientRegistration.getClientId())
                    .authorizationUri(clientRegistration.getProviderDetails().getAuthorizationUri())
                    .redirectUri(redirectUriStr)
                    .scopes(clientRegistration.getScopes())
                    .state(request.getParameter("state"));

            originalRequest = builder.build();
        }

        return originalRequest;
    }


    //우리는 request에서 기본 주소를 뽑고,
    //redirect uri와 조합해서 만들어낼거임
    private static String expandRedirectUri(HttpServletRequest request, ClientRegistration clientRegistration) {
        Map<String, String> uriVariables = new HashMap<>();

        uriVariables.put("registrationId", clientRegistration.getRegistrationId());

        UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(
                        UrlUtils.buildFullRequestUrl(request))
                .replacePath(request.getContextPath())
                .replaceQuery(null)
                .fragment(null)
                .build();

        String path = uriComponents.getPath();
        if (org.springframework.util.StringUtils.hasLength(path)) {
            if (path.charAt(0) != PATH_DELIMITER) {
                path = PATH_DELIMITER + path;
            }
        }
        uriVariables.put("basePath", (path != null) ? path : "");
        uriVariables.put("baseUrl", uriComponents.toUriString());

        return UriComponentsBuilder.fromUriString(clientRegistration.getRedirectUri())
                .buildAndExpand(uriVariables)
                .toUriString();
    }
}
