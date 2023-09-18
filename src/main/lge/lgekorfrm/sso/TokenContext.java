package lgekorfrm.sso;

import com.nimbusds.jwt.JWTClaimsSet;
import lgekorfrm.sso.util.JsonUtils;
import net.minidev.json.JSONObject;
import org.springframework.util.StringUtils;
import lgekorfrm.sso.util.JwtUtils;

import java.util.Map;

public class TokenContext {
	static ThreadLocal<TokenContext> local = new ThreadLocal();
    private Map user;
    private String token;

    public Map getUser() {
        return user;
    }

    public String getToken() {
        return token;
    }

    public TokenContext(String token) {
        this.token = token;
        this.user = null;
        if (!StringUtils.isEmpty(token)) {
            try {
                JWTClaimsSet jwtClaimsSet = JwtUtils.parseToken(token);
                JSONObject contexts = (JSONObject) jwtClaimsSet.getClaim("context");
                this.user = JsonUtils.convertValue(contexts.get("user"), Map.class);
            } catch (Exception ex) {

            }
        }
        local.set(this);
    }

    public static TokenContext getThreadLocalInstance() {
        TokenContext tc = (TokenContext) local.get();
        return tc != null ? tc : new TokenContext(null);
    }
}
