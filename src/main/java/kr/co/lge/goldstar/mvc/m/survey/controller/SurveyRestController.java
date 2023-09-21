/**
 * 
 */
package kr.co.lge.goldstar.mvc.m.survey.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.co.lge.goldstar.mvc.m.survey.service.SurveyService;
import kr.co.lge.goldstar.mvc.m.system.service.SystemService;
import kr.co.lge.goldstar.orm.jpa.entity.survey.SurveyEntity;
import kr.co.lge.goldstar.orm.jpa.entity.system.SystemEntity;
import kr.co.rebel9.web.data.DataMap;
import kr.co.rebel9.web.rest.result.ResultBuilder;
import kr.co.rebel9.web.rest.result.ResultConst;

/**
 * @author issuettl
 *
 */
@RestController("m.SurveyRestController")
@RequestMapping(value = "m/survey")
public class SurveyRestController {
	
	@Autowired
	private SurveyService surveyService;
	
	@Autowired
	private SystemService systemService;

	@PostMapping(value = "save.do")
	public DataMap save(@RequestBody SurveyEntity surveyEntity) {
		
		try {
			return this.surveyService.save(surveyEntity);
			
		}catch(Exception e) {
			DataMap result = new DataMap(false);
			result.put(ResultConst.META, ResultBuilder.failure(e));
			return result;
		}
	}
	
	@PostMapping(value = "update.do")
	public DataMap update(@RequestBody SurveyEntity surveyEntity) {
		
		try {
			return this.surveyService.update(surveyEntity);
			
		}catch(Exception e) {
			DataMap result = new DataMap(false);
			result.put(ResultConst.META, ResultBuilder.failure(e));
			return result;
		}
	}

	
	@PostMapping(value = "remove.do")
	public DataMap remove(@RequestBody SurveyEntity surveyEntity) {
		
		try {
			return this.surveyService.remove(surveyEntity);
			
		}catch(Exception e) {
			DataMap result = new DataMap(false);
			result.put(ResultConst.META, ResultBuilder.failure(e));
			return result;
		}
	}
	
	@PostMapping(value = "status.do")
	public DataMap status(@RequestBody SurveyEntity surveyEntity) {
		
		try {
			return this.surveyService.status(surveyEntity);
			
		}catch(Exception e) {
			DataMap result = new DataMap(false);
			result.put(ResultConst.META, ResultBuilder.failure(e));
			return result;
		}
	}
	
	@PostMapping(value = "system.do")
	public DataMap system(@RequestBody SystemEntity systemEntity) {
		
		try {
			return this.systemService.survey(systemEntity);
			
		}catch(Exception e) {
			DataMap result = new DataMap(false);
			result.put(ResultConst.META, ResultBuilder.failure(e));
			return result;
		}
	}

	@PostMapping(value = "get.do")
	public DataMap get(@RequestBody SurveyEntity surveyEntity) {
		
		try {
			return this.surveyService.get(surveyEntity);
			
		}catch(Exception e) {
			DataMap result = new DataMap(false);
			result.put(ResultConst.META, ResultBuilder.failure(e));
			return result;
		}
	}

	@PostMapping(value = "sort.do")
	public DataMap SurveySort(@RequestParam MultiValueMap<String, Object> paramMap) {
		
		try {
			return this.surveyService.sort(new DataMap(paramMap));
			
		}catch(Exception e) {
			DataMap result = new DataMap(false);
			result.put(ResultConst.META, ResultBuilder.failure(e));
			return result;
		}
	}
}
