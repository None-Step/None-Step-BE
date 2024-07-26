package site.nonestep.idontwantwalk.auth.oauth;

import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistration.Builder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;

import static site.nonestep.idontwantwalk.auth.Constants.SERVER_URL;

public enum CustomOAuth2Provider {

    KAKAO {
        @Override
        public Builder getBuilder(String registrationId) {
            Builder builder = getBuilder(registrationId,
                    ClientAuthenticationMethod.CLIENT_SECRET_POST, SERVER_URL.concat("/nonestep/member/login/callback/{registrationId}"));
            builder.authorizationUri("https://kauth.kakao.com/oauth/authorize");
            builder.tokenUri("https://kauth.kakao.com/oauth/token");
            builder.userInfoUri("https://kapi.kakao.com/v2/user/me");
            builder.userNameAttributeName("id");
            builder.clientName("kakao");
            builder.scope("profile_nickname", "account_email");
            return builder;
        }

    },
    NAVER {
        @Override
        public Builder getBuilder(String registrationId) {
            ClientRegistration.Builder builder = getBuilder(registrationId,
                    ClientAuthenticationMethod.CLIENT_SECRET_BASIC, SERVER_URL.concat("/nonestep/member/login/callback/{registrationId}"));
            builder.scope("name", "email");
            builder.authorizationUri("https://nid.naver.com/oauth2.0/authorize");
            builder.tokenUri("https://nid.naver.com/oauth2.0/token");
            builder.userInfoUri("https://openapi.naver.com/v1/nid/me");
            builder.userNameAttributeName("response");
            builder.clientName("Naver");
            return builder;
        }
    };

    protected final Builder getBuilder(String registrationId, ClientAuthenticationMethod method,
                                       String redirectUri) {
        Builder builder = ClientRegistration.withRegistrationId(registrationId);
        builder.clientAuthenticationMethod(method);
        builder.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE);
        builder.redirectUri(redirectUri);
        return builder;
    }

    public abstract Builder getBuilder(String registrationId);
}
