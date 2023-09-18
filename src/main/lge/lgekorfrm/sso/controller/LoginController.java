package lgekorfrm.sso.controller;

import com.nimbusds.jwt.JWTClaimsSet;
import devonframe.configuration.ConfigService;
import devonframe.dataaccess.CommonDao;
import devonframe.exception.BusinessException;
import devonframe.security.crypto.CryptoManager;
import devonframe.security.crypto.exception.CryptoException;
import lgekorfrm.context.WebContext;
import lgekorfrm.member.service.MemberService;
import lgekorfrm.obs.service.ObsService;
import lgekorfrm.sso.SSOClient;
import lgekorfrm.sso.domain.AccessTokenResponse;
import lgekorfrm.sso.member.MemberInformation;
import lgekorfrm.sso.oauth.AccessTokenRequest;
import lgekorfrm.sso.oauth.AuthorizeRequest;
import lgekorfrm.sso.oauth.LogoutRequest;
import lgekorfrm.sso.util.HttpUtils;
import lgekorfrm.sso.util.JsonUtils;
import lgekorfrm.sso.util.JwtUtils;
import net.minidev.json.JSONObject;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.stereotype.Controller;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.bind.DatatypeConverter;

import java.io.PrintWriter;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@PropertySources(@PropertySource("classpath:/config/project.properties"))

@RequestMapping("/kr")
public class LoginController implements InitializingBean {

	private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

	@Resource(name = "configService")
	ConfigService configService;

	@Resource(name = "aesCrypto")
	private CryptoManager aesCrypto;

	@Resource(name = "ObsService")
	private ObsService obsService;

	@Resource(name = "MemberService")
	private MemberService memberservice;

	@Resource(name = "commonDao")
	private CommonDao commonDao;

	private String baseState;
	private String ssoHost;
	private String clientId;
	private String clientSecret;
	private String loginCallbackUri;
	private String logoutCallbackUri;
	private SSOClient ssoClient;

	private StringBuffer loginFlag = new StringBuffer("LoginFlag=Y");

	@Override
	public void afterPropertiesSet() throws Exception {
		this.ssoHost = configService.getString("ssoHost");
		this.clientId = configService.getString("ssoClientId");
		this.clientSecret = configService.getString("ssoClientSecret");
		this.loginCallbackUri = configService.getString("ssoLoginCallBackUri");
		this.logoutCallbackUri = configService.getString("ssoLogoutCallBackUri");
		this.ssoClient = new SSOClient(ssoHost);
	}

	private void setSSOProperties() {
		this.ssoHost = configService.getString("ssoHost");
		this.clientId = configService.getString("ssoClientId");
		this.clientSecret = configService.getString("ssoClientSecret");
		this.loginCallbackUri = configService.getString("ssoLoginCallBackUri");
		this.logoutCallbackUri = configService.getString("ssoLogoutCallBackUri");
		this.ssoClient = new SSOClient(ssoHost);
	}

	private String getBaseState() {
		String mylgLocaleUrl = "/";
		if (mylgLocaleUrl == null || "".equals(mylgLocaleUrl)) {
			return String.format("%s", mylgLocaleUrl);
		}
		return String.format("%s", mylgLocaleUrl);
	}

