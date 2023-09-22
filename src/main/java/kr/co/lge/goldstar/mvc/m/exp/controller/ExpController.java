/**
 * 
 */
package kr.co.lge.goldstar.mvc.m.exp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.co.lge.goldstar.mvc.m.exp.service.ExpService;

/**
 * @author issuettl
 *
 */
@Controller("m.ExpController")
@RequestMapping(value = "m/exp")
public class ExpController {
	
	@Autowired
	private ExpService expService;

	/**
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "index.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String index(ModelMap model) {
		
		model.putAll(this.expService.getExps());
         
        return "m/exp/index";
	}
}
