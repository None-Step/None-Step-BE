package site.nonestep.idontwantwalk.auth.preferences;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.transaction.annotation.Transactional;
import site.nonestep.idontwantwalk.auth.jwt.CustomAuthenticatedUser;
import site.nonestep.idontwantwalk.auth.jwt.JsonWebToken;
import site.nonestep.idontwantwalk.auth.util.CookieUtils;
import site.nonestep.idontwantwalk.auth.util.JwtTokenUtils;
import site.nonestep.idontwantwalk.member.entity.Member;
import site.nonestep.idontwantwalk.member.repository.MemberRepository;

import java.io.IOException;

import static site.nonestep.idontwantwalk.auth.preferences.CustomOAuth2CookieAuthorizationRepository.OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME;
import static site.nonestep.idontwantwalk.auth.preferences.CustomOAuth2CookieAuthorizationRepository.REDIRECT_URI_PARAM_COOKIE_NAME;
import static site.nonestep.idontwantwalk.auth.util.JwtTokenUtils.ACCESS_PERIOD;
import static site.nonestep.idontwantwalk.auth.util.JwtTokenUtils.REFRESH_PERIOD;

@RequiredArgsConstructor
@Transactional
public class CustomOAuth2UserSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final MemberRepository memberRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException, IOException {

        //쿠키에서 기존에 저장된 refresh token을 삭제한다.
        if (CookieUtils.getCookie(request, "refresh-token") != null) {
            CookieUtils.deleteCookie(request, response, "refresh-token");
        }

        //authentication 객체에서 attribute를 추출하고, CustomAuthenticatedUser를 생성한다.
        CustomAuthenticatedUser customAuthenticatedUser = CustomAuthenticatedUser.mapToObj(((DefaultOAuth2User) authentication.getPrincipal()).getAttributes());
        Long userSeq = customAuthenticatedUser.getUserSequence();

        //jwt 토큰을 생성한다.
        JsonWebToken jsonWebToken =  JwtTokenUtils.allocateToken(userSeq, customAuthenticatedUser.getRole());

        Member member = memberRepository.getReferenceById(userSeq);
        member.changeToken(jsonWebToken.getRefreshToken());

        //cookie에서 redirectUrl을 추출하고, redirect 주소를 생성한다.
        String baseUrl = CookieUtils.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME).getValue();;
//        String url = UriComponentsBuilder.fromUriString(baseUrl).queryParam("token", jwtTokenInfo.getAccessToken()).build().toUriString();

        //쿠키를 삭제한다.
        CookieUtils.deleteCookie(request, response, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME);
        CookieUtils.deleteCookie(request, response, REDIRECT_URI_PARAM_COOKIE_NAME);

        //프론트에 전달할 쿠키
//        Cookie acessCookie = new Cookie("Access", jsonWebToken.getAccessToken());
//        acessCookie.setMaxAge((int) ACCESS_PERIOD);
//        acessCookie.setPath("/");
//        response.addCookie(acessCookie);

        ResponseCookie cookie = ResponseCookie.from("Refresh",jsonWebToken.getRefreshToken())
                .sameSite("None")
                .secure(true)
                .path("/")
                .maxAge(REFRESH_PERIOD)
                .build();
        response.addHeader("Set-Cookie",cookie.toString());
        response.addHeader("Authorization",jsonWebToken.getAccessToken());

        request.getSession().setMaxInactiveInterval(180); //second
        // 헤더에 넣을 부분. F12 - Network - Response Header의 데이터를 임의로 만든 것. 오른쪽에 토근값을 전달한다.
        request.getSession().setAttribute("Authorization",jsonWebToken.getAccessToken());
        request.getSession().setAttribute("Sequence",userSeq);
        //리다이렉트 시킨다.
        getRedirectStrategy().sendRedirect(request, response, baseUrl);
    }
}
