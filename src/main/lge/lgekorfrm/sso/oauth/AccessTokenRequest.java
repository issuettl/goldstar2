package lgekorfrm.sso.oauth;

import lgekorfrm.sso.util.HttpParameterParser;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccessTokenRequest implements Serializable {
    private String clientKey;
    private String clientSecret;
    private GrantType grant_type;
    private String code;
    private List<String> scope = new ArrayList<String>();
    private String state;
    private String username;
    private String password;
    private TokenType tokenType = TokenType.JWT;
    private Map parameterMap = new HashMap();
    private String refreshToken;
    private String locale;
    private String error;

    public static enum TokenType {
        JWT, Bearer
    }

    public static enum GrantType {
        authorization_code, password, client_credentials, refresh_token
    }


    public AccessTokenRequest withClientKeyAndSecret(String clientKey, String clientSecret) {
        this.clientKey = clientKey;
        this.clientSecret = clientSecret;
        return this;
    }

    public AccessTokenRequest withGrantType(GrantType grant_type) {
        this.grant_type = grant_type;
        return this;
    }

    public AccessTokenRequest withCode(String code) {
        this.code = code;
        return this;
    }

    public AccessTokenRequest addScope(String scope) {
        this.scope.add(scope);
        return this;
    }

    public AccessTokenRequest withState(String state) {
        this.state = state;
        return this;
    }

    public AccessTokenRequest withUsernameAndPassword(String username, String password) {
        this.username = username;
        this.password = password;
        return this;
    }

    public AccessTokenRequest withTokenType(TokenType tokenType) {
        this.tokenType = tokenType;
        return this;
    }

    public AccessTokenRequest withParameterMap(Map parameterMap) {
        this.parameterMap = parameterMap;
        return this;
    }

    public AccessTokenRequest withRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
        return this;
    }

    public AccessTokenRequest withLocale(String locale) {
        this.locale = locale;
        return this;
    }


    private void validate() {
        if (this.grant_type == null) {
            this.error = "grant_type is required";
        }
        if (this.clientKey == null) {
            this.error = "clientKey is required";
        }
        if (this.clientSecret == null) {
            this.error = "clientSecret is required";
        }
        if (this.tokenType == null) {
            this.error = "tokenType is required";
        }

        //code
        if (this.grant_type.equals(GrantType.authorization_code)) {
            if (this.code == null) {
                this.error = "authorization_code: code is required";
            }
        }

        //password
        if (this.grant_type.equals(GrantType.password)) {
            if (this.username == null) {
                this.error = "password: username is required";
            }
            if (this.password == null) {
                this.error = "password: password is required";
            }
        }

        //refresh_token
        if (this.grant_type.equals(GrantType.refresh_token)) {
            if (this.refreshToken == null) {
                this.error = "refresh_token: refresh_token is required";
            }
        }
    }

    public Map<String, Object> build() throws Exception {
        this.validate();
        if (this.error != null) {
            throw new Exception(this.error);
        }
        Map params = new HashMap();
        params.put("client_id", this.clientKey);
        params.put("client_secret", this.clientSecret);
        params.put("grant_type", this.grant_type);
        params.put("token_type", this.tokenType);
        //code
        if (this.grant_type.equals(GrantType.authorization_code)) {
            params.put("code", this.code);
        }
        //password
        if (this.grant_type.equals(GrantType.password)) {
            if (this.parameterMap != null) {
                params.putAll(HttpParameterParser.mapToParameterMapWithPrefix(this.parameterMap, "input."));
            }
            //TODO map to params "input."

            params.put("scope", String.join(",", this.scope));
            params.put("username", this.username);
            params.put("password", this.password);
        }
        //client
        if (this.grant_type.equals(GrantType.client_credentials)) {
            params.put("scope", String.join(",", this.scope));
        }
        //refresh
        if (this.grant_type.equals(GrantType.refresh_token)) {
            params.put("refresh_token", this.refreshToken);
        }
        return params;
    }
}
