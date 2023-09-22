/**
 * 
 */
package kr.co.lge.goldstar.mvc.u.sign.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.lge.goldstar.mvc.u.sign.service.SignService;

/**
 * @author issuettl
 *
 */
@Controller("u.SignController")
@RequestMapping(value = "u/sign")
public class SignController {
	
	@Autowired
	private SignService signService;
	
	@Value("${system.domain}")
	private String systemDomain;
	
	@RequestMapping(value = "name.do")
	public String name() {
		
		//로그인된 상태인지 확인.
		boolean isSignIn = this.signService.existSignIn();
		if(isSignIn) {
			return "redirect:"  + systemDomain +  "/u/exp/worry.do?isSignIn=" + isSignIn;
		}

		boolean isMemberIn = this.signService.existMemberIn();
		if(!isMemberIn) {
			return "redirect:"  + systemDomain +  "/sso/account/in.do?state=/u/sign/name.do";
		}
		
		//오늘 로그인했었는지 확인
		boolean checkSignIn = this.signService.checkSignIn();
		if(checkSignIn) {
			return "redirect:"  + systemDomain +  "/u/exp/worry.do?checkSignIn=" + checkSignIn;
		}

		return "u/sign/name";
	}
}
