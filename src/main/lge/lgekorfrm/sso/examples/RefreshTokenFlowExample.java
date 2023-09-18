package lgekorfrm.sso.examples;

import lgekorfrm.sso.SSOClient;
import lgekorfrm.sso.domain.AccessTokenResponse;
import lgekorfrm.sso.oauth.AccessTokenRequest;

public class RefreshTokenFlowExample {
    public static void main(String[] args) throws Exception {

        SSOClient SSOClient = new SSOClient("http://localhost:8080");

        AccessTokenRequest tokenRequest = new AccessTokenRequest()
                .withClientKeyAndSecret("myapp", "69460cb3-cc27-4b95-94fd-1b54bcfe9d95")
                .withGrantType(AccessTokenRequest.GrantType.refresh_token)
                .withTokenType(AccessTokenRequest.TokenType.JWT)
                .withRefreshToken("768a2c1a-c75b-4d4d-a3f2-bc62e411ecf4");
        AccessTokenResponse tokenResponse = SSOClient.requestToken(tokenRequest);

        String access_token = tokenResponse.getAccess_token();
        String refresh_token = tokenResponse.getRefresh_token();

        System.out.println(access_token);
        System.out.println(refresh_token);
    }
}
