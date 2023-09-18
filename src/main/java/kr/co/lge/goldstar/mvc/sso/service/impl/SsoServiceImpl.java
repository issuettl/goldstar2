/**
 * 
 */
package kr.co.lge.goldstar.mvc.sso.service.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.security.SignatureException;
import java.util.stream.Collectors;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ArrayUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mobile.device.Device;
import org.springframework.mobile.device.DeviceUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import kr.co.lge.goldstar.mvc.sso.service.SsoService;
import kr.co.lge.goldstar.mvc.u.sign.service.SignService;
import kr.co.lge.goldstar.orm.jpa.entity.YesOrNo;
import kr.co.lge.goldstar.orm.jpa.entity.member.MemberEntity;
import kr.co.lge.goldstar.orm.jpa.entity.member.MemberSsoEntity;
import kr.co.lge.goldstar.orm.jpa.repository.spring.MemberSsoRepository;
import kr.co.rebel9.core.utils.DateUtils;
import kr.co.rebel9.web.data.DataMap;
import lge.ko.member.MemberCrypt;

/**
 * @author NEOFLOW
 *
 */
@Service("SsoService")
public class SsoServiceImpl implements SsoService {
	
	@Autowired
	private SignService signService;
	
	@Value("${lge.oauth.host}")
	private String oauthHost;
	
	@Value("${lge.oauth.appKey}")
	private String oauthAppKey;
	
	@Value("${lge.oauth.secret}")
	private String oauthSecret;
	
	@Value("${lge.oauth.uri.datetime}")
	private String oauthUriDatetime;
	
	@Value("${lge.oauth.uri.token}")
	private String oauthUriToken;
	
	@Value("${lge.oauth.uri.userProfile}")
	private String oauthUriUserProfile;
	
	@Value("${lge.sso.login.redirectUrl}")
	private String redirectUrl;
	
	@Value("${lge.sso.account.clientId}")
	private String clientId;
	
	@Value("${lge.sso.account.clientSecret}")
	private String clientSecret;
	
	@Value("${lge.sso.account.getMember}")
	private String accountGetMember;
	
	@Value("${lge.sso.account.tokenConfirm}")
	private String accountTokenConfirm;
	
	@Autowired
	private MemberSsoRepository memberSsoRepository;

	@Override
	public DataMap loginInfo() {

		DataMap result = new DataMap();
		Boolean isLogin = false;
		String name = "";

		MemberEntity member = this.signService.getMemberIn();

		if(this.signService.existMemberIn()){
			isLogin = true;
			name = member.getNameDec();
		}

		result.put("isLogin", isLogin);
		result.put("name", name);

		return result;
	}

	@Override
	public void saveResult(DataMap params) {
		
		MemberSsoEntity ssoEntity = new MemberSsoEntity();
		ssoEntity.setCreated(DateUtils.getToday("yyyyMMddHHmmss"));
		
		if(this.signService.existMemberIn()) {
			ssoEntity.setSuccess(YesOrNo.Y);
		}
		else {
			ssoEntity.setSuccess(YesOrNo.N);
		}
		ssoEntity.setResult(params.toString());
		
		this.memberSsoRepository.save(ssoEntity);
	}

	@Override
	public DataMap setAccountInfo(DataMap params, HttpServletRequest request, HttpServletResponse response) {
		/*
		 * Redirect
			state : 클라이언트에서 전달 받은 값을 리턴
			unifyId : 한영본 회원 번호 (*암호화)
			accessToken : emp의 oauth accsess token (*암호화)
			refreshToken : emp의 oauth refresh token (*암호화)
			lgnCtfNo : 한영본 로그인 인증 번호(세션 조회 시 사용) (*암호화)
			ssoId : Request에서 전달 받은 값
			actCd : ‘LGN’
			oauth2BackendUrl : Oauth 인증 서버 URL (*URL)

		 */
		
		DataMap decrypt = new DataMap();
		params.put("decrypt", decrypt);
		try {
			decrypt.put("unifyId", MemberCrypt.front.decryptAES(params.getAsString("unifyId")));
			decrypt.put("lgnCtfNo", MemberCrypt.front.decryptAES(params.getAsString("lgnCtfNo")));
			decrypt.put("accessToken", MemberCrypt.front.decryptAES(params.getAsString("accessToken")));
			decrypt.put("refreshToken", MemberCrypt.front.decryptAES(params.getAsString("refreshToken")));
		} catch (Exception e1) {
			//e1.printStackTrace();
		}
		
		//setCookie
		Cookie cookie = new Cookie("ACCESS_TOKEN", decrypt.getAsString("accessToken"));
		cookie.setDomain("lge.co.kr");
		cookie.setPath("/");
		cookie.setSecure(true);
		cookie.setComment("access_token goldstar");
		response.addCookie(cookie);
		
		cookie = new Cookie("REFRESH_TOKEN", decrypt.getAsString("refreshToken"));
		cookie.setDomain("lge.co.kr");
		cookie.setPath("/");
		cookie.setSecure(true);
		cookie.setComment("refresh_token goldstar");
		response.addCookie(cookie);
		
		/*
		 * 3 user profile
		 * 
		 *  unifyId
			 lgnCtfNo
			 accessToken
			 autoLoginYn
			 dvcTpCd
		 * 
		 * 
		 */
		DataMap account = getMemberAccount(params, request);
		
		if(ObjectUtils.isEmpty(account)) {
			return params;
		}
		params.put("account", account);
		
		if("000".equals(account.getAsString("ResultCode"))) {
			DataMap data = getDecriptBody(account.getAsString("data"));
			
			params.put("encData", data);
			this.signService.signInMember(params, data);
		}
		
		return params;
	}

