package site.nonestep.idontwantwalk.auth.jwt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
@AllArgsConstructor
public class JsonWebToken {
    private String accessToken;

    private String refreshToken;
}
