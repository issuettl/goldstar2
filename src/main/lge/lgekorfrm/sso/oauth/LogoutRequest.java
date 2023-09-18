package lgekorfrm.sso.oauth;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by uengine on 2016. 4. 15..
 */
public class LogoutRequest {
    private String clientKey;
    private String redirectUri;
    private String state;
    private String error;

    public LogoutRequest withClientKey(String clientKey) {
        this.clientKey = clientKey;
        return this;
    }

    public LogoutRequest withRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
        return this;
    }

    public LogoutRequest withState(String state) {
        this.state = state;
        return this;
    }

    private void validate() {
        if (this.clientKey == null) {
            this.error = "clientKey is required";
        }
        if (this.redirectUri == null) {
            this.error = "redirectUri is required";
        }
    }

    public Map<String, Object> build() throws Exception {
        this.validate();
        if (this.error != null) {
            throw new Exception(this.error);
        }
        Map params = new HashMap();
        params.put("client_id", this.clientKey);
        params.put("redirect_uri", this.redirectUri);
        params.put("state", this.state);
        return params;
    }
}
