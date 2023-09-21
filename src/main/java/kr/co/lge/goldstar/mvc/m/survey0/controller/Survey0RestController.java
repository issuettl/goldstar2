/**
 * 
 */
package kr.co.lge.goldstar.mvc.m.survey0.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.co.lge.goldstar.mvc.m.survey0.service.Survey0Service;
import kr.co.lge.goldstar.orm.jpa.entity.indiv.IndivEntity;
import kr.co.lge.goldstar.orm.jpa.entity.mind.MindEntity;
import kr.co.lge.goldstar.orm.jpa.entity.mood.MoodEntity;
import kr.co.lge.goldstar.orm.jpa.entity.style.StyleEntity;
import kr.co.rebel9.web.data.DataMap;
import kr.co.rebel9.web.rest.result.ResultBuilder;
import kr.co.rebel9.web.rest.result.ResultConst;

/**
 * @author issuettl
 *
 */
@RestController("m.Survey0RestController")
@RequestMapping(value = "m/survey0")
public class Survey0RestController {
	
	@Autowired
	private Survey0Service surveyService;

	@PostMapping(value = "mind/save.do")
	public DataMap save(@RequestBody MindEntity mindEntity) {
		
		try {
			return this.surveyService.save(mindEntity);
			
		}catch(Exception e) {
			DataMap result = new DataMap(false);
			result.put(ResultConst.META, ResultBuilder.failure(e));
			return result;
		}
	}

	@PostMapping(value = "indiv/save.do")
	public DataMap save(@RequestBody IndivEntity indivEntity) {
		
		try {
			return this.surveyService.save(indivEntity);
			
		}catch(Exception e) {
			DataMap result = new DataMap(false);
			result.put(ResultConst.META, ResultBuilder.failure(e));
			return result;
		}
	}

	@PostMapping(value = "style/save.do")
	public DataMap save(@RequestBody StyleEntity styleEntity) {
		
		try {
			return this.surveyService.save(styleEntity);
			
		}catch(Exception e) {
			DataMap result = new DataMap(false);
			result.put(ResultConst.META, ResultBuilder.failure(e));
			return result;
		}
	}

	@PostMapping(value = "mood/save.do")
	public DataMap save(@RequestBody MoodEntity moodEntity) {
		
		try {
			return this.surveyService.save(moodEntity);
			
		}catch(Exception e) {
			DataMap result = new DataMap(false);
			result.put(ResultConst.META, ResultBuilder.failure(e));
			return result;
		}
	}
	
	@PostMapping(value = "mind/update.do")
	public DataMap update(@RequestBody MindEntity mindEntity) {
		
		try {
			return this.surveyService.update(mindEntity);
			
		}catch(Exception e) {
			DataMap result = new DataMap(false);
			result.put(ResultConst.META, ResultBuilder.failure(e));
			return result;
		}
	}

	@PostMapping(value = "indiv/update.do")
	public DataMap update(@RequestBody IndivEntity indivEntity) {
		
		try {
			return this.surveyService.update(indivEntity);
			
		}catch(Exception e) {
			DataMap result = new DataMap(false);
			result.put(ResultConst.META, ResultBuilder.failure(e));
			return result;
		}
	}

	@PostMapping(value = "style/update.do")
	public DataMap update(@RequestBody StyleEntity styleEntity) {
		
		try {
			return this.surveyService.update(styleEntity);
			
		}catch(Exception e) {
			DataMap result = new DataMap(false);
			result.put(ResultConst.META, ResultBuilder.failure(e));
			return result;
		}
	}

	@PostMapping(value = "mood/update.do")
	public DataMap update(@RequestBody MoodEntity moodEntity) {
		
		try {
			return this.surveyService.update(moodEntity);
			
		}catch(Exception e) {
			DataMap result = new DataMap(false);
			result.put(ResultConst.META, ResultBuilder.failure(e));
			return result;
		}
	}
	
	@PostMapping(value = "mind/remove.do")
	public DataMap remove(@RequestBody MindEntity mindEntity) {
		
		try {
			return this.surveyService.remove(mindEntity);
			
		}catch(Exception e) {
			DataMap result = new DataMap(false);
			result.put(ResultConst.META, ResultBuilder.failure(e));
			return result;
		}
	}

	@PostMapping(value = "indiv/remove.do")
	public DataMap remove(@RequestBody IndivEntity indivEntity) {
		
		try {
			return this.surveyService.remove(indivEntity);
			
		}catch(Exception e) {
			DataMap result = new DataMap(false);
			result.put(ResultConst.META, ResultBuilder.failure(e));
			return result;
		}
	}

	@PostMapping(value = "style/remove.do")
	public DataMap remove(@RequestBody StyleEntity styleEntity) {
		
		try {
			return this.surveyService.remove(styleEntity);
			
		}catch(Exception e) {
			DataMap result = new DataMap(false);
			result.put(ResultConst.META, ResultBuilder.failure(e));
			return result;
		}
	}