	/**
	 * @param params
	 * @return
	 */
	private DataMap getMemberAccount(DataMap params, HttpServletRequest request) {
		
		DataMap encParams = new DataMap();
		DataMap decrypt = params.getAsDataMap("decrypt");
		String device = "";
		Device currentDevice = DeviceUtils.getCurrentDevice(request);
		if(currentDevice != null && currentDevice.isMobile()) {
			device = "MB";
		}
		else if(currentDevice != null && currentDevice.isTablet()){
			device = "PD";
		}
		else {
			device = "PC";
		}
		encParams.put("unifyId", decrypt.getAsString("unifyId"));
		encParams.put("lgnCtfNo", decrypt.getAsString("lgnCtfNo"));
		encParams.put("accessToken", decrypt.getAsString("accessToken"));
		encParams.put("autoLoginYn", "N");
		encParams.put("dvcTpCd", device);
		
		String body = getEncryptBody(encParams);
		params.put("queryJson", body);
		
		HttpGet httpGet = new HttpGet(this.accountGetMember + "?" + body);
		httpGet.setHeader("Accept", "application/json");
		httpGet.setHeader("X_LG_CLIENT_ID", this.clientId);
		httpGet.setHeader("X_LG_CLIENT_SECRET", this.clientSecret);
		
		CloseableHttpClient httpClient = HttpClients.createDefault();
		String text = null;
		
		try {
			CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
			
			InputStreamReader responseReader = new InputStreamReader(
					httpResponse.getEntity().getContent());
			
			text = new BufferedReader(responseReader)
				        .lines()
				        .collect(Collectors.joining("\n"));
			
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			DataMap response = gson.fromJson(text, DataMap.class);

			response.put("httpURI", httpGet.getURI().toURL());
			response.put("query", httpGet.getURI().getQuery());
			
			return response;
			
		} catch (Exception e) {
			e.printStackTrace();
			
			DataMap response = new DataMap();
			try {
				response.put("httpURI", httpGet.getURI().toURL());
				response.put("query", httpGet.getURI().getQuery());
			} catch (MalformedURLException e1) {
				e1.printStackTrace();
			}
			response.put("text", text);
			return response;
		}
	}

