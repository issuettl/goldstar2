package lgekorfrm.filter;

import com.nimbusds.jwt.JWTClaimsSet;
import devonframe.configuration.ConfigService;
import devonframe.util.NullUtil;
import lgekorfrm.code.CommonCodes;
import lgekorfrm.member.service.MemberService;
import lgekorfrm.obs.service.ObsService;
import lgekorfrm.sso.emp.EmpAccessTokenResponse;
import lgekorfrm.sso.emp.EmpSsoClient;
import lgekorfrm.sso.emp.EmpTokenValidationResponse;
import lgekorfrm.sso.emp.EmpUserProfileResponse;
import lgekorfrm.sso.member.MemberInformation;
import lgekorfrm.sso.util.HttpUtils;
import lgekorfrm.sso.util.JsonUtils;
import lgekorfrm.sso.util.JwtUtils;
import lgekorfrm.util.ServletUtils;
import net.minidev.json.JSONObject;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Component("EmpSsoMemberInfoSessionChkFilter")
@PropertySources(@PropertySource("classpath:/config/project.properties"))

public class EmpSsoMemberInfoSessionChkFilter implements Filter {

    private Logger LOGGER = LoggerFactory.getLogger(EmpSsoMemberInfoSessionChkFilter.class);

    SimpleDateFormat tokenIssuedDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");

    @Resource(name = "configService")
    ConfigService configService;

    @Resource(name = "MemberService")
    MemberService memberService;

    @Resource(name = "ObsService")
    private ObsService obsService;

    public EmpSsoMemberInfoSessionChkFilter() {
        // TODO Auto-generated constructor stub
    }

    @Override
    public void destroy() {
        // TODO Auto-generated method stub
    }

