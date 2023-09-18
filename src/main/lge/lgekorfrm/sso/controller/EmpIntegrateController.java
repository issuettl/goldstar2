package lgekorfrm.sso.controller;

import devonframe.configuration.ConfigService;
import devonframe.util.NullUtil;
import lgekorfrm.code.CommonCodes;
import lgekorfrm.sso.emp.EmpAccessTokenResponse;
import lgekorfrm.sso.emp.EmpIntegrateResponse;
import lgekorfrm.sso.emp.EmpSsoClient;
import lgekorfrm.sso.emp.EmpUserProfileResponse;
import lgekorfrm.sso.member.MemberInformation;
import lgekorfrm.sso.service.EmpService;
import lgekorfrm.sso.util.AES256Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

@Controller
@PropertySources(@PropertySource("classpath:/config/project.properties"))

@RequestMapping("/kr")
public class EmpIntegrateController implements InitializingBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmpIntegrateController.class);

    @Resource(name = "configService")
    ConfigService configService;

    @Resource(name = "EmpService")
    EmpService empService;


    private String empSsoHost;
    private String empSsoAppKey;
    private String empSsoAppSecret;
    private String empSsoLoginCallbackUri;
    private EmpSsoClient empSsoClient;

    @Override
    public void afterPropertiesSet() throws Exception {

        this.empSsoHost = configService.getString("empSsoHost");
        this.empSsoAppKey = configService.getString("empSsoAppKey");
        this.empSsoAppSecret = configService.getString("empSsoAppSecret");
        this.empSsoLoginCallbackUri = configService.getString("empSsoLoginCallbackUri");
        this.empSsoClient = new EmpSsoClient(empSsoHost, empSsoAppKey, empSsoAppSecret);
    }


    /**
     * ThinQ와 LGE.com ID 통합 프로세스. - ThinQ 에서 deeplink 로 넘어오는 경우에 호출됨.
     * ID 통합 여부와 CI 정보 비교에 따라 다음 process 가 결정됨.
     * popup1 - ThinQ에 가입하신 계정과 LGE.com 에 가입하신 계정을 연결하시겠습니까? 연결하시면 LG가 제공하는 다양한 서비스를 편리하게 이용하실 수 있습니다.
     * popup2 - ThinQ와 LGE.com에 연결된 계정은 AAA@gmail.com 입니다.
     * popup3 - LGE.com 로그인 정보와 ThinQ 앱 로그인 정보가 다르기 때문에 멤버십 포인트는 ThinQ 앱에서 보여지는 정보와 다릅니다.
     * popup4 - LGE.com 회원이 아닙니다. ThinQ 회원정보로 LGE.com에 가입 하시겠습니까?
     * @param request
     * @param response
     * @param input
     * @return
     * @throws Exception
     */
    //@RequestMapping(value = "/sso/api/checkIntegrateId" ,  method = RequestMethod.POST)
    @RequestMapping(value = "/sso/api/checkIntegrateId")
    @ResponseBody
    public Map<String, Object> checkIntegrateId(HttpServletRequest request, HttpServletResponse response, @RequestParam Map<String, Object> input) throws Exception {
        Map<String, Object> returnData = new HashMap<>();

        writeIntegrateLog(request, String.format("ThinQ-checkIntegrateId param : %s",input.toString()));
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("integrateType", "none");

        try {
            String amSsoId = readAPPData(input.get("sso_id"), true);
            String amIdTpCode = readAPPData(input.get("id_tp_code"), false);
            String amThinqMbrNo = readAPPData(input.get("thinq_mbrno"), true);
            String amThinqCI = readAPPData(input.get("ci"), true);

            //ThinQ 에서 A/M 에 저장한 데이터인 경우
            if (NullUtil.notNone(amSsoId) && NullUtil.notNone(amIdTpCode) && NullUtil.notNone(amThinqMbrNo)) {

                EmpIntegrateResponse integrateResponse = empService.callEmpAccountMatchingIntegrate(amIdTpCode, amThinqMbrNo);
                MemberInformation sessionMember = (MemberInformation) request.getSession().getAttribute("MemberInformation");

                //1) 통합된 회원인 경우
                if(integrateResponse !=null && integrateResponse.isIntegrated()){
                    EmpAccessTokenResponse ssoTokenResponse = null;
                    try {
                        //writeIntegrateLog(request, String.format("sso_id : %s", amSsoId));
                        ssoTokenResponse = empSsoClient.getTokenBySsoId(amSsoId);
                    }catch(Exception e){
                        writeIntegrateLog(request, String.format("ThinQ-Can not get access_token from ThinQ SSO_ID : %s",amSsoId));
                    }

                    //통합계정&로그인 인 경우 통합계정 CI 와 현재 로그인 CI 비교
                    if (sessionMember != null && ssoTokenResponse != null) {

                        String accessToken = ssoTokenResponse.getAccess_token();
                        String amCi = readAPPData(input.get("ci"), true);

                        //ThinQ 와 LGE.com CI 가 다를 경우 통합된 user ID 를 보여준다.
                        if (!amCi.equals(sessionMember.getCi())) {

                            EmpUserProfileResponse userProfileResponse = empSsoClient.userProfile(accessToken);
                            String displayUserId = userProfileResponse.getAccount().getDisplayUserID();
                            dataMap.put("integrateType", "popup2");
                            dataMap.put("displayUserId", displayUserId);

                            writeIntegrateLog(request, String.format("ThinQ-case2 LGE.com.userId :%s, emp.displayUserId : %s", sessionMember.getUserId(), displayUserId));
                        }
                    }

                    //2) 통합되지 않은 회원인 경우
                }else{
                    //통합 완료/취소시 CI 비교를 위해 세션에 ThinQ CI 저장
                    request.getSession().setAttribute("SSO_THINQ_CI", amThinqCI);

                    //통합가능한 계정인 경우
                    if(integrateResponse !=null && integrateResponse.availableIntegrate()) {
                        String itgTrgtUserNo = integrateResponse.getItgTrgtUserNo();
                        String itgTrgtExceptUserNo = integrateResponse.getItgTrgtExceptUserNo();

                        dataMap.put("integrateType", "popup1");
                        dataMap.put("itgTrgtUserNo", itgTrgtUserNo);
                        dataMap.put("itgTrgtExceptUserNo", itgTrgtExceptUserNo);

                        writeIntegrateLog(request, String.format("ThinQ-case1 itgTrgtUserNo :%s, itgTrgtExceptUserNo : %s", itgTrgtUserNo, itgTrgtExceptUserNo));
                        //한영회 서비스 추가가입이 필요한 계정인 경우
                    }else if(integrateResponse !=null && integrateResponse.needSignUp()){
                        dataMap.put("integrateType", "popup4");

                        writeIntegrateLog(request, String.format("ThinQ-case4 "));
                    }

                }

            }
        }catch(Exception e){
            writeIntegrateLog(request, String.format("ThinQ-checkIntegrateId-Exception : %s", e.getMessage()));
        }
        returnData.put("status" , CommonCodes.JSON_RESPONSE_SUCCESS_MESSAGE);
        returnData.put("message", null);
        returnData.put("data"   , dataMap);

        return returnData;
    }

    /**
     * EMP ID 통합 화면으로 redirect
     * @param request
     * @param response
     * @param input
     * @throws Exception
     */
    //@RequestMapping(value = "/sso/api/integrateId" ,  method = RequestMethod.POST)
    @RequestMapping(value = "/sso/api/integrateId")
    public void integrateId(HttpServletRequest request, HttpServletResponse response, @RequestParam Map<String, Object> input) throws Exception {
        String state = "";
        if(input.get("state") != null){
            state = (String)input.get("state");
            state = URLEncoder.encode(state,"UTF-8");
        }
        String svcUserNo = readAPPData(input.get("thinq_mbrno"), true);
        String idTpCode = readAPPData(input.get("id_tp_code"), false);
        String itgTrgtUserNo = (String)input.get("itgTrgtUserNo");
        String itgTrgtExceptUserNo = (String)input.get("itgTrgtExceptUserNo");


        String callbackUrl = configService.getString("serverInfo.url")+"/sso/api/integrateIdCallback?state="+state;
        callbackUrl = URLEncoder.encode(callbackUrl,"UTF-8");

        StringBuffer empFrontUrl = new StringBuffer(configService.getString("integrateIdEMPFrontUri"));
        empFrontUrl.append("?id_tp_code=").append(idTpCode);
        empFrontUrl.append("&svc_user_no=").append(svcUserNo);
        empFrontUrl.append("&itg_trgt_user_no=").append(itgTrgtUserNo);
        empFrontUrl.append("&itg_trgt_except_user_no=").append(itgTrgtExceptUserNo);
        empFrontUrl.append("&country=KR&svc_list=SVC612&itg_user=B&user_id=&itg_status=24&&itg_svc_user_no=division=KR:C99");
        empFrontUrl.append("&callback_url=").append(callbackUrl);

        writeIntegrateLog(request, String.format("ThinQ-integrateId empFrontUrl : %s", empFrontUrl));

        response.sendRedirect(empFrontUrl.toString());
    }

    /**
     * EMP ID 통합 callback
     * EMP 에서 정상적으로 통합된 경우 returnCode : 0
     * @param request
     * @param response
     * @param input
     * @throws Exception
     */
    @RequestMapping(value = "/sso/api/integrateIdCallback")
    public void integrateIdCallback(HttpServletRequest request, HttpServletResponse response, @RequestParam Map<String, Object> input) throws Exception {
        String returnCode = (String)input.get("returnCode");
        String state = "";
        if(input.get("state") != null){
            state = (String)input.get("state");
            state = URLDecoder.decode(state,"UTF-8");
        }

        writeIntegrateLog(request, String.format("ThinQ-integrateIdCallback returnCode : %s, state : %s", returnCode, state));

        //정상 통합된 경우 로그인 페이지로 이동
        if(returnCode.equals("0")){
            //세션초기화
            request.getSession().removeAttribute("ACCESS_TOKEN");
            request.getSession().removeAttribute("REFRESH_TOKEN");
            request.getSession().removeAttribute("UNIFY_ID");
            request.getSession().removeAttribute("MemberInformation");
            request.getSession().removeAttribute("OBS_CARTID");
            request.getSession().removeAttribute("customerToken");
            request.getSession().removeAttribute("customerTokenExpire");
            request.getSession().removeAttribute("OBSCART_CNT");

            response.sendRedirect("/sso/api/emp/Login?state="+state);
        }else{
            //취소 시 다시 팝업띄우는걸 방지하기 위헤 src_svc_code 파라미터 제거
            if(state.contains("src_svc_code")){
                state = state.replace("src_svc_code","old_svc_code");
            }

            MemberInformation sessionMember = (MemberInformation) request.getSession().getAttribute("MemberInformation");

            //로그인된 경우
            if(sessionMember != null){
                if (state.contains("?")) {
                    response.sendRedirect(state + "&integrateIdCancel=true");
                } else {
                    response.sendRedirect(state + "?integrateIdCancel=true");
                }
            }else{
                state = state + URLEncoder.encode("?integrateIdCancel=true");
                response.sendRedirect("/sso/api/emp/Login?state="+state);

            }

        }
    }

    /**
     * ID 통합유도 취소
     * @param request
     * @param response
     * @param input
     * @return
     * @throws Exception
     */
    //@RequestMapping(value = "/sso/api/integrateIdCancel" ,  method = RequestMethod.POST)
    @RequestMapping(value = "/sso/api/integrateIdCancel")
    @ResponseBody
    public Map<String, Object> integrateIdCancel(HttpServletRequest request, HttpServletResponse response, @RequestParam Map<String, Object> input) throws Exception {
        Map<String, Object> returnData = new HashMap<>();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("integrateType", "none");

        //String amCi = readAPPData(input.get("ci"));
        if(request.getSession().getAttribute("SSO_THINQ_CI") != null){
            String amCi = (String)request.getSession().getAttribute("SSO_THINQ_CI");
            MemberInformation sessionMember = (MemberInformation)request.getSession().getAttribute("MemberInformation");

            //ThinQ CI 와 LGE.com CI 가 다른경우
            if(sessionMember!=null && !amCi.equals(sessionMember.getCi())){
                dataMap.put("integrateType", "popup3");
            }
        }

        returnData.put("status" , CommonCodes.JSON_RESPONSE_SUCCESS_MESSAGE);
        returnData.put("message", null);
        returnData.put("data"   , dataMap);
        return returnData;
    }



    /**
     * sso_id 로 재로그인 처리.
     * @param request
     * @param response
     * @param input
     * @return
     * @throws Exception
     */
    //@RequestMapping(value = "/sso/api/integrateLogin" ,  method = RequestMethod.POST)
    @RequestMapping(value = "/sso/api/integrateLogin")
    public void integrateLogin(HttpServletRequest request, HttpServletResponse response, @RequestParam Map<String, Object> input) throws Exception {

        String state = (String)input.get("state");
        String amSsoId = readAPPData(input.get("sso_id"), true);
        writeIntegrateLog(request, String.format("ThinQ-integrateLogin sso_id : %s", amSsoId));

        if(NullUtil.notNone(amSsoId)) {
            EmpAccessTokenResponse ssoTokenResponse = empSsoClient.getTokenBySsoId(amSsoId);
            if (ssoTokenResponse != null) {
                String accessToken = ssoTokenResponse.getAccess_token();
                String refreshToken = ssoTokenResponse.getRefresh_token();

                // 세션 초기화
                request.getSession().removeAttribute("ACCESS_TOKEN");
                request.getSession().removeAttribute("REFRESH_TOKEN");
                request.getSession().removeAttribute("UNIFY_ID");
                request.getSession().removeAttribute("MemberInformation");
                request.getSession().removeAttribute("OBS_CARTID");
                request.getSession().removeAttribute("customerToken");
                request.getSession().removeAttribute("customerTokenExpire");

                // OBS CART 수량 초기화 ( 회원 CART ID로 재저장 필요 )
                request.getSession().removeAttribute("OBSCART_CNT");

                // 쿠키에 accessToken저장
                Cookie cookie = new Cookie("ACCESS_TOKEN", accessToken);
                cookie.setDomain(CommonCodes.SSO_TOKEN_DOMAIN);
                cookie.setPath("/");
                // cookie.setSecure(true);
                response.addCookie(cookie);

                Cookie rfcookie = new Cookie("REFRESH_TOKEN", refreshToken);
                rfcookie.setDomain(CommonCodes.SSO_TOKEN_DOMAIN);
                rfcookie.setPath("/");
                rfcookie.setMaxAge(60*60*24*30); //한달로 설정
                response.addCookie(rfcookie);

                writeIntegrateLog(request, String.format("ThinQ-integrateLogin accessToken : %s, refreshToken : %s", accessToken, refreshToken));
            }
        }
        //계정 전환 후 다시 팝업띄우는걸 방지하기 위헤 src_svc_code 파라미터 제거
        if(state.contains("src_svc_code")){
            state = state.replace("src_svc_code","old_svc_code");
        }
        response.sendRedirect(state);
    }

    /**
     * APP -> WEB 복호화.
     * @param obj
     * @param encrypted - 암호화 여부
     * @return
     */
    private String readAPPData(Object obj, boolean encrypted){
        String decryptedStr = null;

        try {
            if(obj != null) {
                if(!obj.toString().equals("-1") && !obj.toString().equals("-2") && !obj.toString().equals("-3")){
                    if(encrypted) {
                        String cryptoKey = configService.getString("APPAES256Key");
                        AES256Util aes256 = new AES256Util(cryptoKey);
                        decryptedStr = aes256.decrypt(obj.toString());
                    }else{
                        decryptedStr = obj.toString();
                    }
                }
            }
        }catch(Exception e){
            LOGGER.error(e.getMessage());
        }

        return decryptedStr;
    }


    private void writeIntegrateLog(HttpServletRequest request, String message){
        StringBuilder messageBuilder = new StringBuilder();
        if(request.getHeader("x-amzn-trace-id") != null){
            messageBuilder.append(" x-amzn-trace-id : ").append(request.getHeader("x-amzn-trace-id")).append(" | ");
        }
        messageBuilder.append(message);
        LOGGER.warn(messageBuilder.toString());

    }
}