	@RequestMapping(value = "/sso/api/Login")
	public void ssoLogin(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String ssoLogin = configService.getString("ssoLogin");

		/* 이전 URL emp logout 로 redirection 처리 */
		if(ssoLogin.contains("/emp/")) {
			StringBuffer url = new StringBuffer(ssoLogin);

			Enumeration params = request.getParameterNames();
			int count = 0;
			while(params.hasMoreElements()) {
				String name = (String) params.nextElement();
				if(count == 0){
					url.append("?"+name+"="+request.getParameter(name));
				}else{
					url.append("&"+name+"="+request.getParameter(name));
				}
				count++;
			}
			response.sendRedirect(url.toString());
			return;
		}

		baseState = getBaseState();
		String state = request.getParameter("state");
		List<Object> redirectMain = configService.getList("redirectMain.login");
		List<Object> redirectCart = configService.getList("redirectCart.login");
		List<String> redirectMainList = redirectMain.stream().map(object -> Objects.toString(object, null))
				.collect(Collectors.toList());
		List<String> redirectCartList = redirectCart.stream().map(object -> Objects.toString(object, null))
				.collect(Collectors.toList());
		
		
		LOGGER.debug("state "+ state);
			
		if (state != null && state.startsWith("/kr")) {
			state = state.substring(3, state.length());
		}				
		if (state == null || "".equals(state)) {
			state = request.getHeader("referer");
			if (state == null || "".equals(state)) {
				state = baseState;
			}
		}		
		//로그인 시 main페이지로 redirect 
		for (String exceptUri : redirectMainList) {
			if (state.contains(exceptUri)){
				state = baseState;
				break;
			}			
		}
		
		String cartUrl = configService.getString("cartUrl");
		//로그인 시 cart페이지로 redirect 
		for (String exceptUri : redirectCartList) {
			if (state.endsWith(exceptUri)){
				state = cartUrl;
				break;
			}			
		}		
		// encrypt state
		try {
			state = aesCrypto.encrypt(state);
		} catch (Exception e) {
			LOGGER.error("Encryption(state-" + state + ") is failed. Set decrypted state value to '" + baseState + "'");
			state = aesCrypto.encrypt(baseState);
		}

		// SSO설정
		setSSOProperties();
		LOGGER.debug("[LoginController]/login : state "+aesCrypto.decrypt(state));

		// 로그인페이지로 redirect
		AuthorizeRequest authorizeRequest = new AuthorizeRequest().withClientKey(clientId).withState(state)
				.addScope("member-write").withRedirectUri(loginCallbackUri)
				.withResponseType(AuthorizeRequest.ResponseType.code).withTokenType(AuthorizeRequest.TokenType.JWT);

		String url = ssoClient.generateAutorizeUrl(authorizeRequest);
		response.sendRedirect(url);
		return;
	}

