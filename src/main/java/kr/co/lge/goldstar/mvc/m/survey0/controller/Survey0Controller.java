/**
 * 
 */
package kr.co.lge.goldstar.mvc.m.survey0.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import kr.co.lge.goldstar.mvc.m.survey0.service.Survey0Service;
import kr.co.rebel9.web.data.DataMap;

/**
 * @author issuettl
 *
 */
@Controller("m.Survey0Controller")
@RequestMapping(value = "m/survey0")
public class Survey0Controller {
	
	@Autowired
	private Survey0Service surveyService;

	/**
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "index.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String index(ModelMap model) {
		
		model.putAll(this.surveyService.getSurvey());
         
        return "m/survey/index";
	}

	/**
	 * @param paramMap
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "answer.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String answer(@RequestParam MultiValueMap<String, Object> paramMap, ModelMap model) {
		
		model.putAll(this.surveyService.getAnswer(new DataMap(paramMap)));
         
        return "m/survey/answer";
	}
}
