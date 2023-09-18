package lgekorfrm.sso.emp;

import lgekorfrm.code.CommonCodes;
import lgekorfrm.sso.util.HttpUtils;
import lgekorfrm.sso.util.JsonUtils;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.*;

public class EmpSsoClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmpSsoClient.class);
    private static final String HMAC_SHA1_ALGORITHM = "HmacSHA1";

    private static final int CONNECT_TIME_OUT = 5000;   //5s
    private static final int SOCKET_TIME_OUT = 10000;   //10s

    private String HOST;
    private String APP_KEY;
    private String APP_SECRET;

    public EmpSsoClient(String host, String appKey, String appSecret) {
        this.HOST = host;
        this.APP_KEY = appKey;
        this.APP_SECRET = appSecret;
    }

    /**
     * Call EMP SSO API(OTH.101)
     * @return datetime string
     * @throws Exception
     */
    private String getEMPDateTimeStamp() throws Exception{

        String url = "/oauth/1.0/datetime";
        String dateTimeStampStr = "";

        //Header
        Map<String, String> headers = new HashMap();
        headers.put("Accept", "application/json");
        headers.put("x-lge-appkey", this.APP_KEY);

        //Request
        HttpUtils httpUtils = buildCustomHttpUtils();

        HttpResponse response = httpUtils.makeRequest("GET", this.HOST + url, (String)null, headers);
        HttpEntity entity = response.getEntity();
        String responseText = EntityUtils.toString(entity);

        LOGGER.warn(CommonCodes.INTERFACE_COMMON_KEYWORD+"^^"+CommonCodes.INTERFACE_SYSTEM_EMP_SSO+"^^[FRM]EMPSSOClient^^datetime^^responseCode^^"+response.getStatusLine().getStatusCode()+"^^returnData^^"+ responseText);

        Map unmarshal = JsonUtils.unmarshal(responseText);

        if(unmarshal.get("date")!=null){
            dateTimeStampStr = (String)unmarshal.get("date");
        }

        return dateTimeStampStr;

    }

    /**
     * Call EMP SSO API(OTH.172) - LGE.com 에서는 직접 요청하지않음.
     * 회원통합에서 구현하며, 테스트용
     * @param empSessionStr
     * @param empUserId
     * @return redirection_uri
     * @throws Exception
     */
    public String requestAuthorizationCode(String empSessionStr, String empUserId, String redirectParamUri) throws Exception{
        String redirectUri = "";
/*
        String url = "/oauth/1.0/emp/oauth2/auth";

        //Param
        Map<String,String> params = new HashMap<String, String>();
        params.put("client_id",this.APP_KEY);
        params.put("country_code","KR");
        params.put("redirect_uri","http://example2.com/login");
        params.put("response_type","code");
        params.put("state","/");

        String queryString = sortedEncodedQueryString(params);

        //Header
        String dateTimeStampStr = getEMPDateTimeStamp();
        String signatureStr = encryptingHMacSHA1(url + "?" + queryString + "\n" + dateTimeStampStr,  APP_SECRET);

        Map<String, String> headers = new HashMap();
        headers.put("Accept", "application/json");
        headers.put("Content-Type", "application/x-www-form-urlencoded");
        headers.put("x-lge-oauth-date", dateTimeStampStr);
        headers.put("x-lge-appkey", this.APP_KEY);
        headers.put("x-login-session", empSessionStr);
        headers.put("x-lge-oauth-signature", signatureStr);

        //Request
        HttpUtils httpUtils = buildCustomHttpUtils();
        HttpResponse response = httpUtils.makeRequest("POST", this.HOST + url, queryString, headers);
        HttpEntity entity = response.getEntity();
        String responseText = EntityUtils.toString(entity);
*/
        String url = "/emp/oauth2/authorize/empsession";

        //Param
        Map<String,String> params = new HashMap<String, String>();
        params.put("client_id",this.APP_KEY);
        params.put("country_code","KR");
        params.put("redirect_uri",redirectParamUri);
        params.put("response_type","code");
        params.put("state","/");
        params.put("username",empUserId);
        params.put("account_type","LGE");
        params.put("country_code","KR");


        String queryString = sortedEncodedQueryString(params);

        //Header
        String dateTimeStampStr = getEMPDateTimeStamp();
        String signatureStr = encryptingHMacSHA1(url + "?" + queryString + "\n" + dateTimeStampStr,  "642f73d03c534d67a758e6f22c8fdf15");

        Map<String, String> headers = new HashMap();
        headers.put("Accept", "application/json");
        headers.put("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
        headers.put("lgemp-x-date", dateTimeStampStr);
        headers.put("lgemp-x-app-key", "fa281d2a2c0a46f1832ea2632beef25e");
        headers.put("lgemp-x-session-key", empSessionStr);
        headers.put("lgemp-x-signature", signatureStr);

        //Request
        HttpUtils httpUtils = buildCustomHttpUtils();
        HttpResponse response = httpUtils.makeRequest("GET", this.HOST + url +"?"+queryString, (String)null, headers);
        HttpEntity entity = response.getEntity();
        String responseText = EntityUtils.toString(entity);

        LOGGER.warn(CommonCodes.INTERFACE_COMMON_KEYWORD+"^^"+CommonCodes.INTERFACE_SYSTEM_EMP_SSO+"^^[FRM]EMPSSOClient^^requestAuthorizationCodeRedirectUri^^headers^^"+headers.toString());
        LOGGER.warn(CommonCodes.INTERFACE_COMMON_KEYWORD+"^^"+CommonCodes.INTERFACE_SYSTEM_EMP_SSO+"^^[FRM]EMPSSOClient^^requestAuthorizationCodeRedirectUri^^responseCode^^"+response.getStatusLine().getStatusCode()+"^^returnData^^"+ responseText);

        Map unmarshal = JsonUtils.unmarshal(responseText);

        if(unmarshal.get("redirect_uri")!=null){
            redirectUri = (String)unmarshal.get("redirect_uri");
        }

        return redirectUri;
    }

    /**
     * Call EMP SSO API(OTH.131)
     * @param grantType
     * @param tokenKey
     * @return access_token, refresh_token (only authorization_code type)
     * @throws Exception
     */
    public EmpAccessTokenResponse requestToken(String grantType, String tokenKey, String redirectUri, String ssoId) throws Exception {

        String url = "/oauth/1.0/oauth2/token";

        //Param
        Map<String,String> params = new HashMap<String, String>();
        //RefreshToken
        if(grantType.equals("REFRESH_TOKEN")) {
            params.put("grant_type","refresh_token");
            params.put("refresh_token",tokenKey);
        }else {
            params.put("grant_type", "authorization_code");
            params.put("code", tokenKey);
            if(StringUtils.isNotBlank(ssoId)){
                params.put("sso_id", ssoId);
            }
        }
        params.put("redirect_uri",redirectUri);


        String queryString = sortedEncodedQueryString(params);

        //Header
        String dateTimeStampStr = getEMPDateTimeStamp();
        String signatureStr = encryptingHMacSHA1(url + "?" + queryString + "\n" + dateTimeStampStr,  APP_SECRET);

        Map<String, String> headers = new HashMap();
        headers.put("Accept", "application/json");
        headers.put("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
        headers.put("x-lge-oauth-date", dateTimeStampStr);
        headers.put("x-lge-appkey", this.APP_KEY);
        headers.put("x-lge-oauth-signature", signatureStr);

        //Request
        HttpUtils httpUtils = buildCustomHttpUtils();
        HttpResponse response = httpUtils.makeRequest("POST", this.HOST + url, queryString, headers);
        HttpEntity entity = response.getEntity();
        String responseText = EntityUtils.toString(entity);

        LOGGER.warn(CommonCodes.INTERFACE_COMMON_KEYWORD+"^^"+CommonCodes.INTERFACE_SYSTEM_EMP_SSO+"^^[FRM]EMPSSOClient^^requestToken^^headers^^"+headers.toString());
        LOGGER.warn(CommonCodes.INTERFACE_COMMON_KEYWORD+"^^"+CommonCodes.INTERFACE_SYSTEM_EMP_SSO+"^^[FRM]EMPSSOClient^^requestToken^^responseCode^^"+response.getStatusLine().getStatusCode()+"^^returnData^^"+ responseText);

        Map unmarshal = JsonUtils.unmarshal(responseText);
        EmpAccessTokenResponse accessTokenResponse = JsonUtils.convertValue(unmarshal, EmpAccessTokenResponse.class);

        if (accessTokenResponse.getError() != null) {
            throw new Exception(accessTokenResponse.getError().getCode()+" "+accessTokenResponse.getError().getMessage());
        }
        return accessTokenResponse;
    }

    /**
     * Call EMP SSO API(OTH.135)
     * @param ssoId
     * @return
     * @throws Exception
     */
    public EmpAccessTokenResponse getTokenBySsoId(String ssoId) throws Exception {

        String url = "/oauth/1.0/oauth2/token/sso";

        //Param
        Map<String,String> params = new HashMap<String, String>();
        params.put("sso_id", ssoId);


        String queryString = sortedEncodedQueryString(params);

        //Header
        String dateTimeStampStr = getEMPDateTimeStamp();
        String signatureStr = encryptingHMacSHA1(url + "?" + queryString + "\n" + dateTimeStampStr,  APP_SECRET);

        Map<String, String> headers = new HashMap();
        headers.put("Accept", "application/json");
        headers.put("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
        headers.put("x-lge-oauth-date", dateTimeStampStr);
        headers.put("x-lge-appkey", this.APP_KEY);
        headers.put("x-lge-oauth-signature", signatureStr);

        //Request
        HttpUtils httpUtils = buildCustomHttpUtils();
        //HttpResponse response = httpUtils.makeRequest("GET", this.HOST + url +"?"+queryString, (String)null, headers);
        HttpResponse response = httpUtils.makeRequest("POST", this.HOST + url, queryString, headers);
        HttpEntity entity = response.getEntity();
        String responseText = EntityUtils.toString(entity);

        LOGGER.warn(CommonCodes.INTERFACE_COMMON_KEYWORD+"^^"+CommonCodes.INTERFACE_SYSTEM_EMP_SSO+"^^[FRM]EMPSSOClient^^requestToken^^headers^^"+headers.toString());
        LOGGER.warn(CommonCodes.INTERFACE_COMMON_KEYWORD+"^^"+CommonCodes.INTERFACE_SYSTEM_EMP_SSO+"^^[FRM]EMPSSOClient^^requestToken^^responseCode^^"+response.getStatusLine().getStatusCode()+"^^returnData^^"+ responseText);

        Map unmarshal = JsonUtils.unmarshal(responseText);
        EmpAccessTokenResponse accessTokenResponse = JsonUtils.convertValue(unmarshal, EmpAccessTokenResponse.class);

        if(accessTokenResponse.getError() != null){
            throw new Exception(accessTokenResponse.getError().getMessage());
        }
        return accessTokenResponse;
    }

    /**
     * Call EMP SSO API(OTH.117)
     * @param accessToken
     * @return
     * @throws Exception
     */
    public EmpTokenValidationResponse validateToken(String accessToken) throws Exception {

        String url = "/oauth/1.5/token";

        //Param
        Map<String,String> params = new HashMap<String, String>();
        params.put("access_token",accessToken);

        String queryString = sortedEncodedQueryString(params);

        //Header
        String dateTimeStampStr = getEMPDateTimeStamp();
        String signatureStr = encryptingHMacSHA1(url + "?" + queryString + "\n" + dateTimeStampStr,  APP_SECRET);

        Map<String, String> headers = new HashMap();
        headers.put("Accept", "application/json");
        headers.put("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
        headers.put("x-lge-oauth-date", dateTimeStampStr);
        headers.put("x-lge-appkey", this.APP_KEY);
        headers.put("x-lge-oauth-signature", signatureStr);

        //Request
        HttpUtils httpUtils = buildCustomHttpUtils();
        HttpResponse response = httpUtils.makeRequest("GET", this.HOST + url + "?" + queryString, (String)null, headers);
        HttpEntity entity = response.getEntity();
        String responseText = EntityUtils.toString(entity);

        LOGGER.warn(CommonCodes.INTERFACE_COMMON_KEYWORD+"^^"+CommonCodes.INTERFACE_SYSTEM_EMP_SSO+"^^[FRM]EMPSSOClient^^validateToken^^headers^^"+headers.toString());
        LOGGER.warn(CommonCodes.INTERFACE_COMMON_KEYWORD+"^^"+CommonCodes.INTERFACE_SYSTEM_EMP_SSO+"^^[FRM]EMPSSOClient^^validateToken^^responseCode^^"+response.getStatusLine().getStatusCode()+"^^returnData^^"+ responseText);

        Map unmarshal = JsonUtils.unmarshal(responseText);
        EmpTokenValidationResponse validationResponse = JsonUtils.convertValue(unmarshal, EmpTokenValidationResponse.class);

        if (validationResponse.getError() != null) {
            throw new Exception(validationResponse.getError().getMessage());
        }
        return validationResponse;
    }

    /**
     * Call EMP SSO API(OTH.134)
     * @param accessToken
     * @return
     * @throws Exception
     */
    public EmpTokenValidationResponse expireToken(String accessToken) throws Exception {

        String url = "/oauth/1.0/oauth2/token";

        //Param
        Map<String,String> params = new HashMap<String, String>();
        params.put("access_token",accessToken);

        String queryString = sortedEncodedQueryString(params);

        //Header
        String dateTimeStampStr = getEMPDateTimeStamp();
        String signatureStr = encryptingHMacSHA1(url + "?" + queryString + "\n" + dateTimeStampStr,  APP_SECRET);

        Map<String, String> headers = new HashMap();
        headers.put("Accept", "application/json");
        headers.put("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
        headers.put("x-lge-oauth-date", dateTimeStampStr);
        headers.put("x-lge-appkey", this.APP_KEY);
        headers.put("x-lge-oauth-signature", signatureStr);

        //Request
        HttpUtils httpUtils = buildCustomHttpUtils();
        HttpResponse response = httpUtils.makeRequest("DELETE", this.HOST + url + "?" + queryString, (String)null, headers);
        HttpEntity entity = response.getEntity();
        String responseText = EntityUtils.toString(entity);

        LOGGER.warn(CommonCodes.INTERFACE_COMMON_KEYWORD+"^^"+CommonCodes.INTERFACE_SYSTEM_EMP_SSO+"^^[FRM]EMPSSOClient^^expireToken^^headers^^"+headers.toString());
        LOGGER.warn(CommonCodes.INTERFACE_COMMON_KEYWORD+"^^"+CommonCodes.INTERFACE_SYSTEM_EMP_SSO+"^^[FRM]EMPSSOClient^^expireToken^^responseCode^^"+response.getStatusLine().getStatusCode()+"^^returnData^^"+ responseText);

        Map unmarshal = JsonUtils.unmarshal(responseText);
        EmpTokenValidationResponse validationResponse = JsonUtils.convertValue(unmarshal, EmpTokenValidationResponse.class);

        if (validationResponse.getError() != null) {
            throw new Exception(validationResponse.getError().getMessage());
        }
        return validationResponse;
    }

    /**
     * Call EMP SSO API(OTH.161)
     * @param accessToken
     * @return
     * @throws Exception
     */
    public EmpUserProfileResponse userProfile(String accessToken) throws Exception {

        String url = "/oauth/1.0/users/profile";

        //Header
        String dateTimeStampStr = getEMPDateTimeStamp();
        String signatureStr = encryptingHMacSHA1(url + "\n" + dateTimeStampStr,  APP_SECRET);

        Map<String, String> headers = new HashMap();
        headers.put("Accept", "application/json");
        headers.put("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
        headers.put("x-lge-oauth-date", dateTimeStampStr);
        headers.put("x-lge-appkey", this.APP_KEY);
        headers.put("x-lge-oauth-signature", signatureStr);
        headers.put("Authorization", "Bearer "+accessToken);

        //Request
        HttpUtils httpUtils = buildCustomHttpUtils();
        HttpResponse response = httpUtils.makeRequest("GET", this.HOST + url , (String)null, headers);
        HttpEntity entity = response.getEntity();
        String responseText = EntityUtils.toString(entity);

        LOGGER.warn(CommonCodes.INTERFACE_COMMON_KEYWORD+"^^"+CommonCodes.INTERFACE_SYSTEM_EMP_SSO+"^^[FRM]EMPSSOClient^^userProfile^^headers^^"+headers.toString());
        LOGGER.warn(CommonCodes.INTERFACE_COMMON_KEYWORD+"^^"+CommonCodes.INTERFACE_SYSTEM_EMP_SSO+"^^[FRM]EMPSSOClient^^userProfile^^responseCode^^"+response.getStatusLine().getStatusCode()+"^^returnData^^"+ responseText);

        Map unmarshal = JsonUtils.unmarshal(responseText);
        EmpUserProfileResponse userProfileResponse = JsonUtils.convertValue(unmarshal, EmpUserProfileResponse.class);

        //LOGGER.warn(userProfileResponse.toString());
        if (userProfileResponse.getError() != null) {
            throw new Exception(userProfileResponse.getError().getMessage());
        }
        return userProfileResponse;
    }

    private HttpUtils buildCustomHttpUtils(){

        HttpUtils httpUtils = new HttpUtils();
        httpUtils.setRequestConfig(RequestConfig.custom()
                .setConnectTimeout(this.CONNECT_TIME_OUT)
                .setSocketTimeout(this.SOCKET_TIME_OUT)
                .build());

        return httpUtils;
    }
    /**
     * Alphabet sort + URL Encode
     * @param map
     * @return
     * @throws UnsupportedEncodingException
     */
    public String sortedEncodedQueryString(Map<String, String> map) throws UnsupportedEncodingException {

        List<Map.Entry<String, String>> entries = new LinkedList<>(map.entrySet());
        Collections.sort(entries, (o1, o2) -> o1.getKey().compareTo(o2.getKey()));
        StringBuffer sb = new StringBuffer();
        List<String> listOfParams = new ArrayList<String>();

        LinkedHashMap<String, String> result = new LinkedHashMap<>();
        for (Map.Entry<String, String> entry : entries) {
            listOfParams.add(entry.getKey()+"="+ URLEncoder.encode(entry.getValue(),"UTF-8" ));
        }

        if (!listOfParams.isEmpty()) {
            String query = String.join("&", listOfParams);
            sb.append(query);
        }

        return sb.toString();
    }

    /**
     * EMP SSO HMacSHA1 암호화 + Base64 Encode
     * @param data
     * @param key
     * @return
     * @throws SignatureException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     */
    public String encryptingHMacSHA1(String data, String key) throws SignatureException, NoSuchAlgorithmException, InvalidKeyException {

        SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(), HMAC_SHA1_ALGORITHM);
        Mac mac = Mac.getInstance(HMAC_SHA1_ALGORITHM);
        mac.init(signingKey);
        byte[] rawHMac = mac.doFinal(data.getBytes());
        return new String(Base64.encodeBase64(rawHMac));
    }
}