	@RequestMapping(value = "/sso/api/callbackLogin")
	public void ssoLoginCallback(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// SSO설정
		setSSOProperties();

		String code = request.getParameter("code");

		// code가 없는 경우
		if (StringUtils.isEmpty(code)) {
			throw new BusinessException("login failed");
		}

		String state = request.getParameter("state");

		// decrypt state
		try {
			state = aesCrypto.decrypt(state);
		} catch (CryptoException e) {
			LOGGER.error("Decryption(state-" + state + ") is failed. Set Decrypted state value to '" + baseState + "'");
			state = baseState;
		}

		LOGGER.debug("[LoginController]callbackLogin: state " + state);

		// Token 요청
		AccessTokenRequest tokenRequest = new AccessTokenRequest().withClientKeyAndSecret(clientId, clientSecret)
				.withGrantType(AccessTokenRequest.GrantType.authorization_code)
				.withTokenType(AccessTokenRequest.TokenType.JWT).withCode(code);

		AccessTokenResponse tokenResponse = ssoClient.requestToken(tokenRequest);

		String access_token = tokenResponse.getAccess_token();
		String refresh_token = tokenResponse.getRefresh_token();

		// 세션에 토큰 저장
		request.getSession().setAttribute("ACCESS_TOKEN", access_token);
		request.getSession().setAttribute("REFRESH_TOKEN", refresh_token);

		// unifyId 추출
		JWTClaimsSet jwtClaimsSet = JwtUtils.parseToken(access_token);
		JSONObject contexts = (JSONObject) jwtClaimsSet.getClaim("context");
		Map user = JsonUtils.convertValue(contexts.get("user"), Map.class);
		String unifyId = (String) user.get("unifyId");

		// 세션에 unifyId 저장
		request.getSession().setAttribute("UNIFY_ID", unifyId);

		// 회원정보 요청
		MemberInformation memberInformation = memberservice.getMemberInfo(unifyId);
		LOGGER.debug("memberInformation: " + memberInformation);

		// 세션에 회원정보 저장
		request.getSession().setAttribute("MemberInformation", memberInformation);
		
		// obs api호출
		String customerToken = "";
		String obs_cartId = "";
		
		try {
			// OBS CustomerToken요청
			String memberCi = memberInformation.getCi();
			customerToken = obsService.getCustomerToken(memberCi, request);

			// OBS CustomerCartId 요청
			obs_cartId = obsService.getCustomerCartId(customerToken, request);

			// OBS MERGE CartID
			String empty_cartId = getCookie(request, "OBS_CARTID");
			if (empty_cartId != null && !empty_cartId.equals("")) {
				obsService.mergeCart(customerToken, empty_cartId, obs_cartId, request);
			}
			
		} catch (Exception e) {
			//response.sendError(561,"OBSERROR");
			LOGGER.error("[LoginController] OBS EXCEPTION "+ExceptionUtils.getStackTrace(e));
			//return;
		}
		
		
		// OBS CART 수량 초기화 ( 회원 CART ID로 재저장 필요 )
		request.getSession().removeAttribute("OBSCART_CNT");

		// RecentlyView cookie > 최근본제품 디비 Insert
		Map<String, Object> input = new HashMap<>();
		String myRecentProduct = getCookie(request, "myRecentProduct");
		int rntCnt  = 0;
		if (myRecentProduct != null && !myRecentProduct.equals("")) {
			input.put("unifyId", unifyId);
			String cookieValue = URLDecoder.decode(myRecentProduct, "UTF-8");
			if (cookieValue != null) {
				for (String str : cookieValue.split("\\|")) {
					Map<String, Object> modelIdList = new HashMap<>();
					modelIdList.put("modelId", str.split("\\&", 3)[0]);
					if (!modelIdList.isEmpty()) {
						input.putAll(modelIdList);
						rntCnt = insertRecentlyViewProdList(input);
					}
				}
			}
		}
		LOGGER.info("[LoginController]RecentlyView UPDATECNT: "+ rntCnt+ " myRecentProduct: " + myRecentProduct);

		// 쿠키에 accessToken저장
		String domain = configService.getString("serverInfo.domain");
		Cookie cookie = new Cookie("ACCESS_TOKEN", access_token);
		cookie.setDomain(domain);
		cookie.setPath("/");
		// cookie.setSecure(true);
		response.addCookie(cookie);

		Cookie rfcookie = new Cookie("REFRESH_TOKEN", refresh_token);
		rfcookie.setDomain(domain);
		rfcookie.setPath("/");
		rfcookie.setMaxAge(60*60*24*30); //한달로 설정 
		response.addCookie(rfcookie);

		Cookie ufcookie = new Cookie("UNIFY_ID", unifyId);
		ufcookie.setDomain(domain);
		ufcookie.setPath("/");
		// cookie.setSecure(true);
		response.addCookie(ufcookie);

		// 쿠키에 CustomerCartId저장
		Cookie obsCartIDCookie = new Cookie("OBS_CARTID", obs_cartId);
		obsCartIDCookie.setDomain(domain);
		obsCartIDCookie.setPath("/");
		response.addCookie(obsCartIDCookie);
		
		//sso-client-id 저장 (베스트샵 요구사항)
		Cookie cicookie = new Cookie("CLIENT_ID", clientId);
		cicookie.setDomain(domain);
		cicookie.setPath("/");
		// cookie.setSecure(true);
		response.addCookie(cicookie);
		
		//앱스플라이어 로그인 이벤트 트래킹(공통 스크립트에서 처리)						
		if(request.getHeader("user-agent") != null && request.getHeader("user-agent").contains("LGEAPP")){		
			Cookie afCookie = new Cookie("AF_LOGIN", unifyId);
			afCookie.setDomain(domain);
			afCookie.setPath("/");
			response.addCookie(afCookie);

			// 회원통합용 쿠키(통합회원 요구사항)
			Cookie memberCookie = new Cookie("MEMBER_ACCESS_TOKEN", access_token);
			String memberDomain = "lge.co.kr";
			memberCookie.setDomain(memberDomain);
			memberCookie.setPath("/");
			response.addCookie(memberCookie);
		}

		response.sendRedirect(state);
		return;

	}

	@RequestMapping(value = "/sso/api/Logout")
	public void ssoLogout(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String ssoLogout = configService.getString("ssoLogout");
		/* 이전 URL emp logout 로 redirection 처리 */
		if(ssoLogout.contains("/emp/")) {
			StringBuffer url = new StringBuffer(ssoLogout);

			Enumeration params = request.getParameterNames();
			int count = 0;
			while(params.hasMoreElements()) {
				String name = (String) params.nextElement();
				if(count == 0){
					url.append("?"+name+"="+request.getParameter(name));
				}else{
					url.append("&"+name+"="+request.getParameter(name));
				}
				count++;
			}
			response.sendRedirect(url.toString());
			return;
		}

		// SSO 설정
		setSSOProperties();

		// state가 null인 경우 baseState로 설정
		baseState = getBaseState();
		String domain = configService.getString("serverInfo.domain");
		String state = request.getParameter("state");
		
		LOGGER.debug("[LoginController]/logout 1state: "+state);
		
		List<Object> redirectMain = configService.getList("redirectMain.logout");
		List<String> redirectMainList = redirectMain.stream().map(object -> Objects.toString(object, null))
				.collect(Collectors.toList());

		if (state != null && state.startsWith("/kr")) {
			state = state.substring(3, state.length());
		}

		if (state == null || "".equals(state)) {
			state = request.getHeader("referer");
			if (state == null || "".equals(state)) {
				state = baseState;
			}
		}
		if (state.contains(this.ssoHost)) {
			state = baseState;
		}
		//로그아웃 시 main페이지로 redirect
		if (state.contains(domain)){
			for( String exceptUri : redirectMainList){
				if(state.contains(exceptUri)){
					state = baseState;
					break;
				}
			}		
		}
		LOGGER.debug("[LoginController]/logout 2state: "+state);
		
		// encrypt state
		try {
			state = aesCrypto.encrypt(state);
		} catch (Exception e) {
			LOGGER.error("Encryption(state-" + state + ") is failed. Set encrypted state value to '" + baseState + "'");
			state = aesCrypto.encrypt(baseState);
		}
	
		// sso로그아웃 으로 Redricet
		LogoutRequest logoutRequest = new LogoutRequest().withClientKey(clientId).withRedirectUri(logoutCallbackUri)
				.withState(state);

		String url = ssoClient.generateLogoutUrl(logoutRequest);
		response.sendRedirect(url);
		return;
	}