	@Override
	public void setAccountInfo(HttpServletRequest request, HttpServletResponse response) {
		/*
		//토큰확인
		Cookie[] cookies = request.getCookies();
		String accessToken = null;
		String refreshToken = null;
		
		if(!ArrayUtils.isEmpty(cookies)) {
			for(Cookie cookie : cookies) {
				if("ACCESS_TOKEN".equals(cookie.getName())) {
					accessToken = cookie.getValue();
				}
				if("REFRESH_TOKEN".equals(cookie.getName())) {
					refreshToken = cookie.getValue();
				}
			}
		}
		
		if(!StringUtils.hasText(accessToken) || !StringUtils.hasText(refreshToken)) {
			
			//로그아웃
			this.signService.memberOut();
			
			return;
		}
		
		MemberEntity singedMember = this.signService.getMemberIn();
		
		DataMap params = new DataMap();
		params.put("accessToken", accessToken);
		params.put("refreshToken", refreshToken);
		
		DataMap tokenConfirm = null;
		try {
			tokenConfirm = getTokenConfirm(accessToken);
		} catch (SignatureException e) {
			e.printStackTrace();
		}
		if(ObjectUtils.isEmpty(tokenConfirm)) {
			return;
		}
		if(!"000".equals(tokenConfirm.getAsString("resultCode"))) {
			return;
		}
		params.put("tokenConfirm", tokenConfirm);
		
		//body 디코딩
		DataMap tokenConfirmData = getDecriptBody(params.getAsString("data"));
		params.put("tokenConfirmData", tokenConfirmData);
		
		int expireTime = tokenConfirmData.getAsInt("expireTime");
		if(expireTime > 0) { //토큰 유효함
			
			//세션 회원체크
			MemberEntity signed = this.signService.getMemberIn();
			
			//세션유효
			if(!ObjectUtils.isEmpty(signed) && accessToken.equals(signed.getAccessToken())) {
				return;
			}
			
			//세션이 없거나, 세션이 유효하지 않으면 토큰으로 세션 재생성
			
			//로그아웃
			this.signService.memberOut();
			
			DataMap account = null;
			try {
				
				DataMap decrypt = new DataMap();
				params.put("decrypt", decrypt);
				try {
					decrypt.put("unifyId", MemberCrypt.front.decryptAES(params.getAsString("unifyId")));
					decrypt.put("lgnCtfNo", MemberCrypt.front.decryptAES(params.getAsString("lgnCtfNo")));
					decrypt.put("accessToken", MemberCrypt.front.decryptAES(params.getAsString("accessToken")));
					decrypt.put("refreshToken", MemberCrypt.front.decryptAES(params.getAsString("refreshToken")));
				} catch (Exception e1) {
					//e1.printStackTrace();
				}
				
				params.put("unifyId", data.getAsString("unifyId"));
				
				account = getMemberAccount(params, request);
			} catch (SignatureException e) {
				e.printStackTrace();
			}
			if(ObjectUtils.isEmpty(userProfile)) {
				return;
			}
			params.put("userProfile", userProfile);
			
			DataMap account = userProfile.getAsDataMap("account");
			if(!ObjectUtils.isEmpty(account)) {

				account.put("access_token", accessToken);
				account.put("refresh_token", refreshToken);
				
				this.signService.signInMember(account, "");
			}
			
			return;
		}
		
		DataMap token = null;
		try {
			token = getTokenRefresh(params);
		} catch (SignatureException e) {
			e.printStackTrace();
		}
		if(ObjectUtils.isEmpty(token)) {
			return;
		}
		params.put("token", token);
		
		//로그아웃
		this.signService.memberOut();
		
		//setCookie
		Cookie cookie = new Cookie("ACCESS_TOKEN", token.getAsString("access_token"));
		cookie.setDomain("lge.co.kr");
		cookie.setPath("/");
		cookie.setSecure(true);
		cookie.setComment("access_token goldstar");
		response.addCookie(cookie);
		
		cookie = new Cookie("REFRESH_TOKEN", params.getAsString("refresh_token"));
		cookie.setDomain("lge.co.kr");
		cookie.setPath("/");
		cookie.setSecure(true);
		cookie.setComment("refresh_token goldstar");
		response.addCookie(cookie);
		
		DataMap userProfile = null;
		try {
			userProfile = getUserProfile(params);
		} catch (SignatureException e) {
			e.printStackTrace();
		}
		if(ObjectUtils.isEmpty(userProfile)) {
			return;
		}
		params.put("userProfile", userProfile);
		
		DataMap account = userProfile.getAsDataMap("account");
		if(!ObjectUtils.isEmpty(account)) {

			account.put("access_token", token.getAsString("access_token"));
			account.put("refresh_token", params.getAsString("refresh_token"));
			
			this.signService.signInMember(account, "");
		}
		*/
	}

	private DataMap getDecriptBody(String encData) {
		
		DataMap data = new DataMap();
		if(encData != null) {
			try {
				byte[] decodedBytes = java.util.Base64.getDecoder().decode(encData);
				String decodedString = new String(decodedBytes);

				Gson gson = new GsonBuilder().setPrettyPrinting().create();
				data = gson.fromJson(decodedString, DataMap.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return data;
	}

	private String getEncryptBody(DataMap encParams) {
		
		Gson gson = new Gson();
		String body = gson.toJson(encParams);
		
		try {
			body = java.util.Base64.getEncoder().encodeToString(body.getBytes());
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		
		return body;
	}

	/**
	 * @param params
	 * @return
	 * @throws SignatureException 
	 */
	private DataMap getTokenConfirm(String accessToken) throws SignatureException {
		
		MemberEntity signedMember = this.signService.getMemberIn();
		
		DataMap encParams = new DataMap();
		encParams.put("accessToken", accessToken);
		encParams.put("oauth2BackendUrl", signedMember.getOauth2BackendUri());
		
		String body = getEncryptBody(encParams);
		
		HttpPost httpPost = new HttpPost(this.accountTokenConfirm);
		httpPost.setHeader("Accept", "application/json");
		httpPost.setHeader("X_LG_CLIENT_ID", this.clientId);
		httpPost.setHeader("X_LG_CLIENT_SECRET", this.clientSecret);
		
		CloseableHttpClient httpClient = HttpClients.createDefault();
		String text = null;
		
		try {
			
			httpPost.setEntity(new StringEntity(body));
			
			CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
			
			InputStreamReader responseReader = new InputStreamReader(
					httpResponse.getEntity().getContent());
			
			text = new BufferedReader(responseReader)
				        .lines()
				        .collect(Collectors.joining("\n"));
			
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			DataMap response = gson.fromJson(text, DataMap.class);

			response.put("httpURI", httpPost.getURI().toURL());
			response.put("query", httpPost.getURI().getQuery());
			
			return response;
			
		} catch (Exception e) {
			e.printStackTrace();
			
			DataMap response = new DataMap();
			try {
				response.put("httpURI", httpPost.getURI().toURL());
				response.put("query", httpPost.getURI().getQuery());
			} catch (MalformedURLException e1) {
				e1.printStackTrace();
			}
			response.put("text", text);
			return response;
		}
	}
}
