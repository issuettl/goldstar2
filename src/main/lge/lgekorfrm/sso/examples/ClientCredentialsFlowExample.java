package lgekorfrm.sso.examples;

import lgekorfrm.sso.SSOClient;
import lgekorfrm.sso.domain.AccessTokenResponse;
import lgekorfrm.sso.oauth.AccessTokenRequest;

import java.util.HashMap;

public class ClientCredentialsFlowExample {
    public static void main(String[] args) throws Exception {

        SSOClient SSOClient = new SSOClient("https://ssodev.lg.com");

        AccessTokenRequest tokenRequest = new AccessTokenRequest()
                .withClientKeyAndSecret("test", "test")
                .withGrantType(AccessTokenRequest.GrantType.client_credentials)
                .withTokenType(AccessTokenRequest.TokenType.JWT)
                .withParameterMap(new HashMap())
                .addScope("test");
        AccessTokenResponse tokenResponse = SSOClient.requestToken(tokenRequest);

        String access_token = tokenResponse.getAccess_token();
        String refresh_token = tokenResponse.getRefresh_token();

        System.out.println(access_token);
        System.out.println(refresh_token);
    }
}
