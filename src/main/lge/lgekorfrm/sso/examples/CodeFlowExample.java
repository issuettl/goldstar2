package lgekorfrm.sso.examples;

import lgekorfrm.sso.SSOClient;
import lgekorfrm.sso.domain.AccessTokenResponse;
import lgekorfrm.sso.oauth.AccessTokenRequest;
import lgekorfrm.sso.oauth.AuthorizeRequest;

import java.util.HashMap;

public class CodeFlowExample {
    public static void main(String[] args) throws Exception {

        SSOClient SSOClient = new SSOClient("http://localhost:8080");

        //Step1. Request Authorize for code
        AuthorizeRequest authorizeRequest =
                new AuthorizeRequest()
                        .withClientKey("myapp")
                        .addScope("lgcom.read")
                        .addScope("lgcom.edit")
                        .withParameterMap(new HashMap())
                        .withState("my-state")
                        .withLocale("us")
                        //.withPage("signup") //if want to move other page.
                        .withRedirectUri("http://localhost:8080/callback/login")
                        .withResponseType(AuthorizeRequest.ResponseType.code)
                        .withTokenType(AuthorizeRequest.TokenType.JWT)
                        .forceLogin(true);

        String url = SSOClient.generateAutorizeUrl(authorizeRequest);
        System.out.println(url);

        //Step2. Redirect user
        //response.sendRedirect(url)


        //Step3. After user login, SSO server will redirect user to GET "http://localhost:8080/check?code={code}&state={state}"

        //Step4. Send to sso service with "code", sso server will return token.
        AccessTokenRequest tokenRequest = new AccessTokenRequest()
                .withClientKeyAndSecret("myapp", "69460cb3-cc27-4b95-94fd-1b54bcfe9d95")
                .withGrantType(AccessTokenRequest.GrantType.authorization_code)
                .withTokenType(AccessTokenRequest.TokenType.JWT)
                .withCode("700aea62-5d97-405b-a0ab-d75656a387b2");
        AccessTokenResponse tokenResponse = SSOClient.requestToken(tokenRequest);

        String access_token = tokenResponse.getAccess_token();
        String refresh_token = tokenResponse.getRefresh_token();

        System.out.println(access_token);
        System.out.println(refresh_token);
    }
}
