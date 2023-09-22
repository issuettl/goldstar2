/**
 * 
 */
package kr.co.lge.goldstar.mvc.m.exp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import kr.co.lge.goldstar.mvc.m.exp.service.ExpService;
import kr.co.lge.goldstar.mvc.m.system.service.SystemService;
import kr.co.lge.goldstar.orm.jpa.entity.survey.SurveyAnswerEntity;
import kr.co.lge.goldstar.orm.jpa.entity.system.SystemEntity;
import kr.co.rebel9.web.data.DataMap;
import kr.co.rebel9.web.rest.result.ResultBuilder;
import kr.co.rebel9.web.rest.result.ResultConst;

/**
 * @author issuettl
 *
 */
@RestController("m.ExpRestController")
@RequestMapping(value = "m/exp")
public class ExpRestController {
	
	@Autowired
	private ExpService expService;
	
	@Autowired
	private SystemService systemService;

	@PostMapping(value = "save.do")
	public DataMap save(MultipartFile imageFile, @RequestParam MultiValueMap<String, Object> params) {
		
		try {
			return this.expService.save(imageFile, new DataMap(params));
			
		}catch(Exception e) {
			DataMap result = new DataMap(false);
			result.put(ResultConst.META, ResultBuilder.failure(e));
			return result;
		}
	}
	
	@PostMapping(value = "update.do")
	public DataMap update(MultipartFile imageFile, @RequestParam MultiValueMap<String, Object> params) {
		
		try {
			return this.expService.update(imageFile, new DataMap(params));
			
		}catch(Exception e) {
			DataMap result = new DataMap(false);
			result.put(ResultConst.META, ResultBuilder.failure(e));
			return result;
		}
	}

	
	@PostMapping(value = "remove.do")
	public DataMap remove(@RequestBody SurveyAnswerEntity surveyAnswerEntity) {
		
		try {
			return this.expService.remove(surveyAnswerEntity);
			
		}catch(Exception e) {
			DataMap result = new DataMap(false);
			result.put(ResultConst.META, ResultBuilder.failure(e));
			return result;
		}
	}

	@PostMapping(value = "get.do")
	public DataMap get(@RequestBody SurveyAnswerEntity surveyAnswerEntity) {
		
		try {
			return this.expService.get(surveyAnswerEntity);
			
		}catch(Exception e) {
			DataMap result = new DataMap(false);
			result.put(ResultConst.META, ResultBuilder.failure(e));
			return result;
		}
	}

	@PostMapping(value = "sort.do")
	public DataMap sort(@RequestParam MultiValueMap<String, Object> paramMap) {
		
		try {
			return this.expService.sort(new DataMap(paramMap));
			
		}catch(Exception e) {
			DataMap result = new DataMap(false);
			result.put(ResultConst.META, ResultBuilder.failure(e));
			return result;
		}
	}
	
	@PostMapping(value = "system.do")
	public DataMap system(@RequestBody SystemEntity systemEntity) {
		
		try {
			return this.systemService.product(systemEntity);
			
		}catch(Exception e) {
			DataMap result = new DataMap(false);
			result.put(ResultConst.META, ResultBuilder.failure(e));
			return result;
		}
	}
}
