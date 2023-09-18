package lgekorfrm.sso.emp;

import lgekorfrm.sso.util.HttpUtils;
import lgekorfrm.sso.util.JsonUtils;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmpSsoTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmpSsoTest.class);

    private static String EMP_HOST = "https://qa-kr.lgeapi.com";
    private static String APP_KEY = "b753ec52505145f68f4adcc7f46eedb9";
    private static String APP_SECRET = "0a1e31e96ce7475796d980a1d5e4033a";
    private static String REDIRECT_URI = "http://test.lge.co.kr:81/sso/api/emp/callbackLogin";


    public static void main(String[] args) {

        /** ID, PW 입력 **/
        String userId = "";
        String passwd = "";

        String empSessionStr = "";
        String empUserId = "";
        try {

            EmpSsoClient empSsoClient = new EmpSsoClient(EMP_HOST, APP_KEY, APP_SECRET);


            /** 1. EMP Login on 회원통합

            String strUrl = "http://qt2-kr.emp.lgsmartplatform.com/emp/v2.0/account/session/" + userId;

            Map<String,String> bodyMap = new HashMap<>();
            bodyMap.put("svc_list", "SVC612");
            bodyMap.put("user_auth2", getEmpSha512(passwd));
            bodyMap.put("itg_user_type", "B"); // 한영회
            bodyMap.put("itg_terms_use_flag", "Y"); // Y:로그인시 재동의 대상 약관에 통합약관 포함

            String strUnixTime = String.valueOf(System.currentTimeMillis() / 1000L);
            String signature = getHMacSHA256(strUnixTime, "MMSSPAVDBGE7WNHT6U1R13J1TBDU0RVT");

            Map<String, String> headers = new HashMap();
            headers.put("Accept", "application/json");

            headers.put("X-Lge-Svccode", "SVC612");
            headers.put("X-Application-Key ", "MMSSPAVDBGE7WNHT6U1R13J1TBDU0RVT");
            headers.put("X-Signature", signature);
            headers.put("X-Timestamp", strUnixTime);
            headers.put("X-Device-Country", "KR");
            headers.put("X-Device-Language", "ko-KR");
            headers.put("X-Device-Language-Type", "IETF");
            headers.put("X-Device-Publish-Flag", "N");   //개발
            headers.put("X-Device-Type", "P01");
            headers.put("X-Device-Platform", "PC");
            headers.put("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
            headers.put("Accept", "application/json");

            HttpUtils httpUtils = new HttpUtils();
            HttpResponse response = httpUtils.makeRequest("POST", strUrl, empSsoClient.sortedEncodedQueryString(bodyMap), headers);
            HttpEntity entity = response.getEntity();
            String responseText = EntityUtils.toString(entity);

            Map unmarshal = JsonUtils.unmarshal(responseText);
            if(unmarshal.get("account") != null){
                Map accountMap = (Map)unmarshal.get("account");
                empSessionStr = (String)accountMap.get("loginSessionID");
                empUserId = (String)accountMap.get("userID");
                System.out.println("loginSessionID:"+empSessionStr);
                System.out.println("empUserId:"+empUserId);
            }
             **/


            /** 2. EMP SSO - OAuth Authorization Code Flow on 회원통합
            String redirectUri = empSsoClient.requestAuthorizationCode(empSessionStr, empUserId, REDIRECT_URI);
            redirectUri = URLDecoder.decode(redirectUri, "UTF-8");
            System.out.println("callbackUri : " + redirectUri);

            String authorizationCode = "";
            Pattern p = Pattern.compile("http.*\\&code=([a-zA-Z0-9_\\-]+)(?:&.)*");
            Matcher m = p.matcher(redirectUri.trim());
            if (m.find()) {
                authorizationCode = m.group(1);
            }
            System.out.println("authorizationCode : " + authorizationCode);
             **/

            // 여기서부터 LGE.com 에서 처리해야할 내용
            /** 3. Get Access Token on LGE.com login callback
            String ssoId = UUID.randomUUID().toString();    //receive from APP
            System.out.println(ssoId);
            EmpAccessTokenResponse accessTokenResponse = empSsoClient.requestToken("AUTHORIZATION_CODE", authorizationCode, REDIRECT_URI, null);
            System.out.println("requestToken : " + accessTokenResponse.toString());

            String accessToken = accessTokenResponse.getAccess_token();
            String refreshToken = accessTokenResponse.getRefresh_token();
             **/

            /** 3-1. Get Access Token by SSO_ID
            EmpAccessTokenResponse accessSsoTokenResponse = empSsoClient.getTokenBySsoId(ssoId);
            System.out.println("accessSsoToken : " + accessSsoTokenResponse.toString());
             **/


            /** 4. User Profile
            EmpUserProfileResponse userProfileResponse = empSsoClient.userProfile(accessToken);
            if(userProfileResponse != null) {
                System.out.println("userProfile : " + userProfileResponse.toString());
            }**/

            /** 5. Expire Access Token **
            EmpTokenValidationResponse eResponse = empSsoClient.expireToken(accessToken);
            System.out.println("expireToken : " + eResponse.toString());


            /** 6. Validate Access Token
            try {
                EmpTokenValidationResponse validationResponse = empSsoClient.validateToken(accessToken);
                System.out.println("validateToken : " + validationResponse.toString());

                if (validationResponse.getExpireTime() < 1800L) {
                    EmpAccessTokenResponse refreshResponse = empSsoClient.requestToken("REFRESH_TOKEN", refreshToken, REDIRECT_URI, null);
                    System.out.println("refreshToken : " + refreshResponse.toString());
                }
            }catch(Exception te){
                EmpAccessTokenResponse eRefreshResponse = empSsoClient.requestToken("REFRESH_TOKEN", refreshToken, REDIRECT_URI, null);
                System.out.println("refreshToken : " + eRefreshResponse.toString());
            }***/


            /*
            String accessToken = "4fd61cbe236960021656078baef692b30184c4ad08f6c777c824d9e76e8f80bf6f42c2941b5ff08eec2cf50363fd6563";
            EmpTokenValidationResponse validationResponse = empSsoClient.validateToken(accessToken);
            System.out.println("validateToken : " + validationResponse.toString());
            */

            String integrateIdUrl = "http://qt2-kr.emp.lgsmartplatform.com/emp/v2.0/account/user/matching/integrated";

            Map<String,String> param = new HashMap<>();

            param.put("id_tp_code", "LGE");
            param.put("itg_user_type", "B");    //한영본
            param.put("svc_list", "SVC612");
            param.put("user_no", "KR2UN0903000299");
            //param.put("user_no", "KR2203316545692");    //이승기 ThinQ

            param.put("src_svc_code", "SVC202");    //ThinQ

            String strUnixTime = String.valueOf(System.currentTimeMillis() / 1000L);
            String signature = getHMacSHA256(strUnixTime, "MMSSPAVDBGE7WNHT6U1R13J1TBDU0RVT");

            Map<String, String> headers = new HashMap();
            headers.put("Accept", "application/json");

            headers.put("X-Lge-Svccode", "SVC612");
            headers.put("X-Application-Key ", "MMSSPAVDBGE7WNHT6U1R13J1TBDU0RVT");
            headers.put("X-Signature", signature);
            headers.put("X-Timestamp", strUnixTime);
            headers.put("X-Device-Country", "KR");
            headers.put("X-Device-Language", "ko-KR");
            headers.put("X-Device-Language-Type", "IETF");
            headers.put("X-Device-Publish-Flag", "N");   //개발
            headers.put("X-Device-Type", "P01");
            headers.put("X-Device-Platform", "PC");
            headers.put("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
            headers.put("Accept", "application/json");

            System.out.println(integrateIdUrl + "?" + empSsoClient.sortedEncodedQueryString(param));
            System.out.println(headers.toString());
            HttpUtils httpUtils = new HttpUtils();
            //HttpResponse response = httpUtils.makeRequest("POST", integrateIdUrl, empSsoClient.sortedEncodedQueryString(param), headers);
            HttpResponse response = httpUtils.makeRequest("GET", integrateIdUrl + "?" + empSsoClient.sortedEncodedQueryString(param), (String)null, headers);
            HttpEntity entity = response.getEntity();
            String responseText = EntityUtils.toString(entity);
            System.out.println(responseText);
            Map unmarshal = JsonUtils.unmarshal(responseText);

            if(unmarshal.get("error") != null){
                Map errorMap = (Map)unmarshal.get("error");
                System.out.println("Error:"+errorMap.toString());

            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static String ase256Encrypt(String str) throws NoSuchAlgorithmException,
            GeneralSecurityException, UnsupportedEncodingException {

        String iv;
        Key keySpec;
        String key ="b0fdac4f718d71a02d796f8fe3e12983";

        iv = key.substring(0, 16);
        byte[] keyBytes = new byte[16];
        byte[] b = key.getBytes("UTF-8");
        int len = b.length;
        if (len > keyBytes.length) {
            len = keyBytes.length;
        }
        System.arraycopy(b, 0, keyBytes, 0, len);
        SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, "AES");

        keySpec = secretKeySpec;

        Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
        c.init(Cipher.ENCRYPT_MODE, keySpec, new IvParameterSpec(iv.getBytes()));
        byte[] encrypted = c.doFinal(str.getBytes("UTF-8"));
        String enStr = new String(Base64.encodeBase64(encrypted));
        return enStr;
    }

    public static String getHMacSHA256(String data, String key) throws SignatureException {
        String hmac;
        try {
            SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(), "HmacSHA256");
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(signingKey);
            byte[] rawHmac = mac.doFinal(data.getBytes());
            hmac = new String(Base64.encodeBase64(rawHmac));
        } catch (Exception e) {
            throw new SignatureException("Failed to generate HMAC : ");
        }
        return hmac;
    }

    public static String getEmpSha512(String password) throws Exception {

        StringBuffer sb = new StringBuffer();

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(password.getBytes());
            byte[] digest = md.digest();

            String hex;
            for (byte aDigest : digest) {
                hex = Integer.toHexString(0xFF & aDigest);

                if (hex.length() < 2) {
                    sb.append("0");
                }
                sb.append(hex);
            }
        } catch (Exception e) {
            return null;
        }

        return sb.toString();

    }
}
