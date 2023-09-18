package lgekorfrm.sso.examples;

import lgekorfrm.sso.SSOClient;
import lgekorfrm.sso.domain.TokenInformation;
import lgekorfrm.sso.util.JsonUtils;
import lgekorfrm.sso.util.JwtUtils;
import com.nimbusds.jwt.JWTClaimsSet;
import net.minidev.json.JSONObject;

import java.util.Map;

public class ValidateTokenExample {
    public static void main(String[] args) throws Exception {


        String token = "eyJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJsZ2NvbS1zc28iLCJjb250ZXh0Ijp7ImNsaWVudEtleSI6Im15YXBwIiwic2NvcGVzIjpbIm1rdCJdLCJ0eXBlIjoidXNlciIsInVzZXIiOnsiaWQiOjQ4ODEzNjMsImVtYWlsIjoiZGFya2dvZGFya2dvMkBnbWFpbC5jb20iLCJmaXJzdF9uYW1lIjoiU2V1bmdwaWwiLCJsYXN0X25hbWUiOiJQYXJrIn0sInNpZCI6ImY2N2FlOGZkLTg2NmItNDM2Mi1hYmU3LTI4NjZhZWFjZDRkNyIsInVzZXJuYW1lIjoiZGFya2dvZGFya2dvMkBnbWFpbC5jb20iLCJzdG9yYWdlSWQiOjF9LCJleHAiOjE1NDU4OTE1MjQsImlhdCI6MTU0NTg4NzkyNH0.LQhBn7ALDtvgQP5NtAalwRrIWVXCrOheFqSWfOXoK4bNhVA9CLjUCo2mcPfUycxEEVACat2RQZE4l37aRXaqpqflcqc-lwIBrvC1Rz3zGrxzEiXNF5BjMZkpgdMemua2B6GdFxsYTWb_go-T6EIhKI6pHKPpQGfawgabv1TLZGE";
        /**
         * Case1. Validate token to sso server.
         * In this case, at the start of the page, it is performed to check whether or not the user is logged in.
         */
        SSOClient SSOClient = new SSOClient("http://localhost:8080");
        TokenInformation tokenInformation = SSOClient.validateToken(token);

        //if token is valid, get user information from context.
        Map user = tokenInformation.getContext().getUser();
        String username = tokenInformation.getContext().getUsername();
        String clientKey = tokenInformation.getContext().getClientKey();
        System.out.println(JsonUtils.marshal(user));
        System.out.println(username);
        System.out.println(clientKey);

        //Although the token is valid, you should check separately whether the user has logged in.
        TokenInformation.Status status = tokenInformation.getLogin().getStatus();
        if (status.equals(TokenInformation.Status.active)) {
            System.out.println("user logged in");
        }

        if (status.equals(TokenInformation.Status.inactive)) {
            System.out.println("user not logged in");
        }


        /**
         * Case2. Get user information application itself.
         * In this case, you want to get only user information from token.
         * Since you do not need to connect to the SSO server, you can access it quickly.
         *
         * Limitation.
         * Because this method does not validate the token signature, protect it with a middleware (Api gateway) that can prove that the token is valid.
         * Because you are not querying the SSO server, you can not accurately determine the user's login status.
         */
        JWTClaimsSet jwtClaimsSet = JwtUtils.parseToken(token);
        JSONObject contexts = (JSONObject) jwtClaimsSet.getClaim("context");
        user = JsonUtils.convertValue(contexts.get("user"), Map.class);

        username = tokenInformation.getContext().getUsername();
        clientKey = tokenInformation.getContext().getClientKey();
        System.out.println(JsonUtils.marshal(user));
        System.out.println(username);
        System.out.println(clientKey);
    }
}
