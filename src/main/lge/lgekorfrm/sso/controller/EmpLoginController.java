package lgekorfrm.sso.controller;

import com.google.gson.Gson;
import devonframe.configuration.ConfigService;
import devonframe.dataaccess.CommonDao;
import devonframe.exception.BusinessException;
import devonframe.security.crypto.CryptoManager;
import devonframe.security.crypto.exception.CryptoException;
import lgekorfrm.code.CommonCodes;
import lgekorfrm.member.service.MemberService;
import lgekorfrm.obs.service.ObsService;
import lgekorfrm.sso.emp.EmpAccessTokenResponse;
import lgekorfrm.sso.emp.EmpSsoClient;
import lgekorfrm.sso.emp.EmpTokenValidationResponse;
import lgekorfrm.sso.emp.EmpUserProfileResponse;
import lgekorfrm.sso.member.MemberInformation;
import lgekorfrm.sso.util.AES256Util;
import lgekorfrm.sso.util.HttpUtils;
import lgekorfrm.sso.util.JsonUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@PropertySources(@PropertySource("classpath:/config/project.properties"))

@RequestMapping("/kr")
public class EmpLoginController implements InitializingBean {

	private static final Logger LOGGER = LoggerFactory.getLogger(EmpLoginController.class);

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
	private String clientId;
	private String empMemberLoginPageUri;
	private String empSsoHost;
	private String empSsoAppKey;
	private String empSsoAppSecret;
	private String empSsoLoginCallbackUri;
	private EmpSsoClient empSsoClient;

	private StringBuffer loginFlag = new StringBuffer("LoginFlag=Y");

	@Override
	public void afterPropertiesSet() throws Exception {

		this.clientId = configService.getString("ssoClientId");
		this.empMemberLoginPageUri = configService.getString("empMemberLoginPageUri");
		this.empSsoHost = configService.getString("empSsoHost");
		this.empSsoAppKey = configService.getString("empSsoAppKey");
		this.empSsoAppSecret = configService.getString("empSsoAppSecret");
		this.empSsoLoginCallbackUri = configService.getString("empSsoLoginCallbackUri");
		this.empSsoClient = new EmpSsoClient(empSsoHost, empSsoAppKey, empSsoAppSecret);
	}

	private void setSSOProperties() {

		this.clientId = configService.getString("ssoClientId");
		this.empMemberLoginPageUri = configService.getString("empMemberLoginPageUri");
		this.empSsoHost = configService.getString("empSsoHost");
		this.empSsoAppKey = configService.getString("empSsoAppKey");
		this.empSsoAppSecret = configService.getString("empSsoAppSecret");
		this.empSsoLoginCallbackUri = configService.getString("empSsoLoginCallbackUri");
		this.empSsoClient = new EmpSsoClient(empSsoHost, empSsoAppKey, empSsoAppSecret);
	}

	private String getBaseState() {
		String mylgLocaleUrl = "/";
		if (mylgLocaleUrl == null || "".equals(mylgLocaleUrl)) {
			return String.format("%s", mylgLocaleUrl);
		}
		return String.format("%s", mylgLocaleUrl);
	}

