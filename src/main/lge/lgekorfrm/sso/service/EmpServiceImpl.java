package lgekorfrm.sso.service;

import devonframe.configuration.ConfigService;
import lgekorfrm.code.CommonCodes;
import lgekorfrm.sso.emp.EmpIntegrateResponse;
import lgekorfrm.sso.util.HttpUtils;
import lgekorfrm.sso.util.JsonUtils;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.SignatureException;
import java.util.*;

@Service("EmpService")
public class EmpServiceImpl implements EmpService{

    private static final Logger LOGGER = LoggerFactory.getLogger(EmpServiceImpl.class);

    @Resource(name = "configService")
    ConfigService configService;

    /**
     * ThinQ앱 <-> 한영본앱 통합 여부 조회 (MR.024)
     * GET /account/user/matching/integrated
     * param itg_user_type=B&id_tp_code=LGE&src_svc_code=SVC202&svc_code_list=SVC612&user_no=KR2012334421
     * { "account": { "country": "KR", // 계정의 국가 "usrNo": "KR202009080001", // EMP 회원번호 "svcUsrId": "30caa201b60aa774da894380d0f00007" // 통합 서비스 번호 } }
     * { "error" : { "request" : “/emp/v2.0/account/user/matching/integrated”, "code" : " MR.024.24", "message" : " Suitable Account[itg_trgt_user_no:통합대상UserNo, itg_trgt_except_user_no:통합대상제외UserNo] } }
     * @param
     * @return
     */
    @Override
    public EmpIntegrateResponse callEmpAccountMatchingIntegrate(String thinqIdTpCode, String thinqEmpUserNo) throws Exception{
        EmpIntegrateResponse integrateResponse = null;

        String empHost = configService.getString("empHost");
        String empAppKey = configService.getString("empAppKey");
        String empPublishFlag = configService.getString("empPublishFlag");

        String integrateIdUrl = empHost + "/emp/v2.0/account/user/matching/integrated";

        Map<String, String> param = new HashMap<>();

        param.put("id_tp_code", thinqIdTpCode);
        param.put("itg_user_type", "B");    //한영본
        param.put("svc_list", "SVC612");    //한영본
        param.put("src_svc_code", "SVC202");    //ThinQ
        param.put("user_no", thinqEmpUserNo);

        String strUnixTime = String.valueOf(System.currentTimeMillis() / 1000L);
        String signature = encryptHMacSHA256(strUnixTime, empAppKey);

        Map<String, String> headers = new HashMap();
        headers.put("Accept", "application/json");

        headers.put("X-Lge-Svccode", "SVC612");
        headers.put("X-Application-Key ", empAppKey);
        headers.put("X-Signature", signature);
        headers.put("X-Timestamp", strUnixTime);
        headers.put("X-Device-Country", "KR");
        headers.put("X-Device-Language", "ko-KR");
        headers.put("X-Device-Language-Type", "IETF");
        headers.put("X-Device-Publish-Flag", empPublishFlag);   //Y:운영, N:개발
        headers.put("X-Device-Type", "P01");
        headers.put("X-Device-Platform", "PC");
        headers.put("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
        headers.put("Accept", "application/json");

        HttpUtils httpUtils = new HttpUtils();
        httpUtils.setRequestConfig(RequestConfig.custom()
                .setConnectTimeout(5000)
                .setSocketTimeout(10000)
                .build());

        //HttpResponse response = httpUtils.makeRequest("POST", integrateIdUrl, empSsoClient.sortedEncodedQueryString(param), headers);
        HttpResponse response = httpUtils.makeRequest("GET", integrateIdUrl + "?" + sortedEncodedQueryString(param), (String) null, headers);
        org.apache.http.HttpEntity entity = response.getEntity();
        String responseText = EntityUtils.toString(entity);
        LOGGER.warn(CommonCodes.INTERFACE_COMMON_KEYWORD+"^^"+CommonCodes.INTERFACE_SYSTEM_EMP+"^^[FRM]EmpServiceImpl^^integrated^^param^^"+integrateIdUrl + "?" + sortedEncodedQueryString(param));
        LOGGER.warn(CommonCodes.INTERFACE_COMMON_KEYWORD+"^^"+CommonCodes.INTERFACE_SYSTEM_EMP+"^^[FRM]EmpServiceImpl^^integrated^^headers^^"+headers.toString());
        LOGGER.warn(CommonCodes.INTERFACE_COMMON_KEYWORD+"^^"+CommonCodes.INTERFACE_SYSTEM_EMP+"^^[FRM]EmpServiceImpl^^integrated^^responseCode^^"+response.getStatusLine().getStatusCode()+"^^returnData^^"+ responseText);
        Map unmarshal = JsonUtils.unmarshal(responseText);

        integrateResponse = JsonUtils.convertValue(unmarshal, EmpIntegrateResponse.class);

        return integrateResponse;
    }

    /**
     * EMP encryption SHA256
     * @param data
     * @param key
     * @return
     * @throws SignatureException
     */
    private String encryptHMacSHA256(String data, String key) throws SignatureException {
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

    /**
     * Alphabet sort + URL Encode
     * @param map
     * @return
     * @throws UnsupportedEncodingException
     */
    private String sortedEncodedQueryString(Map<String, String> map) throws UnsupportedEncodingException {

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
}
