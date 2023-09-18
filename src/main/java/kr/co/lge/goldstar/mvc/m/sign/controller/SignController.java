/**
 * 
 */
package kr.co.lge.goldstar.mvc.m.sign.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author issuettl
 *
 */
@Controller("m.SignController")
@RequestMapping(value = "m/sign")
public class SignController {
	
	@Value("${system.domain}")
	private String systemDomain;

	@GetMapping(value = "qr/{memberSn}/{created}.do")
	public String qr(@PathVariable int memberSn, @PathVariable String created) {
		return "redirect:"  + systemDomain +  "/u/page/index.do";
	}
}
