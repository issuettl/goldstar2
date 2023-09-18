package lgekorfrm.sso.oauth;

import lgekorfrm.sso.util.HttpParameterParser;
import lgekorfrm.sso.util.HttpUtils;
import lgekorfrm.sso.util.JsonUtils;

import java.util.*;

/**
 * Created by uengine on 2016. 4. 15..
 */
public class AuthorizeRequest {
    private String clientKey;
    private ResponseType responseType;
    private String redirectUri;
    private List<String> scope = new ArrayList<String>();
    private String state;
    private TokenType tokenType = TokenType.JWT;
    private Map parameterMap = new HashMap();
    private boolean forceLogin;
    private String page;
    private String locale;
    private String error;

    public static enum ResponseType {
        code, token
    }

    public static enum TokenType {
        JWT, Bearer
    }

    public AuthorizeRequest withClientKey(String clientKey) {
        this.clientKey = clientKey;
        return this;
    }

    public AuthorizeRequest withResponseType(ResponseType responseType) {
        this.responseType = responseType;
        return this;
    }

    public AuthorizeRequest withRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
        return this;
    }

    public AuthorizeRequest addScope(String scope) {
        this.scope.add(scope);
        return this;
    }

    public AuthorizeRequest withState(String state) {
        this.state = state;
        return this;
    }

    public AuthorizeRequest withTokenType(TokenType tokenType) {
        this.tokenType = tokenType;
        return this;
    }

    public AuthorizeRequest withParameterMap(Map parameterMap) {
        this.parameterMap = parameterMap;
        return this;
    }

    public AuthorizeRequest forceLogin(boolean forceLogin) {
        this.forceLogin = forceLogin;
        return this;
    }

    public AuthorizeRequest withPage(String page) {
        this.page = page;
        return this;
    }

    public AuthorizeRequest withLocale(String locale) {
        this.locale = locale;
        return this;
    }

    private void validate() {
        if (this.clientKey == null) {
            this.error = "clientKey is required";
        }
        if (this.responseType == null) {
            this.error = "responseType is required";
        }
        if (this.redirectUri == null) {
            this.error = "redirectUri is required";
        }
        if (this.responseType.equals(ResponseType.token) && this.tokenType == null) {
            this.error = "tokenType is required for implict flow";
        }
    }

    public Map<String, Object> build() throws Exception {
        this.validate();
        if (this.error != null) {
            throw new Exception(this.error);
        }
        Map params = new HashMap();
        params.put("client_id", this.clientKey);
        params.put("response_type", this.responseType.toString());

        if (this.tokenType != null) {
            params.put("token_type", this.tokenType.toString());
        }

        params.put("redirect_uri", this.redirectUri);
        params.put("scope", String.join(",", this.scope));

        params.put("state", this.state);

        if (this.parameterMap != null) {
            params.putAll(HttpParameterParser.mapToParameterMapWithPrefix(this.parameterMap, "input."));
        }
        if (this.page != null) {
            params.put("page", this.page);
        }
        if (this.locale != null) {
            params.put("locale", this.locale);
        }

        params.put("forceLogin", this.forceLogin);
        return params;
    }
}
