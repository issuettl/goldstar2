package lgekorfrm.sso.examples;

import lgekorfrm.sso.SSOClient;
import lgekorfrm.sso.oauth.LogoutRequest;

public class LogoutFlowExample {
    public static void main(String[] args) throws Exception {

        SSOClient SSOClient = new SSOClient("http://localhost:8080");

        //Step1. Request Logout
        LogoutRequest logoutRequest = new LogoutRequest()
                .withClientKey("myapp")
                .withRedirectUri("http://localhost:8080/callback/logout")
                .withState("my-state");

        String url = SSOClient.generateLogoutUrl(logoutRequest);
        System.out.println(url);

        //Step2. Redirect user
        //response.sendRedirect(url)


        //Step3. After user logout, SSO server will redirect user to GET "http://localhost:8080/my-logout?status={success/failed}&state={state}"
    }
}
