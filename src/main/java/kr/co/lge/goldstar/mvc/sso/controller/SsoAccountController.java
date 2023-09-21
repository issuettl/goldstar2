/**
 * 
 */
package kr.co.lge.goldstar.mvc.sso.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import kr.co.lge.goldstar.mvc.c.encrypt.service.EncryptService;
import kr.co.lge.goldstar.mvc.sso.service.SsoService;
import kr.co.lge.goldstar.mvc.u.sign.service.SignService;
import kr.co.lge.goldstar.orm.jpa.entity.member.MemberEntity;
import kr.co.rebel9.web.data.DataMap;
import kr.co.rebel9.web.utils.RequestUtils;

/**
 * @author issuettl
 *
 */
@Controller("SsoAccountController")
public class SsoAccountController {
	
	@Autowired
	private SignService signService;
	
	@Autowired
	private SsoService ssoService;
	
	@Autowired
	private EncryptService encryptService;
	
	@Value("${lge.sso.account.login}")
	private String ssoLoginUrl;
	
	@Value("${lge.sso.account.logout}")
	private String ssoLogoutUrl;
	
	@Value("${lge.sso.account.clientId}")
	private String clientId;
	
	@Value("${lge.sso.account.clientSecret}")
	private String clientSecret;
	
	@Value("${lge.sso.account.loginRedirectUrl}")
	private String loginRedirectUrl;
	
	@Value("${lge.sso.account.loginRedirectUrl}")
	private String logoutRedirectUrl;
	
	@Value("${system.domain}")
	private String systemDomain;
	
	@Value("${lge.url.domain}")
	private String lgeDomain;
	
	@RequestMapping(value = "/sso/account/out.do")
	public RedirectView signOut(@RequestParam MultiValueMap<String, Object> paramMap) {
		
		//LG회원인지 체크 LG 회원확인으로 이동
		boolean isMemberIn = this.signService.existMemberIn();
		RedirectView redirectView = new RedirectView();
		
		if(!isMemberIn) {
			redirectView.setUrl(systemDomain + "/u/page/index.do");
			return redirectView;
		}
		
		MemberEntity memberEntity = this.signService.getMemberIn();
		
		DataMap params = new DataMap(paramMap);
		String state = params.getAsString("state");
		
		String redirectUrl;
		try {
			state = StringUtils.hasText(state) ? URLEncoder.encode(state, "UTF-8") : "";
			redirectUrl = URLEncoder.encode(this.logoutRedirectUrl, "UTF-8");
		} catch (Exception e) {
			state = "";
			redirectUrl = "";
		}
		
		StringBuilder ssoRedirectUrl = new StringBuilder();
		ssoRedirectUrl
		.append(ssoLogoutUrl)
		.append("?clientId=").append(clientId)
		.append("&clientSecret=").append(clientSecret)
		.append("&state=").append(state)
		.append("&redirectUrl=").append(redirectUrl)
		.append("&accessToken=").append(memberEntity.getAccessTokenEnc())
		.append("&unifyId=").append(memberEntity.getUnifyIdEnc());
		
		this.signService.memberOut();
		
		redirectView.setUrl(ssoRedirectUrl.toString());
		
		return redirectView;
	}
	
	@RequestMapping(value = "/sso/account/in.do")
	public RedirectView signIn(@RequestParam MultiValueMap<String, Object> paramMap, HttpSession session) {
		
		RedirectView redirectView = new RedirectView();
		
		DataMap params = new DataMap(paramMap);
		String state = params.getAsString("state");
		
		try {
			state = StringUtils.hasText(state) ? URLDecoder.decode(state, "UTF-8") : "";
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		
		//LG회원인지 체크 LG 회원확인으로 이동
		boolean isMemberIn = this.signService.existMemberIn();
		if(!isMemberIn) {

			String profile = System.getProperty("spring.profiles.active");
			if("local".equals(profile) || "cafe".equals(profile)) {
				
				DataMap member = new DataMap();
				
				member.put("unifyId", RequestUtils.getRequest().getSession().getId());
				member.put("mbrNm", "홍길동" + (member.getAsString("unifyId").substring(0,1)));
				member.put("cphn", "0101111" + (member.getAsString("unifyId").substring(0,4)));
				member.put("empMbrNo", "abc");
				member.put("decrypt", new DataMap());
				
				this.signService.signInMember(member, member);
				
				if(StringUtils.hasText(state) && state.startsWith("/u/")) {
					redirectView.setUrl(systemDomain + state);
					return redirectView;
				}
				
				redirectView.setUrl(systemDomain + "/u/page/index.do");
				return redirectView;
			}
			
			String redirectUrl;
			try {
				state = StringUtils.hasText(state) ? URLEncoder.encode(state, "UTF-8") : "";
				redirectUrl = URLEncoder.encode(this.loginRedirectUrl, "UTF-8");
			} catch (Exception e) {
				state = "";
				redirectUrl = "";
			}
			
			session.invalidate();

			StringBuilder ssoRedirectUrl = new StringBuilder();
			ssoRedirectUrl
			.append(ssoLoginUrl)
			.append("?clientId=").append(clientId)
			.append("&clientSecret=").append(clientSecret)
			.append("&state=").append(state)
			.append("&redirectUrl=").append(redirectUrl);
			
			redirectView.setUrl(ssoRedirectUrl.toString());
			return redirectView;
		}
		
		if(StringUtils.hasText(state) && state.startsWith("/u/")) {
			redirectView.setUrl(systemDomain + state);
			return redirectView;
		}
		
		//메인으로 이동
		redirectView.setUrl(systemDomain + "/u/page/index.do");
		return redirectView;
	}
	
	@RequestMapping(value = "/sso/account/in/action.do")
	public RedirectView signInAction(@RequestParam MultiValueMap<String, Object> paramMap, HttpServletRequest request, HttpServletResponse response) {
		
		DataMap params = new DataMap(paramMap);
		
		/*
		 * {state=VchcCEzJ2aaLiUic9BEyD7lV3VauJ 9jjg3qDDjD2Zg=, 
		 * code=XXo9EZW32EIV7qsfVOxlVZwQv4qwNV, 
		 * enc_user_number=KDR5oqR9K4M1AOY5xKszGA==, 
		 * oauth2_backend_url=https://qa-kr.lgeapi.com/}
		 */
		
		this.ssoService.setAccountInfo(params, request, response);
		
		this.ssoService.saveResult(params);
		
		RedirectView redirectView = new RedirectView();
		
		String state = params.getAsString("state");
		if(StringUtils.hasText(state)) {
			try {
				state = state.replaceAll(" ", "+");
				state = encryptService.decryptAES256(state);
			} catch (Exception e) {
				state = null;
			}
		}
		if(StringUtils.hasText(state) && state.startsWith("/u/")) {
			redirectView.setUrl(systemDomain + state);
			return redirectView;
		}
		
		redirectView.setUrl(systemDomain + "/u/page/index.do");
		return redirectView;
	}
}
