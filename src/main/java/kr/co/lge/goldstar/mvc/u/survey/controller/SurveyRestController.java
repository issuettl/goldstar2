/**
 * 
 */
package kr.co.lge.goldstar.mvc.u.survey.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.lge.goldstar.mvc.u.survey.service.SurveyService;
import kr.co.rebel9.web.data.DataMap;
import kr.co.rebel9.web.rest.result.ResultBuilder;
import kr.co.rebel9.web.rest.result.ResultConst;

/**
 * @author issuettl
 *
 */
@RestController("u.SurveyRestController")
@RequestMapping(value = "u/survey")
public class SurveyRestController {
	
	@Autowired
	private SurveyService surveyService;

	@PostMapping(value = "answer/action.do")
	public DataMap answer(@RequestBody List<Integer> answers) {
		
		try {
			return this.surveyService.memberAnswers(answers);
			
		}catch(Exception e) {
			DataMap result = new DataMap(false);
			result.put("reason", e.getMessage());
			result.put(ResultConst.META, ResultBuilder.failure(e));
			return result;
		}
	}
}
