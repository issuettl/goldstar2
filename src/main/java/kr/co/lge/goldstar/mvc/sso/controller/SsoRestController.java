/**
 * 
 */
package kr.co.lge.goldstar.mvc.sso.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.lge.goldstar.mvc.sso.service.SsoService;
import kr.co.rebel9.web.data.DataMap;

/**
 * @author issuettl
 *
 */
@RestController("SsoRestController")
public class SsoRestController {
	
	public static final String JSON_RESPONSE_SUCCESS_MESSAGE ="success";
    public static final String JSON_RESPONSE_FAILURE_MESSAGE ="fail";
    
	@Autowired
	private SsoService ssoService;
	
	@Value("${lge.url.domain}")
	private String lgeDomain;

	@PostMapping(value = "/sso/loginInfo.do")
	public DataMap loginInfo() {
		
		DataMap result = new DataMap();
		String status = JSON_RESPONSE_SUCCESS_MESSAGE;
		DataMap loginInfo = new DataMap();
		try {
			loginInfo = this.ssoService.loginInfo();

		}catch (Exception e) {
			 status = JSON_RESPONSE_FAILURE_MESSAGE;
		}
		result.put("status", status);
		result.put("data", loginInfo);
		
		return result;
	}

	@PostMapping(value = "/sso/minicart.do")
	public String cart() {
		String result = "{\"data\":{\"cartCnt\":0,\"cartUrl\":\"" + lgeDomain  + "/shop/lgobscart/cart/quote\",\"success\":\"Y\"},\"status\":\"success\"}";
		return result;
	}
	
}
