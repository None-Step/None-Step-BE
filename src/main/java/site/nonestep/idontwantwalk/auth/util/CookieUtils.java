package site.nonestep.idontwantwalk.auth.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.util.SerializationUtils;

import java.util.Arrays;
import java.util.Base64;
import java.util.Optional;

public class CookieUtils {

    public static Cookie getCookie(HttpServletRequest request, String cookieName) {
        try {
            return Arrays.stream(request.getCookies()).filter(cookie -> cookie.getName().equals(cookieName)).findFirst().orElseThrow(NullPointerException::new);
        } catch (NullPointerException e) {
            return null;
        }
    }

    // 소셜 로그인 과정 중에서, 리다이렉트 되면서 데이터를 보낼 때 URL에 담아서 보내면 보안상에 이유가 있기 때문에 존재하는 코드
    // 이 addCookie는 소셜 로그인이 진행되는 과정 중에서만 잠깐 쓰이고 바로 삭제된다.
    // 로그인 시 쿠키 관련과는 연관X
    public static void addCookie(HttpServletResponse response, String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setSecure(false); //서버에 올리면 true 하자. https 관련
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }

    public static void deleteCookie(HttpServletRequest request, HttpServletResponse response, String name) {
        Optional<Cookie> cookie = Arrays.stream(request.getCookies()).filter(c -> c.getName().equals(name)).findFirst();
        cookie.ifPresent(c -> {
            c.setValue("");
            c.setPath("/");
            c.setMaxAge(0);
            response.addCookie(c);
        });
    }

    public static String serialize(Object object) {
        return Base64.getUrlEncoder().encodeToString(SerializationUtils.serialize(object));
    }

    public static <T> T deserialize(Cookie cookie, Class<T> tClass) {
        return tClass.cast(SerializationUtils.deserialize(Base64.getUrlDecoder().decode(cookie.getValue())));
    }
}
