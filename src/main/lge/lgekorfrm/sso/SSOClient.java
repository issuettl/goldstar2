package lgekorfrm.sso;

import lgekorfrm.sso.controller.LoginController;
import lgekorfrm.sso.domain.AccessTokenResponse;
import lgekorfrm.sso.domain.TokenInformation;
import lgekorfrm.sso.domain.PipeLineHistory;
import lgekorfrm.sso.oauth.*;
import lgekorfrm.sso.util.HttpUtils;
import lgekorfrm.sso.util.JsonUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import lgekorfrm.code.CommonCodes;
/**
 * Created by uengine on 2016. 9. 8..
 */
public class SSOClient {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SSOClient.class);
    private String host;

    public SSOClient(String host) {
        this.host = host;
    }

    public String generateAutorizeUrl(AuthorizeRequest request) throws Exception {
        Map<String, Object> params = request.build();
        String queryString = HttpUtils.createGETQueryString(params);
        return this.host + "/oauth/authorize" + queryString;
    }

    public String generateLogoutUrl(LogoutRequest request) throws Exception {
        Map<String, Object> params = request.build();
        String queryString = HttpUtils.createGETQueryString(params);
        return this.host + "/oauth/logout" + queryString;
    }

    public AccessTokenResponse requestToken(AccessTokenRequest request) throws Exception {
        Map<String, Object> params = request.build();

        String queryString = HttpUtils.createPOSTQueryString(params);

        HttpUtils httpUtils = new HttpUtils();
        String url = this.host + "/oauth/access_token";

        Map<String, String> headers = new HashMap();
        headers.put("Content-Type", "application/x-www-form-urlencoded");
        
		LOGGER.warn(CommonCodes.INTERFACE_COMMON_KEYWORD+"^^"+CommonCodes.INTERFACE_SYSTEM_SSO+"^^[FRM]SSOClient^^requestToken^^inputParam(queryString)^^"+queryString+"^^inputParma(header)^^"+headers);
		LOGGER.warn(CommonCodes.INTERFACE_COMMON_KEYWORD+"^^"+CommonCodes.INTERFACE_SYSTEM_SSO+"^^[FRM]SSOClient^^requestToken^^targetURL^^"+url);
		
        HttpResponse response = httpUtils.makeRequest("POST", url, queryString, headers);
        HttpEntity entity = response.getEntity();
        String responseText = EntityUtils.toString(entity);
        
		LOGGER.warn(CommonCodes.INTERFACE_COMMON_KEYWORD+"^^"+CommonCodes.INTERFACE_SYSTEM_SSO+"^^[FRM]SSOClient^^requestToken^^responseCode^^"+response.getStatusLine().getStatusCode()+"^^returnData^^"+ responseText);
               
        Map unmarshal = JsonUtils.unmarshal(responseText);
        AccessTokenResponse accessTokenResponse = JsonUtils.convertValue(unmarshal, AccessTokenResponse.class);
             
        if (accessTokenResponse.getError() != null) {
            throw new Exception(accessTokenResponse.getError_description());
        }
        return accessTokenResponse;
    }

    public TokenInformation validateToken(String token) throws Exception {
        Map<String, Object> params = new HashMap<>();
        params.put("access_token", token);

        String queryString = HttpUtils.createGETQueryString(params);

        HttpUtils httpUtils = new HttpUtils();
        String url = this.host + "/oauth/token_info" + queryString;

        Map<String, String> headers = new HashMap();
		LOGGER.warn(CommonCodes.INTERFACE_COMMON_KEYWORD+"^^"+CommonCodes.INTERFACE_SYSTEM_SSO+"^^[FRM]SSOClient^^validateToken^^inputParam(queryString)^^"+queryString+"^^inputParma(header)^^"+headers);
		LOGGER.warn(CommonCodes.INTERFACE_COMMON_KEYWORD+"^^"+CommonCodes.INTERFACE_SYSTEM_SSO+"^^[FRM]SSOClient^^validateToken^^targetURL^^"+url);
        
		HttpResponse response = httpUtils.makeRequest("GET", url, null, headers);
  
        HttpEntity entity = response.getEntity();
        String responseText = EntityUtils.toString(entity);
		LOGGER.warn(CommonCodes.INTERFACE_COMMON_KEYWORD+"^^"+CommonCodes.INTERFACE_SYSTEM_SSO+"^^[FRM]SSOClient^^validateToken^^responseCode^^"+response.getStatusLine().getStatusCode()+"^^returnData^^"+ responseText);
	    
        Map unmarshal = JsonUtils.unmarshal(responseText);
        TokenInformation tokenInformation = JsonUtils.convertValue(unmarshal, TokenInformation.class);
        if (tokenInformation.getError() != null) {
            throw new Exception(tokenInformation.getError_description());
        }
        return tokenInformation;
    }
}
