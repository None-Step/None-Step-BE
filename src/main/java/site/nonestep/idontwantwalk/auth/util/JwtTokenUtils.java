package site.nonestep.idontwantwalk.auth.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import site.nonestep.idontwantwalk.auth.exception.ErrorResponse;
import site.nonestep.idontwantwalk.auth.jwt.JsonWebToken;

import java.security.Key;
import java.util.Arrays;
import java.util.Date;

public class JwtTokenUtils {
    //우리 프로젝트 만의 시크릿 키
//    private static final String SECRET_KEY = Base64.getEncoder().encodeToString("xyz-jwt-secret-key".getBytes());
//    private Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    //    private byte[] keyBytes = Decoders.BASE64.decode("xyz-jwt-secret-key");
    private static Key key = Keys.secretKeyFor(io.jsonwebtoken.SignatureAlgorithm.HS256);
//    // access 토큰 1시간 실제
//    public static final long ACCESS_PERIOD = 1000L * 60L * 60L * 1L;

    // access 토큰 1시간
    public static final long ACCESS_PERIOD = 1000L * 60L * 60L * 24L * 14L;

    // refresh 토큰 보통 2주
//    public static final long REFRESH_PERIOD = 1000L * 60L * 60L * 24L * 2L;
    public static final long REFRESH_PERIOD = 1000L * 60L * 60L * 24L * 14L;

    //userSequence & role 토큰 발급
    public static JsonWebToken allocateToken(Long userSequence, String role) throws RuntimeException {
        try {
            JwtBuilder jwtBuilder = Jwts.builder()
                    .setHeaderParam("alg", "HS256")
                    .setHeaderParam("typ", "JWT");

            jwtBuilder.claim("sequence", userSequence);                                    //JWT의 body
            jwtBuilder.claim("role", role);                                          //JWT의 body

            Date now = new Date();
            return new JsonWebToken(
                    jwtBuilder.setIssuedAt(now)
                            .setExpiration(new Date(now.getTime() + ACCESS_PERIOD))
                            .signWith(key, SignatureAlgorithm.HS256)                //암호화. JWT에는 권한까지 되어있기 때문에 중요.
                            .compact(),
                    jwtBuilder.setIssuedAt(now)
                            .setExpiration(new Date(now.getTime() + REFRESH_PERIOD))        //암호화. JWT에는 권한까지 되어있기 때문에 중요.
                            .signWith(key, SignatureAlgorithm.HS256)
                            .compact()
            );
        } catch (Exception e) {
            throw new ErrorResponse(HttpStatus.FORBIDDEN, "잘못된 토큰입니다.");
        }
    }

    public static String changeAccessToken(Long userSequence, String role) throws RuntimeException {
        try {
            JwtBuilder jwtBuilder = Jwts.builder()
                    .setHeaderParam("alg", "HS256")
                    .setHeaderParam("typ", "JWT");

            jwtBuilder.claim("sequence", userSequence);                                    //JWT의 body
            jwtBuilder.claim("role", role);                                          //JWT의 body

            Date now = new Date();
            return jwtBuilder.setIssuedAt(now)
                    .setExpiration(new Date(now.getTime() + ACCESS_PERIOD))
                    .signWith(key, SignatureAlgorithm.HS256)                //암호화. JWT에는 권한까지 되어있기 때문에 중요.
                    .compact();
        } catch (Exception e) {
            throw new ErrorResponse(HttpStatus.FORBIDDEN, "잘못된 토큰입니다.");
        }
    }

    public static Claims getClaims(String token) throws RuntimeException {
        try {
            return Jwts.parser().setSigningKey(key).build().parseClaimsJws(token).getBody();
        } catch (Exception e) {
            throw new ErrorResponse(HttpStatus.FORBIDDEN, "잘못된 토큰입니다.(2)");
        }
    }

    public static String getClaimAttribute(String token, String key) throws RuntimeException {
        return getClaims(token).getOrDefault(key, null).toString();
    }

    //토큰 유효 시간 검사
    public static boolean isValidToken(String token) {//throws RuntimeException
        try {
            Jwts.parser().setSigningKey(key).build().parseClaimsJws(token);
            if (Jwts.parser().setSigningKey(key).build().parseClaimsJws(token).getBody().getExpiration().before(new Date())) {
                return false;
            } else {
                return true;
            }
        } catch (ExpiredJwtException e) {
            return false;
        } catch (Exception e) {
            throw new ErrorResponse(HttpStatus.BAD_REQUEST, "잘못된 토큰입니다.");
        }
    }

    public static String resolveAccessToken(HttpServletRequest req) throws RuntimeException {

        String accessToken = req.getHeader("Authorization");
        if (accessToken == null) throw new ErrorResponse(HttpStatus.FORBIDDEN, "로그인 되어 있지 않습니다.");

        return accessToken;

    }

    public static String resolveRefreshToken(HttpServletRequest req) throws RuntimeException {
        Cookie[] cookies = req.getCookies();
        if (null == cookies) return null;
        Cookie accessToken = Arrays.stream(cookies)
                .filter(c -> c.getName().equals("Refresh"))
                .findAny()
                .orElse(new Cookie("void", null));

        return accessToken.getValue();
    }
}
