/**
 * 
 */
package kr.co.lge.goldstar.mvc.u.life.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.lge.goldstar.mvc.u.life.service.LifeService;
import kr.co.lge.goldstar.mvc.u.sign.service.SignService;
import kr.co.lge.goldstar.orm.jpa.entity.LifeTime;

/**
 * @author issuettl
 *
 */
@Controller("u.LifeController")
@RequestMapping(value = "u/life")
public class LifeController {
	
	@Autowired
	private SignService signService;
	
	@Autowired
	private LifeService lifeService;
	
	@Value("${system.domain}")
	private String systemDomain;
	
	@RequestMapping(value = "about.do")
	public String about(ModelMap model) {
		
		boolean isMemberIn = this.signService.existMemberIn();
		model.put("isMemberIn", isMemberIn);
		
		return "u/life/about";
	}
	
	@RequestMapping(value = "weekday.do")
	public String weekday(ModelMap model) {
		
		boolean isMemberIn = this.signService.existMemberIn();
		if(!isMemberIn) {
			return "redirect:"  + systemDomain +  "/sso/account/in.do?state=/u/life/weekday.do";
		}
		
		model.put("times", LifeTime.values());
		model.put("weekDates", this.lifeService.getWeekdayDates());
		
		return "u/life/weekday";
	}
	
	@RequestMapping(value = "weekend.do")
	public String weekend(ModelMap model) {
		
		boolean isMemberIn = this.signService.existMemberIn();
		if(!isMemberIn) {
			return "redirect:" + systemDomain + "/sso/account/in.do?state=/u/life/weekend.do";
		}
		
		model.put("times", LifeTime.values());
		model.put("weekDates", this.lifeService.getWeekendDates());
		
		return "u/life/weekend";
	}
	
	@RequestMapping(value = "list.do")
	public String list(ModelMap model) {
		
		boolean isMemberIn = this.signService.existMemberIn();
		if(!isMemberIn) {
			return "redirect:" + systemDomain + "/sso/account/in.do?state=/u/life/list.do";
		}

		model.put("list", this.lifeService.myList());
		
		return "u/life/list";
	}
}