	@RequestMapping(value = "/sso/api/emp/Login")
	public void ssoLogin(HttpServletRequest request, HttpServletResponse response) throws Exception {

		//회원정보 수정 후 return 시 code 값이 존재하는 경우 EMP 다시 로그인 처리한 case. (비밀번호변경)
		if(request.getParameter("code") != null) {
			StringBuffer url = new StringBuffer("/sso/api/emp/callbackLogin");
			Enumeration params = request.getParameterNames();
			int count = 0;
			while (params.hasMoreElements()) {
				String name = (String) params.nextElement();
				if (count == 0) {
					url.append("?" + name + "=" + request.getParameter(name));
				} else {
					url.append("&" + name + "=" + request.getParameter(name));
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

		// SSO설정
		setSSOProperties();

		boolean isLoggedIn = false;
		try {
			MemberInformation sessionMember = (MemberInformation)request.getSession().getAttribute("MemberInformation");

			if(sessionMember != null) {
				isLoggedIn = true;
			}
		}catch(Exception e){
			LOGGER.error("Token Validation failed.");
		}

		if(isLoggedIn) {
			response.sendRedirect(state);
		}else{
			String encryptedState = "";
			// encrypt state
			try {
				encryptedState = aesCrypto.encrypt(state);
			} catch (Exception e) {
				LOGGER.error("Encryption(state-" + encryptedState + ") is failed. Set decrypted state value to '" + baseState + "'");
				encryptedState = aesCrypto.encrypt(baseState);
			}

			// 로그인페이지로 redirect
			StringBuffer sb = new StringBuffer(this.empMemberLoginPageUri);
			sb.append("?client_id=").append(this.clientId);    //회원통합에서 client 구분용으로, EMP APP KEY 와 다른 데이터임.
			sb.append("&state=").append(encryptedState);
			sb.append("&redirect_uri=").append(this.empSsoLoginCallbackUri);
			response.sendRedirect(sb.toString());

		}

		return;
	}

	@RequestMapping(value = "/sso/api/emp/callbackLogin")
	public ModelAndView ssoLoginCallback(HttpServletRequest request, HttpServletResponse response) throws Exception {

		SimpleDateFormat tokenIssuedDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");

		// SSO설정
		setSSOProperties();

		String code = request.getParameter("code");
		String empUserNo = request.getParameter("user_number");

		// code가 없는 경우
		if (StringUtils.isEmpty(code)) {
			throw new BusinessException("login failed");
		}

		String state = "";
		// decrypt state
		try {
			if(request.getParameter("state") != null){
				//LOGGER.error("Decryption1(state-" + state + ") is Success'");
				state = URLDecoder.decode(request.getParameter("state"), "UTF-8");
				state = state.trim().replaceAll(" ","+");
				//state = request.getParameter("state").trim();
			}
			state = aesCrypto.decrypt(state);

			LOGGER.warn("Decryption3(state-" + state + ") is Success'");
		} catch (CryptoException e) {
			baseState = getBaseState();
			LOGGER.error("Decryption(state-" + state + ") is failed. Set Decrypted state value to '" + baseState + "'");
			state = baseState;
		}

		LOGGER.debug("[LoginController]callbackLogin: state " + state);

		//UUID 생성
		String ssoId = UUID.randomUUID().toString();

		// Token 요청
		//EmpAccessTokenResponse tokenResponse = empSsoClient.requestToken("AUTHORIZATION_CODE", code, this.empSsoLoginCallbackUri, null);
		// Token 요청 + sso_id
		EmpAccessTokenResponse tokenResponse = empSsoClient.requestToken("AUTHORIZATION_CODE", code, this.empSsoLoginCallbackUri, ssoId);

		String access_token = tokenResponse.getAccess_token();
		String refresh_token = tokenResponse.getRefresh_token();

		// 세션에 토큰 저장
		request.getSession().setAttribute("ACCESS_TOKEN", access_token);
		request.getSession().setAttribute("REFRESH_TOKEN", refresh_token);
		//토큰 발급시간 세션에 저장.
		request.getSession().setAttribute("TOKEN_ISSUE_DATETIME", tokenIssuedDateFormat.format(new Date()));

		// unifyId 추출
		String unifyId = "";
		if(request.getParameter("unifyId") != null) {
			unifyId = request.getParameter("unifyId");
		}else{
			unifyId = getUnifyId(request, empUserNo);
		}
		LOGGER.debug("unifyId: " + unifyId);

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
		cookie.setDomain(CommonCodes.SSO_TOKEN_DOMAIN);
		cookie.setPath("/");
		// cookie.setSecure(true);
		response.addCookie(cookie);

		Cookie rfcookie = new Cookie("REFRESH_TOKEN", refresh_token);
		rfcookie.setDomain(CommonCodes.SSO_TOKEN_DOMAIN);
		rfcookie.setPath("/");
		rfcookie.setMaxAge(60*60*24*30); //한달로 설정
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

		// 쿠키에 CustomerCartId저장
		Cookie obsCartIDCookie = new Cookie("OBS_CARTID", obs_cartId);
		obsCartIDCookie.setDomain(domain);
		obsCartIDCookie.setPath("/");
		response.addCookie(obsCartIDCookie);

		//sso-client-id 저장 (베스트샵 요구사항)
		Cookie cicookie = new Cookie("CLIENT_ID", this.clientId);
		cicookie.setDomain(domain);
		cicookie.setPath("/");
		// cookie.setSecure(true);
		response.addCookie(cicookie);

		//sso-id 저장 (AccountManager 저장용)
		Cookie ssoIdCookie = new Cookie("sso_id", ssoId);
		ssoIdCookie.setDomain(domain);
		ssoIdCookie.setPath("/");
		// cookie.setSecure(true);
		response.addCookie(ssoIdCookie);

		//앱스플라이어 로그인 이벤트 트래킹(공통 스크립트에서 처리)
		if(request.getHeader("user-agent") != null && request.getHeader("user-agent").contains("LGEAPP")){
			Cookie afCookie = new Cookie("AF_LOGIN", unifyId);
			afCookie.setDomain(domain);
			afCookie.setPath("/");
			response.addCookie(afCookie);

			// 회원통합용 쿠키(통합회원 요구사항)
			Cookie memberCookie = new Cookie("MEMBER_ACCESS_TOKEN", access_token);
			memberCookie.setDomain(CommonCodes.SSO_TOKEN_DOMAIN);
			memberCookie.setPath("/");
			response.addCookie(memberCookie);
		}

		//response.sendRedirect(state);
		ModelAndView mav = new ModelAndView("common/sso/additionalUri");
		mav.addObject("state",state);

		List<Object> additionalLoginUris = configService.getList("additionalLoginUris");
		if(additionalLoginUris == null){
			additionalLoginUris = new ArrayList<Object>();
		}
		List<String> additionalLoginUrisList = additionalLoginUris.stream().map(object -> Objects.toString(object, null))
				.collect(Collectors.toList());
		Gson objGson = new Gson();

		mav.addObject("additionalUris", objGson.toJson(additionalLoginUrisList));

		EmpUserProfileResponse userProfileResponse = empSsoClient.userProfile(access_token);

		//sso용 파라미터 추가
		String cryptoKey = configService.getString("APPAES256Key");
		AES256Util aes256 = new AES256Util(cryptoKey);

		String userIDType = userProfileResponse.getAccount().getUserIDType();
		String userId = !memberInformation.getEmail().isEmpty()?memberInformation.getEmail():memberInformation.getMobileNo();
		mav.addObject("userId", userId);
		mav.addObject("ssoId", aes256.encrypt(ssoId));
		mav.addObject("ci", aes256.encrypt(memberInformation.getCi()));
		mav.addObject("lgecomMbrNo", aes256.encrypt(empUserNo));
		mav.addObject("lgecomSvcUserId", aes256.encrypt(unifyId));
		mav.addObject("idTpCode", userIDType);
		mav.addObject("src_svc_code", "SVC612");

		return mav;

	}

	@RequestMapping(value = "/sso/api/emp/Logout")
	public ModelAndView ssoLogout(HttpServletRequest request, HttpServletResponse response) throws Exception {
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
		if (state.contains(this.empMemberLoginPageUri)) {
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

		//EMP token expire 호출.
		String accessToken = (String) request.getSession().getAttribute("ACCESS_TOKEN");
		try {
			EmpTokenValidationResponse eResponse = empSsoClient.expireToken(accessToken);
		}catch(Exception e){
			LOGGER.error("EMP SSO Token Expire failed. {}",accessToken);
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
		Cookie cookie = new Cookie("ACCESS_TOKEN", null);
		cookie.setMaxAge(0);
		cookie.setDomain(CommonCodes.SSO_TOKEN_DOMAIN);
		cookie.setPath("/");
		response.addCookie(cookie);

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
		memberCookie.setDomain(CommonCodes.SSO_TOKEN_DOMAIN);
		memberCookie.setPath("/");
		// cookie.setSecure(true);
		response.addCookie(memberCookie);

		// ssoCookie 삭제
		Cookie ssoCookie = new Cookie("sso_id", null);
		ssoCookie.setMaxAge(0);
		ssoCookie.setDomain(domain);
		ssoCookie.setPath("/");
		response.addCookie(ssoCookie);

		//Rediriect
		//response.sendRedirect(state);
		ModelAndView mav = new ModelAndView("common/sso/additionalUri");
		mav.addObject("state",state);

		List<Object> additionalLoginUris = configService.getList("additionalLogoutUris");
		if(additionalLoginUris == null){
			additionalLoginUris = new ArrayList<Object>();
		}
		List<String> additionalLoginUrisList = additionalLoginUris.stream().map(object -> Objects.toString(object, null))
				.collect(Collectors.toList());
		Gson objGson = new Gson();

		mav.addObject("additionalUris", objGson.toJson(additionalLoginUrisList));

		return mav;
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

	public String getUnifyId(HttpServletRequest request, String empUserNo) {

		String unifyId = "";

		try {

			LOGGER.warn(String.format("LUnify retrieve - empUserNo: %s", empUserNo));

			String memberUnifyIdUri = configService.getString("memberUnifyIdUri");
			String param = "empNo="+empUserNo;

			HttpUtils httpUtils = new HttpUtils();
			HttpResponse response = httpUtils.makeRequest("GET", memberUnifyIdUri + "?" + param, (String)null, null);
			HttpEntity entity = response.getEntity();
			String responseText = EntityUtils.toString(entity);
			Map unmarshal = JsonUtils.unmarshal(responseText);
			unifyId = (String)unmarshal.get("unifyId");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			LOGGER.error("LUnify Requesting token has failed");
			LOGGER.warn(String.format("LUnify Exception : %s", e.getMessage()));
		}

		return unifyId;
	}

	/**
	 * AccountManager SSO 정보 저장을 위한 Controller Method이다.
	 *
	 * @return
	 * @return String - AM저장 SSO정보
	 */
	@RequestMapping(value = "/sso/api/AmSsoInfo.lgajax", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
	@ResponseBody
	public Map<String, Object> AmSsoInfo(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> returnData = new HashMap<>();
		String status = "success";
		String message = "";
		try {
			MemberInformation sessionMember = (MemberInformation)request.getSession().getAttribute("MemberInformation");
			String accessToken = (String) request.getSession().getAttribute("ACCESS_TOKEN");

			EmpUserProfileResponse userProfileResponse = empSsoClient.userProfile(accessToken);
			String userId = !sessionMember.getEmail().isEmpty()?sessionMember.getEmail():sessionMember.getMobileNo();
			String ci = sessionMember.getCi();
			String empUserNo = userProfileResponse.getAccount().getUserNo();
			String ssoId = getCookie(request, "sso_id");
			String unifyId = sessionMember.getUnifyId();
			String userIDType = userProfileResponse.getAccount().getUserIDType();

			String cryptoKey = configService.getString("APPAES256Key");
			AES256Util aes256 = new AES256Util(cryptoKey);

			StringBuffer amValue = new StringBuffer();
			amValue.append("{\"ci\":\""+aes256.encrypt(ci)+"\"");
			amValue.append(",\"lgecom_mbrno\":\""+aes256.encrypt(empUserNo)+"\"");
			amValue.append(",\"sso_id\":\""+aes256.encrypt(ssoId)+"\"");
			amValue.append(",\"lgecom_svcuserid\":\""+aes256.encrypt(unifyId)+"\"");
			amValue.append(",\"id_tp_code\":\""+userIDType+"\"");
			amValue.append(",\"src_svc_code\":\"SVC612\"}");
			returnData.put("userId", userId);
			returnData.put("amValue", amValue);
		}catch (Exception e) {
			status = "fail";
			message = "error";
		}
		returnData.put("status", status);
		LOGGER.debug( "[Ajax] AmSsoInfo returnData : " + returnData);

		return returnData;
	}
}
