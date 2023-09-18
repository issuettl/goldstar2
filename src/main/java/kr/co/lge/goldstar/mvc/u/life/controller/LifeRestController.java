/**
 * 
 */
package kr.co.lge.goldstar.mvc.u.life.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.lge.goldstar.mvc.u.life.service.LifeService;
import kr.co.lge.goldstar.orm.jpa.entity.life.LifePartEntity;
import kr.co.rebel9.web.data.DataMap;
import kr.co.rebel9.web.rest.result.ResultBuilder;
import kr.co.rebel9.web.rest.result.ResultConst;

/**
 * @author issuettl
 *
 */
@RestController("u.LifeRestController")
@RequestMapping(value = "u/life")
public class LifeRestController {
	
	@Autowired
	private LifeService lifeService;

	@PostMapping(value = "booking/times.do")
	public DataMap today(@RequestBody LifePartEntity lifePartEntity) {
		
		try {
			return this.lifeService.bookingTimes(lifePartEntity);
			
		}catch(Exception e) {
			DataMap result = new DataMap(false);
			result.put(ResultConst.META, ResultBuilder.failure(e));
			return result;
		}
	}

	@PostMapping(value = "booking/action.do")
	public DataMap answer(@RequestBody LifePartEntity lifePartEntity) {
		
		try {
			return this.lifeService.bookingAction(lifePartEntity);
			
		}catch(Exception e) {
			DataMap result = new DataMap(false);
			result.put(ResultConst.META, ResultBuilder.failure(e));
			return result;
		}
	}

	@PostMapping(value = "booking/cancel.do")
	public DataMap cancel(@RequestBody LifePartEntity lifePartEntity) {
		
		try {
			return this.lifeService.bookingCancel(lifePartEntity);
			
		}catch(Exception e) {
			DataMap result = new DataMap(false);
			result.put(ResultConst.META, ResultBuilder.failure(e));
			return result;
		}
	}
}
