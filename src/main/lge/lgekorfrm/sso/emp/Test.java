package lgekorfrm.sso.emp;

import org.apache.commons.codec.binary.Base64;
import org.springframework.util.StringUtils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.SignatureException;
import java.util.HashMap;
import java.util.Map;

public class Test {

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

    public static void main(String[] args) {

        try {
String message = "Suitable Account[itg_trgt_user_no:123,itg_trgt_except_user_no:456,]";

            String keyUserNo = "itg_trgt_user_no";
            String itgTrgtUserNo = message.substring(message.indexOf(keyUserNo)+keyUserNo.length()+1, +message.indexOf(","));   //통합대상UserNo
            System.out.println(itgTrgtUserNo);
            String keyExceptUserNo = "itg_trgt_except_user_no";
            String itgTrgtExceptUserNo = message.substring(message.indexOf(keyExceptUserNo)+keyExceptUserNo.length()+1, +message.lastIndexOf(","));    //통합대상제외UserNo
            System.out.println(itgTrgtExceptUserNo);

/*
            //LGE 로그인 토큰
            String strLoginSessionID = "a73d1f450c1eae41976d30b114125b5c01600a272bbde057ba53b859270f63a1cfb1774aae1de7aabe2b280bdfd78747";
            //SNS 로그인 토큰
            //String strLoginSessionID = "7aa68c232790aa2fef0418a7f86a3c9c850c2631485e2f4d479eb23adb569074a9e3eebf758bc42004aeb03736e3648c";


            String strUrl = "http://qt2-kr.internal.emp.lgsmartplatform.com:20780/emp/v2.0/account/user/detail/";

            String strUnixTime = String.valueOf(System.currentTimeMillis() / 1000L);

            URL url = new URL(strUrl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setConnectTimeout(10000);      // 서버에 연결되는 Timeout 시간 설정 ///EMPApi 호출 시간 조정 : 5초->10초
            con.setReadTimeout(10000);         // InputStream 읽어 오는 Timeout 시간 설정
            con.setRequestMethod("GET");

            String signature = getHMacSHA256(strUnixTime, "MMSSPAVDBGE7WNHT6U1R13J1TBDU0RVT");

            con.setRequestProperty("X-Lge-Svccode", "SVC612");
            con.setRequestProperty("X-Application-Key ", "MMSSPAVDBGE7WNHT6U1R13J1TBDU0RVT");
            con.setRequestProperty("X-Signature", signature);
            con.setRequestProperty("X-Timestamp", strUnixTime);
            con.setRequestProperty("X-Device-Country", "KR");
            con.setRequestProperty("X-Device-Language", "ko-KR");
            con.setRequestProperty("X-Device-Language-Type", "IETF");
            con.setRequestProperty("X-Device-Publish-Flag", "N");
            con.setRequestProperty("X-Device-Type", "P01");
            con.setRequestProperty("X-Device-Platform", "PC");
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
            con.setRequestProperty("Accept", "application/json");

            Map<String, String> headerMap = new HashMap<String, String>();
            headerMap.put("X-Login-Session", strLoginSessionID);

            // API별 필수 Header Data 셋
            if (headerMap != null) {
                for (String key : headerMap.keySet()) {
                    String value = headerMap.get(key);
                    con.setRequestProperty(key, value);
                }
            }


            InputStream stream = con.getResponseCode() == HttpURLConnection.HTTP_OK ? con.getInputStream() : con.getErrorStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(stream, "utf-8"));
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
            br.close();


            System.out.println(con.getResponseCode());
            System.out.println(sb.toString());
*/

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