    @Override
    public void init(FilterConfig fConfig) throws ServletException {
        // TODO Auto-generated method stub
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String servletPath = request.getServletPath();

        // MemberInfoSessionChkFilter 필수 uri
        List<Object> requiredList = configService.getList("requiredList");
        // MemberInfoSessionChkFilter 예외 uri
        List<Object> exceptionList = configService.getList("exceptionList");

        /** S1. health check url(/health) 인 경우 MemberInfoSessionChkFilter 생략 **/
        if ("/health".equals(request.getRequestURI())) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        /** S2. staticResource 이거나 OPTIONS method인 경우 MemberInfoSessionChkFilter 생략 **/
        boolean isStaticResource = request.getRequestURI().startsWith("/lg5-common/");
        boolean isStaticResource2 = ServletUtils.isWebResource(request);

        if (isStaticResource2 || isStaticResource || request.getMethod().equals(HttpMethod.OPTIONS.toString())) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        // User Agent 로 Mobile APP 판단( 문자열 LGEAPP 포함)
        String userAgent = (request.getHeader("user-agent") != null)? request.getHeader("user-agent") : "";
        String autoLoginCookie = getCookie(request, "AUTO_LOGIN");
        boolean isAutoLogin = ("N".equals(autoLoginCookie))? false:true;   //if empty/null is true.
        boolean isMobileApp = userAgent.contains("LGEAPP")? true : false;

        /** old SSO 토큰인 경우 강제 로그아웃 처리 **/
        String oldAccessToken = getCookie(request, "ACCESS_TOKEN");;
        String oldRefreshToken = getCookie(request, "REFRESH_TOKEN");
        if(oldRefreshToken.length() > 0 && oldRefreshToken.length() <= 36 ){

            writeFilterDebugLog(request, String.format(
                    "EMPSSO forceLogout : Refresh Token: %s, Access Token: %s, isMobileApp: %s",
                    oldRefreshToken, oldAccessToken, isMobileApp));

            removeAllSessionAndCookie(request, response, false, false); //강제삭제

            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        /** S3. ajax, api 패턴은 필터 생략 **/
        if(servletPath.contains("ajax")||servletPath.contains("/api")||servletPath.contains("/gnb")|| servletPath.contains("/footer")||servletPath.contains("/breadCrumb")
                ||servletPath.contains("/mobileGnb")){
            //commonmodule/memberInfo 요청시 refresh 토큰만 존재할 경우 갱신처리
            if(servletPath.contains("/commonmodule/memberInfo") && isMobileApp && isAutoLogin){

                MemberInformation memberInformation = (MemberInformation) request.getSession().getAttribute("MemberInformation");

                String accessTokenA = getCookie(request, "ACCESS_TOKEN");
                String refreshTokenA = getCookie(request, "REFRESH_TOKEN");
                String ssoIdA = getCookie(request, "sso_id");

                //ACCESS_TOKEN 이 만료되었거나, REFRESH_TOKEN, sso_id 가 있지만 로그인 안된 경우 필터 수행
                if( ! ((getExpiresIn(request, accessTokenA) <= 0
                        || memberInformation == null && NullUtil.notNone(refreshTokenA))
                        || memberInformation == null && NullUtil.notNone(ssoIdA))){
                    filterChain.doFilter(servletRequest, servletResponse);
                    return;
                }

            }else{
                filterChain.doFilter(servletRequest, servletResponse);
                return;
            }
        }

        /** S4. MemberInfoSessionChkFilter 예외 URL 필터 생략 **/
        // MemberInfoSessionChkFilter 제외 여부
        boolean isFilterExcludedURI = loginExceptionList(request.getRequestURI(), exceptionList);
        if(isFilterExcludedURI){
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }


        String clientId = configService.getString("ssoClientId");

        //EMP SSO 설정
        String empSsoHost = configService.getString("empSsoHost");
        String empSsoAppKey = configService.getString("empSsoAppKey");
        String empSsoAppSecret = configService.getString("empSsoAppSecret");
        String empSsoLoginCallbackUri = configService.getString("empSsoLoginCallbackUri");
        EmpSsoClient empSsoClient = new EmpSsoClient(empSsoHost, empSsoAppKey, empSsoAppSecret);

        boolean isLoggedIn = false;
        boolean isTokenValidationException = false;
        boolean isTokenRefreshException = false;
        //Mobile APP 에서 자동로그인 된 경우 (약관 재동의용)
        boolean isAutoLoginViaMobileApp = false;

        /** L1. Session 에서 Token, UnifyId, CustomerToken, CustomerTokenExpire 추출 **/
        String accessToken = (String) request.getSession().getAttribute("ACCESS_TOKEN");
        String refreshToken = (String) request.getSession().getAttribute("REFRESH_TOKEN");
        String unifyId = (String) request.getSession().getAttribute("UNIFY_ID");
        String customerToken = (String) request.getSession().getAttribute("customerToken");
        Date customerTokenExpire = (Date) request.getSession().getAttribute("customerTokenExpire");

        writeFilterDebugLog(request, String.format(
                "L1 Session Token : Refresh Token: %s, Access Token: %s, Unify Id: %s, isMobileApp: %s, isAutoLogin : %s",
                refreshToken, accessToken, unifyId, isMobileApp, isAutoLogin));

        /** L2. Session 에 Token 이 없는 경우 Cookie 사용 **/
        if (accessToken == null || accessToken.equals("")) {
            // Cookie에서 accessToken 추출
            accessToken = getCookie(request, "ACCESS_TOKEN");
            refreshToken = getCookie(request, "REFRESH_TOKEN");
        }

        String accessTokenC = getCookie(request, "ACCESS_TOKEN");
        String refreshTokenC = getCookie(request, "REFRESH_TOKEN");

        writeFilterDebugLog(request, String.format(
                "L2 Cookie Token : Refresh Token: %s, Access Token: %s, Unify Id: %s, isMobileApp: %s, isAutoLogin : %s",
                refreshTokenC, accessTokenC, "", isMobileApp, isAutoLogin));

        /** L3. AccessToken 이 Session 과 Cookie 가 다른경우 - UnifyId 까지 확인 **/
        if( accessTokenC!=null && !accessTokenC.equals("")&& !accessToken.equals(accessTokenC)){
            // Session unifyId 와 쿠키 AccessToken 의 unifyId 가 다른 경우 로그아웃 처리
            String unifyIdC = getUnifyId(request, accessTokenC);
            if( unifyId!=null && !unifyId.equals("")&& !unifyId.equals(unifyIdC)){
                accessToken = null;
                refreshToken = null;
                isLoggedIn = false;

                writeFilterDebugLog(request, String.format(
                        "L32 Force Logout : Session unifyId: %s, Cookie unifyIdC: %s",
                        unifyId, unifyIdC));
            }
            else{
                accessToken = accessTokenC;
                refreshToken = refreshTokenC;
            }
        }

        /** L4. AccessToken validation **/
        if(accessToken != null && !accessToken.equals("")){

            // L41. accessToken 유효성 및 로그인 상태 확인
            try {
                // accessToken 유효성 확인 (token_info)
                //EmpTokenValidationResponse validationResponse = empSsoClient.validateToken(accessToken);
                //Long expiresIn = validationResponse.getExpireTime();
                Long expiresIn = getExpiresIn(request, accessToken);

                if(expiresIn > 0) {
                    // 토큰 유효 시간이 30분 이상
                    if (expiresIn >= 1800L) {
                        writeFilterDebugLog(request, "L41 Access token is Valid.");
                        isLoggedIn = true;
                    }
                    //ajax인 경우 토큰 갱신하지 않음 -> accessToken이 유효한 경우 로그인 처리
                    else if (expiresIn < 1800L && servletPath.contains("ajax")) {
                        writeFilterDebugLog(request, "L41 Access token is Valid (ajax) : " + servletPath);
                        isLoggedIn = true;
                    }
                    // 토큰 갱신
                    else {
                        try {
                            EmpAccessTokenResponse refreshResponse = empSsoClient.requestToken("REFRESH_TOKEN", refreshToken, empSsoLoginCallbackUri, null);
                            //EMP Refresh Token 은 refresh token 이 변경되지 않으며, 리턴되지 않음.
                            accessToken = refreshResponse.getAccess_token();
                            isLoggedIn = true;
                            request.getSession().setAttribute("TOKEN_ISSUE_DATETIME", tokenIssuedDateFormat.format(new Date()));
                        } catch (Exception e) {
                            initOBSConf(request, response);
                            isTokenRefreshException = true;
                            LOGGER.error("L42 Requesting token has failed. : {}", e.getMessage());
                            writeFilterDebugLog(request, String.format("L42 RefreshTokenException : %s", e.getMessage()));
                        }
                    }
                }

            }catch(Exception e){
                initOBSConf(request, response);
                isTokenValidationException = true;
                LOGGER.error("L42 Validating token has failed. : {}",e.getMessage());
                writeFilterDebugLog(request, String.format("L42 ValidateTokenException: %s", e.getMessage()));
            }
        }

        /** L5 EMP SSO_ID 로 로그인 처리
         String ssoId = getCookie(request, "SSO_ID");
         if(isMobileApp && StringUtils.isNotBlank(ssoId)){
         try {
         EmpAccessTokenResponse ssoTokenResponse = empSsoClient.getTokenBySsoId(ssoId);
         if(ssoTokenResponse != null){

         accessToken = ssoTokenResponse.getAccess_token();
         refreshToken = ssoTokenResponse.getRefresh_token();
         isLoggedIn = true;
         isAutoLoginViaMobileApp = true;
         writeFilterDebugLog(request, String.format("L51 Login with SSO_ID : %s", ssoId));

         //Cookie 삭제.
         String domain = configService.getString("serverInfo.domain");
         Cookie cookie = new Cookie("SSO_ID", null);
         cookie.setDomain(domain);
         cookie.setPath("/");
         // cookie.setSecure(true);
         response.addCookie(cookie);
         }
         }catch(Exception e){
         LOGGER.error("L52 Requesting SSO_ID token has failed");
         }
         }**/

        /** L6. MobileAPP 자동로그인 인 경우에  예외발생 등으로 로그인 되지 않은 경우 RefreshToken . **/
        if(isMobileApp && isAutoLogin && !isLoggedIn ){
            refreshToken = getCookie(request, "REFRESH_TOKEN");
            if(refreshToken != null && !refreshToken.equals("") && refreshToken.length() > 36 ){
                writeFilterDebugLog(request, String.format("L51 Mobile APP RefreshToken : %s", refreshToken));

                try {
                    EmpAccessTokenResponse refreshResponse = empSsoClient.requestToken("REFRESH_TOKEN", refreshToken, empSsoLoginCallbackUri, null);
                    //EMP Refresh Token 은 refresh token 이 변경되지 않으며, 리턴되지 않음.
                    accessToken = refreshResponse.getAccess_token();
                    isLoggedIn = true;
                    isAutoLoginViaMobileApp = true;
                    request.getSession().setAttribute("TOKEN_ISSUE_DATETIME", tokenIssuedDateFormat.format(new Date()));

                } catch (Exception e) {
                    initOBSConf(request, response);
                    isTokenRefreshException = true;

                    LOGGER.error("L62 Requesting token has failed. : {}",e.getMessage());
                    writeFilterDebugLog(request, String.format("L62 RefreshTokenException : %s", e.getMessage()));

                    /** L62 RefreshToken 비정상(network) 예외 발생시 최대 한번 더 시도 **/

                    try {
                        EmpAccessTokenResponse refreshResponse = empSsoClient.requestToken("REFRESH_TOKEN", refreshToken, empSsoLoginCallbackUri, null);
                        //EMP Refresh Token 은 refresh token 이 변경되지 않으며, 리턴되지 않음.
                        accessToken = refreshResponse.getAccess_token();

                        isLoggedIn = true;
                        isAutoLoginViaMobileApp = true;
                        request.getSession().setAttribute("TOKEN_ISSUE_DATETIME", tokenIssuedDateFormat.format(new Date()));

                    } catch (Exception sube) {
                        initOBSConf(request, response);
                        isTokenRefreshException = true;

                        LOGGER.error("L622 Requesting token has failed. : {}",e.getMessage());
                        writeFilterDebugLog(request, String.format("L622 RefreshTokenException : %s", sube.getMessage()));

                    }
                }

            }
        }

        /** L5 EMP SSO_ID 로 로그인 처리 **/
        String ssoId = getCookie(request, "sso_id");
        if (isMobileApp && !isLoggedIn && StringUtils.isNotBlank(ssoId)) {
            try {
                EmpAccessTokenResponse ssoTokenResponse = empSsoClient.getTokenBySsoId(ssoId);
                if (ssoTokenResponse != null) {

                    accessToken = ssoTokenResponse.getAccess_token();
                    refreshToken = ssoTokenResponse.getRefresh_token();
                    isLoggedIn = true;
                    isAutoLoginViaMobileApp = true;
                    writeFilterDebugLog(request, String.format("L51 Login with sso_id : %s", ssoId));

                    // Cookie 삭제.
                    //String domain = configService.getString("serverInfo.domain");
                    //Cookie cookie = new Cookie("sso_id", null);
                    //cookie.setDomain(domain);
                    //cookie.setPath("/");
                    //cookie.setMaxAge(0);
                    // cookie.setSecure(true);
                    //response.addCookie(cookie);
                }
            } catch (Exception e) {
                LOGGER.error("L52 Requesting sso_id token has failed");
            }
        }

        /** L7 세션/쿠키 응답 처리 **/
        if(isLoggedIn){
            if (unifyId == null || unifyId.equals("")) {
                unifyId = getUnifyId(request, accessToken);
            }

            if(isMobileApp) {
                writeFilterDebugLog(request, String.format(
                        "L71 Final Info - AccessToken: %s, RefreshToken: %s, UnifyId: %s, isLoggedIn: %s, isMobileApp: %s, isAutoLogin: %s, isTokenValidationException: %s, isTokenRefreshException: %s",
                        accessToken, refreshToken, unifyId, isLoggedIn, isMobileApp, isAutoLogin, isTokenValidationException, isTokenRefreshException));
            }

            // 세션에 Token, unifyId 저장
            request.getSession().setAttribute("ACCESS_TOKEN", accessToken);
            request.getSession().setAttribute("REFRESH_TOKEN", refreshToken);
            request.getSession().setAttribute("UNIFY_ID", unifyId);

            // 쿠키에 Token 저장
            String domain = configService.getString("serverInfo.domain");
            Cookie cookie = new Cookie("ACCESS_TOKEN", accessToken);
            cookie.setDomain(CommonCodes.SSO_TOKEN_DOMAIN);
            cookie.setPath("/");
            // cookie.setSecure(true);
            response.addCookie(cookie);

            Cookie rfcookie = new Cookie("REFRESH_TOKEN", refreshToken);
            rfcookie.setDomain(CommonCodes.SSO_TOKEN_DOMAIN);
            rfcookie.setPath("/");
            rfcookie.setMaxAge(60 * 60 * 24 * 30); // 한달로 설정
            response.addCookie(rfcookie);

            //delete old refresh cookie
            Cookie oldRfcookie = new Cookie("REFRESH_TOKEN", null);
            oldRfcookie.setMaxAge(0);
            oldRfcookie.setDomain(domain);
            oldRfcookie.setPath("/");
            response.addCookie(oldRfcookie);

            Cookie ufcookie = new Cookie("UNIFY_ID", unifyId);
            ufcookie.setDomain(domain);
            ufcookie.setPath("/");
            // cookie.setSecure(true);
            response.addCookie(ufcookie);

            // sso-client-id 저장 (베스트샵 요구사항)
            Cookie cicookie = new Cookie("CLIENT_ID", clientId);
            cicookie.setDomain(domain);
            cicookie.setPath("/");
            // cookie.setSecure(true);
            response.addCookie(cicookie);

            // 세션에 멤버 정보 없을 시 저장
            MemberInformation memberInformation = (MemberInformation) request.getSession()
                    .getAttribute("MemberInformation");
            if (memberInformation == null) {
                try {
                    memberInformation = memberService.getMemberInfo(unifyId);
                } catch (Exception e) {
                    LOGGER.error("[MemberInfoSessionChkFilter] 회원통합  EXCEPTION "+ ExceptionUtils.getStackTrace(e));
                }
                request.getSession().setAttribute("MemberInformation", memberInformation);
            }

            // 세션에 CustomerToken, CustomerTokenExpire 저장
            if (customerToken == null) {
                try {
                    customerToken = obsService.getCustomerToken(memberInformation.getCi(), request);
                    String obs_cartId = obsService.getCustomerCartId(customerToken, request);
                    Cookie obsCartIDCookie = new Cookie("OBS_CARTID", obs_cartId);
                    obsCartIDCookie.setDomain(domain);
                    obsCartIDCookie.setPath("/");
                    response.addCookie(obsCartIDCookie);

                } catch (Exception e) {
                    LOGGER.error("[MemberInfoSessionChkFilter] OBS EXCEPTION "+ExceptionUtils.getStackTrace(e));
                }
            }

            String obs_cartId = getCookie(request,"OBS_CARTID");
            if(obs_cartId.equals("")|| obs_cartId == null){
                try {
                    obs_cartId = obsService.getCustomerCartId(customerToken, request);
                    Cookie obsCartIDCookie = new Cookie("OBS_CARTID", obs_cartId);
                    obsCartIDCookie.setDomain(domain);
                    obsCartIDCookie.setPath("/");
                    response.addCookie(obsCartIDCookie);
                } catch (Exception e) {
                    LOGGER.error("[MemberInfoSessionChkFilter] OBS EXCEPTION "+ExceptionUtils.getStackTrace(e));
                }
            }

            if(isMobileApp) {
                // 회원통합용 쿠키(통합회원 요구사항)
                Cookie memberCookie = new Cookie("MEMBER_ACCESS_TOKEN", accessToken);
                memberCookie.setDomain(CommonCodes.SSO_TOKEN_DOMAIN);
                memberCookie.setPath("/");
                // cookie.setSecure(true);
                response.addCookie(memberCookie);
            }

            /** L7 Mobile APP 에서 자동로그인 된 경우 (약관 재동의 필요시 약관동의화면으로 redirection)
             if(isAutoLoginViaMobileApp){
             boolean hasNewAgree = hasNewAgreeTermsAndCondition(request, unifyId, clientId);
             if(hasNewAgree){
             String serverDomain = configService.getString("serverInfo.url");
             String requestUrl = request.getRequestURI() + (request.getQueryString() != null ? "?" + request.getQueryString() : "");
             if(requestUrl.startsWith("/kr")){
             requestUrl = requestUrl.replace("/kr","/");
             }
             String redirectUri = serverDomain + requestUrl;

             String memberAgreeUri = configService.getString("memberAgreeUri");
             String param = "{\"unifyId\":\"" + unifyId + "\",\"clientId\":\"" + clientId + "\",\"redirectUri\":\""+redirectUri+"\"}";
             String url = memberAgreeUri+ "?" + new String(Base64.encodeBase64(param.getBytes(StandardCharsets.UTF_8)));

             writeFilterDebugLog(request, String.format("L7 Agree Redirect URL: %s", url));

             response.sendRedirect(url);

             return;
             }
             }**/

        }else{
            if(isMobileApp) {
                writeFilterDebugLog(request, String.format(
                        "L72 Final Info - AccessToken: %s, RefreshToken: %s, UnifyId: %s, isLoggedIn: %s, isMobileApp: %s, isAutoLogin: %s, isTokenValidationException: %s, isTokenRefreshException: %s",
                        accessToken, refreshToken, unifyId, isLoggedIn, isMobileApp, isAutoLogin, isTokenValidationException, isTokenRefreshException));

                //MobileAPP , 자동로그인, REFRESH_TOKEN 존재하는 상태에서 logout 된 케이스 로깅.
                String requestRefreshToken = getCookie(request, "REFRESH_TOKEN");
                if(isAutoLogin && requestRefreshToken!=null && !requestRefreshToken.equals("") ) {

                    writeFilterDebugLog(request, String.format(
                            "APPLOGOUT Info - URI: %s, AccessToken: %s, RefreshToken: %s, UnifyId: %s, isLoggedIn: %s, isMobileApp: %s, isAutoLogin: %s, isTokenValidationException: %s, isTokenRefreshException: %s",
                            request.getRequestURI(), accessToken, refreshToken, unifyId, isLoggedIn, isMobileApp, isAutoLogin, isTokenValidationException, isTokenRefreshException));
                }

            }

            removeAllSessionAndCookie(request, response, isMobileApp, isAutoLogin);
            /*
            // 세션에서 Token, member정보
            request.getSession().removeAttribute("ACCESS_TOKEN");
            request.getSession().removeAttribute("REFRESH_TOKEN");
            request.getSession().removeAttribute("UNIFY_ID");
            request.getSession().removeAttribute("MemberInformation");
            request.getSession().removeAttribute("customerToken");
            request.getSession().removeAttribute("customerTokenExpire");

            // 쿠키에서 Token삭제
            String domain = configService.getString("serverInfo.domain");

            Cookie cookie = new Cookie("ACCESS_TOKEN", null);
            cookie.setMaxAge(0);
            cookie.setDomain(domain);
            cookie.setPath("/");
            response.addCookie(cookie);

            //mobile app , autoLogin 이 아닌경우 에만 삭제.
            if(!(isMobileApp && isAutoLogin)){
                Cookie rfcookie = new Cookie("REFRESH_TOKEN", null);
                rfcookie.setMaxAge(0);
                rfcookie.setDomain(domain);
                rfcookie.setPath("/");
                response.addCookie(rfcookie);
            }

            Cookie ufcookie = new Cookie("UNIFY_ID", null);
            ufcookie.setMaxAge(0);
            ufcookie.setDomain(domain);
            ufcookie.setPath("/");
            // cookie.setSecure(true);
            response.addCookie(ufcookie);

            // sso-client-id 저장 (베스트샵 요구사항)
            Cookie cicookie = new Cookie("CLIENT_ID", null);
            cicookie.setMaxAge(0);
            cicookie.setDomain(domain);
            cicookie.setPath("/");
            // cookie.setSecure(true);
            response.addCookie(cicookie);

            // 회원통합용 쿠키(통합회원 요구사항)
            String memberDomain = "lge.co.kr";
            Cookie memberCookie = new Cookie("MEMBER_ACCESS_TOKEN", null);
            memberCookie.setMaxAge(0);
            memberCookie.setDomain(memberDomain);
            memberCookie.setPath("/");
            // cookie.setSecure(true);
            response.addCookie(memberCookie);
            */

            // 로그인이 필요한 페이지인 경우 로그인페이지로 redirect
            if (requiredLogin(request, requiredList, exceptionList)) {
                String state = request.getRequestURI() + (request.getQueryString() != null ? "?" + request.getQueryString() : "");
                String url = String.format("%s?state=%s", "/sso/api/emp/Login", state);
                response.sendRedirect(url);

                return;
            }
        }

        // Continue filterChain
        filterChain.doFilter(servletRequest, servletResponse);
    }


    private void removeAllSessionAndCookie(HttpServletRequest request, HttpServletResponse response, boolean isMobileApp, boolean isAutoLogin){
        // 세션에서 Token, member정보
        request.getSession().removeAttribute("ACCESS_TOKEN");
        request.getSession().removeAttribute("REFRESH_TOKEN");
        request.getSession().removeAttribute("UNIFY_ID");
        request.getSession().removeAttribute("MemberInformation");
        request.getSession().removeAttribute("customerToken");
        request.getSession().removeAttribute("customerTokenExpire");

        // 쿠키에서 Token삭제
        String domain = configService.getString("serverInfo.domain");

        Cookie cookie = new Cookie("ACCESS_TOKEN", null);
        cookie.setMaxAge(0);
        cookie.setDomain(CommonCodes.SSO_TOKEN_DOMAIN);
        cookie.setPath("/");
        response.addCookie(cookie);

        //mobile app , autoLogin 이 아닌경우 에만 삭제.
        if(!(isMobileApp && isAutoLogin)){
            Cookie rfcookie = new Cookie("REFRESH_TOKEN", null);
            rfcookie.setMaxAge(0);
            rfcookie.setDomain(CommonCodes.SSO_TOKEN_DOMAIN);
            rfcookie.setPath("/");
            response.addCookie(rfcookie);

            //delete old refresh cookie
            Cookie oldRfcookie = new Cookie("REFRESH_TOKEN", null);
            oldRfcookie.setMaxAge(0);
            oldRfcookie.setDomain(domain);
            oldRfcookie.setPath("/");
            response.addCookie(oldRfcookie);
        }

        Cookie ufcookie = new Cookie("UNIFY_ID", null);
        ufcookie.setMaxAge(0);
        ufcookie.setDomain(domain);
        ufcookie.setPath("/");
        // cookie.setSecure(true);
        response.addCookie(ufcookie);

        // sso-client-id 저장 (베스트샵 요구사항)
        Cookie cicookie = new Cookie("CLIENT_ID", null);
        cicookie.setMaxAge(0);
        cicookie.setDomain(domain);
        cicookie.setPath("/");
        // cookie.setSecure(true);
        response.addCookie(cicookie);

        // 회원통합용 쿠키(통합회원 요구사항)
        Cookie memberCookie = new Cookie("MEMBER_ACCESS_TOKEN", null);
        memberCookie.setMaxAge(0);
        memberCookie.setDomain(CommonCodes.SSO_TOKEN_DOMAIN);
        memberCookie.setPath("/");
        // cookie.setSecure(true);
        response.addCookie(memberCookie);
    }

    public boolean loginExceptionList(String uri, List<Object> exceptionList) {
        boolean bException;
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        List<String> list = exceptionList.stream().map(object -> Objects.toString(object, null))
                .collect(Collectors.toList());

        for (String exceptUri : list) {
            if (exceptUri == null)
                continue;

            bException = antPathMatcher.match(exceptUri, uri);

            if (bException) {
                return true;
            }
        }

        return false;
    }

    public boolean requiredLogin(HttpServletRequest request, List<Object> requiredList, List<Object> exceptionList) {
        boolean isRequiredLogin;
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        List<String> list = requiredList.stream().map(object -> Objects.toString(object, null))
                .collect(Collectors.toList());
        String currentUri = request.getRequestURI();

        for (String uri : list) {
            if (uri == null)
                continue;

            isRequiredLogin = antPathMatcher.match(uri, currentUri);

            if (isRequiredLogin) {
                if (exceptionList.isEmpty()) {
                    return true;
                } else {
                    return !loginExceptionList(currentUri, exceptionList);
                }
            }
        }

        return false;
    }

    public String getCookie(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null)
            return "";
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(name)) {
                return cookie.getValue();
            }
        }
        return "";
    }

    public String getUnifyId(HttpServletRequest request, String accessToken) {

        String unifyId = "";

        try {
            String empSsoHost = configService.getString("empSsoHost");
            String empSsoAppKey = configService.getString("empSsoAppKey");
            String empSsoAppSecret = configService.getString("empSsoAppSecret");

            EmpSsoClient empSsoClient = new EmpSsoClient(empSsoHost, empSsoAppKey, empSsoAppSecret);
            EmpUserProfileResponse userProfileResponse = empSsoClient.userProfile(accessToken);
            if(userProfileResponse != null) {
                String empUserNo = userProfileResponse.getAccount().getUserNo();

                writeFilterDebugLog(request, String.format(
                        "LUnify retrieve - AccessToken: %s, empUserNo: %s",
                        accessToken, empUserNo));

                String memberUnifyIdUri = configService.getString("memberUnifyIdUri");
                String param = "empNo="+empUserNo;

                HttpUtils httpUtils = new HttpUtils();
                HttpResponse response = httpUtils.makeRequest("GET", memberUnifyIdUri + "?" + param, (String)null, null);
                HttpEntity entity = response.getEntity();
                String responseText = EntityUtils.toString(entity);
                Map unmarshal = JsonUtils.unmarshal(responseText);
                unifyId = (String)unmarshal.get("unifyId");

            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            LOGGER.error("LUnify Requesting token has failed");
            writeFilterDebugLog(request, String.format("LUnify Exception : %s", e.getMessage()));
        }

        return unifyId;
    }

    /**
     * EMP 호출 최소화를 위해 세션에 저장 후 확인.
     * 1. 토큰발급/갱신 시 세션에 발급시간 저장.
     * 2. 세션에 저장된 토큰 발급시간으로 잔여시간 계산(현재 시간 - 토큰 발급시간)
     * 3. 잔여시간이 30분이상이면 계산된시간으로 처리.(세션값이 없거나, 잔여시간이 30분 이내면 EMP 호출)
     * @param request
     * @param accessToken
     * @return
     */
    public long getExpiresIn(HttpServletRequest request, String accessToken) {
        long expiresIn = 0;
        try {
            if(NullUtil.notNone(accessToken)){
                String tokenCheckType = "SESSION";
                //토큰발급 후 지난 초
                long issuePassedSeconds = 0;
                if(request.getSession().getAttribute("TOKEN_ISSUE_DATETIME") != null){

                    String issuedDateTimeStr = (String)request.getSession().getAttribute("TOKEN_ISSUE_DATETIME");
                    Date currentDateTime = new Date();
                    Date issuedDateTime = null;
                    try {
                        issuedDateTime = tokenIssuedDateFormat.parse(issuedDateTimeStr);
                        long diff = currentDateTime.getTime() - issuedDateTime.getTime();
                        issuePassedSeconds = diff / 1000;
                        //LOGGER.warn("diffSeconds        :"+diffSeconds);
                    } catch (Exception e) {
                    }
                }

                //30분 이내 발급된 경우
                /*
                if(issuePassedSeconds > 0 && issuePassedSeconds <= 1800){
                    expiresIn = 3600 - issuePassedSeconds;
                }else {

                */
                    String empSsoHost = configService.getString("empSsoHost");
                    String empSsoAppKey = configService.getString("empSsoAppKey");
                    String empSsoAppSecret = configService.getString("empSsoAppSecret");
                    EmpSsoClient empSsoClient = new EmpSsoClient(empSsoHost, empSsoAppKey, empSsoAppSecret);

                    EmpTokenValidationResponse validationResponse = empSsoClient.validateToken(accessToken);
                    expiresIn = validationResponse.getExpireTime();
                    Date currentDateTime = new Date();
                    issuePassedSeconds = 3600 - expiresIn;
                    Date issuedDateTime = Date.from(currentDateTime.toInstant().minusSeconds(issuePassedSeconds));

                    request.getSession().setAttribute("TOKEN_ISSUE_DATETIME", tokenIssuedDateFormat.format(issuedDateTime));
                    tokenCheckType = "EMP";
                //}

                writeFilterDebugLog(request, String.format(
                        "EMPSSO validateToken : tokenCheckType: %s, expiresIn : %s, Access Token: %s",tokenCheckType, expiresIn, accessToken));
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
            LOGGER.error("access_token validation has failed {} : {}", accessToken, e.getMessage());
        }
        return expiresIn;
    }

    public void initOBSConf(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().removeAttribute("OBS_CARTID");
        request.getSession().removeAttribute("customerToken");
        request.getSession().removeAttribute("customerTokenExpire");
        request.getSession().removeAttribute("OBSCART_CNT");

        String domain = configService.getString("serverInfo.domain");
        Cookie obsCartIDCookie = new Cookie("OBS_CARTID", null);
        obsCartIDCookie.setMaxAge(0);
        obsCartIDCookie.setDomain(domain);
        obsCartIDCookie.setPath("/");
        response.addCookie(obsCartIDCookie);

        return;
    }

    /**
     * 자동로그인 시 약관변경 등으로 인한 약관 재동의 여부 확인
     * @param request
     * @param unifyId
     * @param clientId
     * @return
     */
    private boolean hasNewAgreeTermsAndCondition(HttpServletRequest request, String unifyId, String clientId){
        boolean hasNew = false;
        int timeout = 2000;
        try {
            String memberAgreeCheckUri = configService.getString("memberAgreeCheckUri");

            String param = "{\"unifyId\":\"" + unifyId + "\",\"clientId\":\"" + clientId + "\"} ";
            memberAgreeCheckUri += "?" + new String(Base64.encodeBase64(param.getBytes(StandardCharsets.UTF_8)));

            HttpGet get = new HttpGet(memberAgreeCheckUri);
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectTimeout(timeout)
                    .setSocketTimeout(timeout)
                    .setConnectionRequestTimeout(timeout)
                    .build();
            get.setConfig(requestConfig);
            HttpClient client = HttpClientBuilder.create().build();
            HttpResponse response = client.execute(get);

            HttpEntity entity = response.getEntity();
            String responseText = EntityUtils.toString(entity);
            Map unmarshal = JsonUtils.unmarshal(responseText);

            writeFilterDebugLog(request, String.format(
                    "L7 Agree Check Info - UnifyId: %s, Check URL: %s, ReturnCode: %s, ReturnText: %s",
                    unifyId, memberAgreeCheckUri, response.getStatusLine().getStatusCode(), responseText));

            String agreeYN = (String) unmarshal.getOrDefault("agreeYN","N");
            if(agreeYN.equals("Y")){
                hasNew = true;
            }
        }catch(Exception e){
            LOGGER.error(String.format("L7 Agree Check Info Exception : %s, Timeout : %s", e.getMessage(), timeout ));
        }
        return hasNew;
    }

    private boolean isJWTAccessToken(String accessToken){
        boolean isJWTToken = false;
        try {
            JWTClaimsSet jwtClaimsSet = JwtUtils.parseToken(accessToken);
            JSONObject contexts = (JSONObject) jwtClaimsSet.getClaim("context");
            isJWTToken = true;
        } catch (Exception ex) {
        }

        return isJWTToken;
    }
    private void writeFilterDebugLog(HttpServletRequest request, String message){
        StringBuilder messageBuilder = new StringBuilder("[MemberInfoSessionChkFilter] ");
        if(request.getHeader("x-amzn-trace-id") != null){
            messageBuilder.append(" x-amzn-trace-id : ").append(request.getHeader("x-amzn-trace-id")).append(" | ");
        }
        messageBuilder.append(message);
        LOGGER.warn(messageBuilder.toString());

    }
}