	@PostMapping(value = "mood/remove.do")
	public DataMap remove(@RequestBody MoodEntity moodEntity) {
		
		try {
			return this.surveyService.remove(moodEntity);
			
		}catch(Exception e) {
			DataMap result = new DataMap(false);
			result.put(ResultConst.META, ResultBuilder.failure(e));
			return result;
		}
	}
	
	@PostMapping(value = "mind/status.do")
	public DataMap status(@RequestBody MindEntity mindEntity) {
		
		try {
			return this.surveyService.status(mindEntity);
			
		}catch(Exception e) {
			DataMap result = new DataMap(false);
			result.put(ResultConst.META, ResultBuilder.failure(e));
			return result;
		}
	}

	@PostMapping(value = "indiv/status.do")
	public DataMap status(@RequestBody IndivEntity indivEntity) {
		
		try {
			return this.surveyService.status(indivEntity);
			
		}catch(Exception e) {
			DataMap result = new DataMap(false);
			result.put(ResultConst.META, ResultBuilder.failure(e));
			return result;
		}
	}

	@PostMapping(value = "style/status.do")
	public DataMap status(@RequestBody StyleEntity styleEntity) {
		
		try {
			return this.surveyService.status(styleEntity);
			
		}catch(Exception e) {
			DataMap result = new DataMap(false);
			result.put(ResultConst.META, ResultBuilder.failure(e));
			return result;
		}
	}

	@PostMapping(value = "mood/status.do")
	public DataMap status(@RequestBody MoodEntity moodEntity) {
		
		try {
			return this.surveyService.status(moodEntity);
			
		}catch(Exception e) {
			DataMap result = new DataMap(false);
			result.put(ResultConst.META, ResultBuilder.failure(e));
			return result;
		}
	}

	@PostMapping(value = "mind/get.do")
	public DataMap get(@RequestBody MindEntity mindEntity) {
		
		try {
			return this.surveyService.get(mindEntity);
			
		}catch(Exception e) {
			DataMap result = new DataMap(false);
			result.put(ResultConst.META, ResultBuilder.failure(e));
			return result;
		}
	}

	@PostMapping(value = "indiv/get.do")
	public DataMap get(@RequestBody IndivEntity indivEntity) {
		
		try {
			return this.surveyService.get(indivEntity);
			
		}catch(Exception e) {
			DataMap result = new DataMap(false);
			result.put(ResultConst.META, ResultBuilder.failure(e));
			return result;
		}
	}

	@PostMapping(value = "style/get.do")
	public DataMap get(@RequestBody StyleEntity styleEntity) {
		
		try {
			return this.surveyService.get(styleEntity);
			
		}catch(Exception e) {
			DataMap result = new DataMap(false);
			result.put(ResultConst.META, ResultBuilder.failure(e));
			return result;
		}
	}

	@PostMapping(value = "mood/get.do")
	public DataMap get(@RequestBody MoodEntity moodEntity) {
		
		try {
			return this.surveyService.get(moodEntity);
			
		}catch(Exception e) {
			DataMap result = new DataMap(false);
			result.put(ResultConst.META, ResultBuilder.failure(e));
			return result;
		}
	}

	@PostMapping(value = "mind/sort.do")
	public DataMap mindSort(@RequestParam MultiValueMap<String, Object> paramMap) {
		
		try {
			return this.surveyService.mindSort(new DataMap(paramMap));
			
		}catch(Exception e) {
			DataMap result = new DataMap(false);
			result.put(ResultConst.META, ResultBuilder.failure(e));
			return result;
		}
	}

	@PostMapping(value = "indiv/sort.do")
	public DataMap indivSort(@RequestParam MultiValueMap<String, Object> paramMap) {
		
		try {
			return this.surveyService.indivSort(new DataMap(paramMap));
			
		}catch(Exception e) {
			DataMap result = new DataMap(false);
			result.put(ResultConst.META, ResultBuilder.failure(e));
			return result;
		}
	}

	@PostMapping(value = "style/sort.do")
	public DataMap styleSort(@RequestParam MultiValueMap<String, Object> paramMap) {
		
		try {
			return this.surveyService.styleSort(new DataMap(paramMap));
			
		}catch(Exception e) {
			DataMap result = new DataMap(false);
			result.put(ResultConst.META, ResultBuilder.failure(e));
			return result;
		}
	}

	@PostMapping(value = "mood/sort.do")
	public DataMap moodSort(@RequestParam MultiValueMap<String, Object> paramMap) {
		
		try {
			return this.surveyService.moodSort(new DataMap(paramMap));
			
		}catch(Exception e) {
			DataMap result = new DataMap(false);
			result.put(ResultConst.META, ResultBuilder.failure(e));
			return result;
		}
	}
}
