/**
 * 
 */
package kr.co.lge.goldstar.mvc.u.survey.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import kr.co.lge.goldstar.mvc.u.survey.service.SurveyService;
import kr.co.rebel9.web.data.DataMap;

/**
 * @author issuettl
 *
 */
@Controller("u.SurveyController")
@RequestMapping(value = "u/survey")
public class SurveyController {

	@Resource(name = "u.SurveyService")
	private SurveyService surveyService;

    
    /**
	 * @param sn
	 * @param modelMap 
	 * @return 파일 다운로드
	 */
	@RequestMapping(value = "file/image/{sn}.do", method = RequestMethod.GET)
	public ModelAndView thumbFile(@PathVariable int sn, ModelMap modelMap) {
		
	    DataMap data = this.surveyService.getImageFile(sn);
	    if(!ObjectUtils.isEmpty(data)) {
	        modelMap.putAll(data);
	    }
	    
	    return new ModelAndView("ImageView");
	}
}