	@RequestMapping(value = "/sso/api/callbackLogout")
	public void ssoLogoutCallback(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// SSO 설정
		setSSOProperties();

		// stauts 확인
		String status = request.getParameter("status");

		if (!"success".equals(status)) {
			throw new RuntimeException("logout failed.");
		}

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

		// 쿠키 초기화
		String domain = configService.getString("serverInfo.domain");
		Cookie cookie = new Cookie("ACCESS_TOKEN", null);
		cookie.setMaxAge(0);
		cookie.setDomain(domain);
		cookie.setPath("/");
		response.addCookie(cookie);

		Cookie rfcookie = new Cookie("REFRESH_TOKEN", null);
		rfcookie.setMaxAge(0);
		rfcookie.setDomain(domain);
		rfcookie.setPath("/");
		response.addCookie(rfcookie);

		Cookie obsCartIDCookie = new Cookie("OBS_CARTID", null);
		obsCartIDCookie.setMaxAge(0);
		obsCartIDCookie.setDomain(domain);
		obsCartIDCookie.setPath("/");
		response.addCookie(obsCartIDCookie);

		Cookie ufcookie = new Cookie("UNIFY_ID", null);
		ufcookie.setMaxAge(0);
		ufcookie.setDomain(domain);
		ufcookie.setPath("/");
		// cookie.setSecure(true);
		response.addCookie(ufcookie);
		
		//sso-client-id 저장 (베스트샵 요구사항)
		Cookie cicookie = new Cookie("CLIENT_ID", null);
		cicookie.setMaxAge(0);
		cicookie.setDomain(domain);
		cicookie.setPath("/");
		// cookie.setSecure(true);
		response.addCookie(cicookie);

		// 회원통합용 쿠키(통합회원 요구사항)
		Cookie memberCookie = new Cookie("MEMBER_ACCESS_TOKEN", null);
		memberCookie.setMaxAge(0);
		String memberDomain = "lge.co.kr";
		memberCookie.setDomain(memberDomain);
		memberCookie.setPath("/");
		// cookie.setSecure(true);
		response.addCookie(memberCookie);

		// decrypt state
		String state = request.getParameter("state");
		try {
			state = aesCrypto.decrypt(state);

		} catch (CryptoException e) {
			LOGGER.error("Decryption(state-" + state + ") is failed. Set Decrypted state value to '" + baseState + "'");
			state = baseState;
		}

		response.sendRedirect(state);
		return;
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
	
	public boolean redirectMainList(String uri, List<Object> mainList) {
		boolean bException;
		AntPathMatcher antPathMatcher = new AntPathMatcher();
		List<String> list = mainList.stream().map(object -> Objects.toString(object, null))
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
	
	public int insertRecentlyViewProdList(Map<String, Object> input){
		int count = 0;
		try {			
			int dupChk = commonDao.select("lgekorfrm.retrieveDupChk", input, "mgr");
			if(dupChk > 0){				
				count = commonDao.insert("lgekorfrm.updateRvProducts", input, "mgr");
			}else{
				count = commonDao.insert("lgekorfrm.insertRvProducts", input, "mgr");
			}
		} catch (Exception e) {
			count = -1;
			LOGGER.error("### deleteRvProducts service Exception : " + ExceptionUtils.getStackTrace(e));
		}
		return count;
	}

}
