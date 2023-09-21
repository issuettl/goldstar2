/**
 * 
 */
package kr.co.lge.goldstar.mvc.m.pursue.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.co.lge.goldstar.mvc.m.pursue.service.PursueService;

/**
 * @author issuettl
 *
 */
@Controller("m.PursueController")
@RequestMapping(value = "m/pursue")
public class PursueController {
	
	@Autowired
	private PursueService pursueService;

	/**
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "index.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String index(ModelMap model) {
		
		model.putAll(this.pursueService.getPursue());
         
        return "m/pursue/index";
	}
}
