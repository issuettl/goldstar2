/**
 * 
 */
package kr.co.lge.goldstar.mvc.m.pursue.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.co.lge.goldstar.mvc.m.pursue.service.PursueService;
import kr.co.lge.goldstar.orm.jpa.entity.pursue.PursueEntity;
import kr.co.rebel9.web.data.DataMap;
import kr.co.rebel9.web.rest.result.ResultBuilder;
import kr.co.rebel9.web.rest.result.ResultConst;

/**
 * @author issuettl
 *
 */
@RestController("m.PursueRestController")
@RequestMapping(value = "m/pursue")
public class PursueRestController {
	
	@Autowired
	private PursueService pursueService;
	
	@PostMapping(value = "update.do")
	public DataMap update(@RequestBody PursueEntity pursueEntity) {
		
		try {
			return this.pursueService.update(pursueEntity);
			
		}catch(Exception e) {
			DataMap result = new DataMap(false);
			result.put(ResultConst.META, ResultBuilder.failure(e));
			return result;
		}
	}
	
	@PostMapping(value = "status.do")
	public DataMap status(@RequestBody PursueEntity pursueEntity) {
		
		try {
			return this.pursueService.status(pursueEntity);
			
		}catch(Exception e) {
			DataMap result = new DataMap(false);
			result.put(ResultConst.META, ResultBuilder.failure(e));
			return result;
		}
	}

	@PostMapping(value = "get.do")
	public DataMap get(@RequestBody PursueEntity pursueEntity) {
		
		try {
			return this.pursueService.get(pursueEntity);
			
		}catch(Exception e) {
			DataMap result = new DataMap(false);
			result.put(ResultConst.META, ResultBuilder.failure(e));
			return result;
		}
	}

	@PostMapping(value = "sort.do")
	public DataMap pursueSort(@RequestParam MultiValueMap<String, Object> paramMap) {
		
		try {
			return this.pursueService.sort(new DataMap(paramMap));
			
		}catch(Exception e) {
			DataMap result = new DataMap(false);
			result.put(ResultConst.META, ResultBuilder.failure(e));
			return result;
		}
	}
}
