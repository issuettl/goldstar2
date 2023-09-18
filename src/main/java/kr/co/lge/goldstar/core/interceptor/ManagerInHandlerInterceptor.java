package kr.co.lge.goldstar.core.interceptor;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import kr.co.lge.goldstar.mvc.c.request.service.RequestService;
import kr.co.lge.goldstar.orm.jpa.entity.member.ManagerEntity;
import kr.co.rebel9.web.utils.RequestUtils;
import lombok.extern.slf4j.Slf4j;


/**
 * @author issuettl
 * @since 2018. 4. 10.
 * @version 1.0
 *
 * <pre>
 * Name : AdminWebHandlerInterceptor.java
 * Description  : 관리자 인증 체크
 * 
 * << 개정이력(Modification Information) >>
 *   
 *   수정일                         수정자                     수정내용
 * ==============================================
 *  2018. 4. 10.      issuettl         최초 생성
 *
 * </pre>
 */
@Slf4j
public class ManagerInHandlerInterceptor implements HandlerInterceptor {

	private static final String SIGNIN_KEY = "AUTH-MANAGER";
	
	@Value("${sign.manager.failure.uri}")
	private String failureUri;
	
	@Value("${sign.manager.request.uri}")
	private String requestUri;
	
	@Value("${sign.manager.request.encode}")
	private String requestUriEncode;
	
	@Value("${sign.manager.pass.uri}")
	private String passUri;
	
	@Value("${sign.manager.pass.ip}")
	private String passIp;
	
	@Value("${sign.manager.check}")
	private String checkUri;
	
	@Value("${system.domain}")
	private String systemDomain;
	
	@Autowired
	private RequestService requestService;
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		
		HttpSession session = request.getSession();
		String uri = RequestUtils.getRequestURIExcludeContextPath(request);

		log.debug("session creationTime : {}", session.getCreationTime());
		log.debug("sessionid : {}", session.getId());
		log.debug("{}", (ManagerEntity)session.getAttribute(SIGNIN_KEY));

		log.debug("ip : " + this.requestService.getIp());
		/*
		 * ip체크 여부
		 */
		boolean isIpPassable = isIpPassable(this.requestService.getIp());
		
		log.debug("isIpPassable : " + isIpPassable);
		
		/*
		 * ip체크 대상이 아니면 허용안함
		 */
		if(!isIpPassable){
			log.debug("허용된 아이피가 아닙니다.");
			response.sendRedirect(systemDomain);
			return false;
		}

		log.debug("uri : " + uri);
		/*
		 * 인증체크 여부
		 */
		boolean isAuthCheckable = isAuthCheckable(uri);
		
		log.debug("isAuthCheckable : " + isAuthCheckable);
		
		/*
		 * 인증체크 대상이 아니면 패스
		 */
		if(!isAuthCheckable){
			return true;
		}
		
		/*
		 * 인증체크 패스여부
		 */
		boolean isAuthPassable = isAuthPassable(uri);
		
		log.debug("isAuthPassable : " + isAuthPassable);
		
		/*
		 * 인증체크 패스대상
		 */
		if(isAuthPassable){
			return true;
		}

		/*
		 * 접근한 회원정보
		 */
		ManagerEntity sign = (ManagerEntity)session.getAttribute(SIGNIN_KEY);
		if(ObjectUtils.isEmpty(sign)){
			log.debug("로그인 정보가 존재 하지 않습니다.");
			response.sendRedirect(getSignRequestUri(uri, RequestUtils.getParams(request)));
			return false;
		}
		
		return true;
	}

	/**
	 * @param uri
	 * @param params
	 * @return
	 */
	private String getSignRequestUri(String uri, String params) {
		
		/*
		 * 로그인 후 접근 uri 로 로그인한다.
		 */
		StringBuilder requestURI = new StringBuilder();
		requestURI.append(uri);
		if(params != null && params.length() > 0){
			requestURI.append("?");
			requestURI.append(params);
		}

		StringBuilder loginURI = new StringBuilder();
		loginURI.append(requestUri);
		try {
			String returnURI = URLEncoder.encode(requestURI.toString(), requestUriEncode);
			if(requestUri.indexOf("?") > -1){
				loginURI.append("&");
			}
			else {
				loginURI.append("?");
			}
			loginURI.append("returnURI=");
			loginURI.append(returnURI);
		} catch (UnsupportedEncodingException e) {
			log.debug(e.getMessage());
		}
		
		log.debug("loginURI : " + loginURI);
		
		return loginURI.toString();
	}

	/**
	 * @param ip remoteHost
	 * @return 아이피패스 여부
	 */
	private boolean isIpPassable(String ip) {
		
		/*
		 * 인증패스 대상
		 */
		String[] passableIpList = null;
		if(StringUtils.hasText(passIp)){
			passableIpList = passIp.split(",");
		}
		if(passableIpList == null || passableIpList.length == 0){
			return false;
		}
		
		for(String passableIp : passableIpList){
			
			if("*".equals(passableIp.trim())) {
				return true;
			}
			
			if(ip.startsWith(passableIp.trim())){
				return true;
			}
		}
		
		return false;
	}

	/**
	 * @param uri RequestURI
	 * @return 인증패스 여부
	 */
	private boolean isAuthPassable(String uri) {
		
		/*
		 * 인증패스 대상
		 */
		String[] passableUriList = null;
		if(StringUtils.hasText(passUri)){
			passableUriList = passUri.split(",");
		}
		if(passableUriList == null || passableUriList.length == 0){
			return false;
		}
		
		for(String passableUri : passableUriList){
			if(uri.startsWith(passableUri)){
				return true;
			}
		}
		
		return false;
	}

	/**
	 * @param uri RequestURI
	 * @return 인증체크 여부
	 */
	private boolean isAuthCheckable(String uri) {
		
		/*
		 * 인증체크 대상
		 */
		String[] checkableUriList = null;
		if(StringUtils.hasText(checkUri)){
			checkableUriList = checkUri.split(",");
		}
		if(checkableUriList == null || checkableUriList.length == 0){
			return false;
		}
		
		for(String checkableUri : checkableUriList){
			if(uri.startsWith(checkableUri)){
				return true;
			}
		}
		
		return false;
	}

	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3) throws Exception {
		//
	}

	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3) throws Exception {
		//
		
	}
}
