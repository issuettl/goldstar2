/**
 * 
 */
package kr.co.lge.goldstar.mvc.m.survey.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.co.lge.goldstar.mvc.m.survey.service.SurveyService;

/**
 * @author issuettl
 *
 */
@Controller("m.SurveyController")
@RequestMapping(value = "m/survey")
public class SurveyController {
	
	@Autowired
	private SurveyService surveyService;

	/**
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "index.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String index(ModelMap model) {
		
		model.putAll(this.surveyService.getSurvey());
         
        return "m/survey/index";
	}
}
