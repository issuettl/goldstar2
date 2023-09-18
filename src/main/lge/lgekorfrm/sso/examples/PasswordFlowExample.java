package lgekorfrm.sso.examples;

import lgekorfrm.sso.SSOClient;
import lgekorfrm.sso.domain.AccessTokenResponse;
import lgekorfrm.sso.oauth.AccessTokenRequest;

import java.util.HashMap;

/**
 * Created by uengine on 2016. 9. 8..
 */
public class PasswordFlowExample {
    public static void main(String[] args) throws Exception {

        SSOClient SSOClient = new SSOClient("http://localhost:8080");

        AccessTokenRequest tokenRequest = new AccessTokenRequest()
                .withClientKeyAndSecret("myapp", "69460cb3-cc27-4b95-94fd-1b54bcfe9d95")
                .withGrantType(AccessTokenRequest.GrantType.password)
                .withTokenType(AccessTokenRequest.TokenType.JWT)
                .withParameterMap(new HashMap())
                .addScope("lgcom.read")
                .addScope("lgcom.edit")
                .withLocale("us")
                .withUsernameAndPassword("darkgodarkgo2@gmail.com", "!Gosu23546");
        AccessTokenResponse tokenResponse = SSOClient.requestToken(tokenRequest);

        String access_token = tokenResponse.getAccess_token();
        String refresh_token = tokenResponse.getRefresh_token();

        System.out.println(access_token);
        System.out.println(refresh_token);
    }
}
