/**
 * 
 */
package kr.co.lge.goldstar.mvc.m.life.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.co.lge.goldstar.mvc.m.life.service.LifeService;
import kr.co.rebel9.web.data.DataMap;
import kr.co.rebel9.web.rest.result.ResultBuilder;
import kr.co.rebel9.web.rest.result.ResultConst;

/**
 * @author issuettl
 *
 */
@RestController("m.LifeRestController")
@RequestMapping(value = "m/life")
public class LifeRestController {

	@Autowired
	private LifeService lifeService;
	
	/**
	 * @param paramMap
	 * @return
	 */
	@PostMapping("admin/cancel.do")
	public DataMap adminCancel(@RequestParam MultiValueMap<String, Object> paramMap) {
        try {
            return this.lifeService.adminCancel(new DataMap(paramMap));
        }catch(Exception e) {
			DataMap result = new DataMap(false);
			result.put(ResultConst.META, ResultBuilder.failure(e));
			return result;
		}
	}
	
	/**
	 * @param paramMap
	 * @return
	 */
	@PostMapping("weekday/staff.do")
	public DataMap weekdayStaff(@RequestParam MultiValueMap<String, Object> paramMap) {
        try {
            return this.lifeService.weekdayStaff(new DataMap(paramMap));
        }catch(Exception e) {
			DataMap result = new DataMap(false);
			result.put(ResultConst.META, ResultBuilder.failure(e));
			return result;
		}
	}
	
	/**
	 * @param paramMap
	 * @return
	 */
	@PostMapping("weekday/date/add.do")
	public DataMap weekdayDateAdd(@RequestParam MultiValueMap<String, Object> paramMap) {
        try {
            return this.lifeService.weekdayDateAdd(new DataMap(paramMap));
        }catch(Exception e) {
			DataMap result = new DataMap(false);
			result.put(ResultConst.META, ResultBuilder.failure(e));
			return result;
		}
	}
	
	/**
	 * @param paramMap
	 * @return
	 */
	@PostMapping("weekday/date/remove.do")
	public DataMap weekdayDateRemove(@RequestParam MultiValueMap<String, Object> paramMap) {
        try {
            return this.lifeService.weekdayDateRemove(new DataMap(paramMap));
        }catch(Exception e) {
			DataMap result = new DataMap(false);
			result.put(ResultConst.META, ResultBuilder.failure(e));
			return result;
		}
	}
	
	/**
	 * @param paramMap
	 * @return
	 */
	@PostMapping("admin/confirm.do")
	public DataMap adminConfirm(@RequestParam MultiValueMap<String, Object> paramMap) {
        try {
            return this.lifeService.adminConfirm(new DataMap(paramMap));
        }catch(Exception e) {
			DataMap result = new DataMap(false);
			result.put(ResultConst.META, ResultBuilder.failure(e));
			return result;
		}
	}
	
	/**
	 * @param paramMap
	 * @return
	 */
	@PostMapping("admin/confirms.do")
	public DataMap adminConfirms(@RequestParam MultiValueMap<String, Object> paramMap) {
        try {
            return this.lifeService.adminConfirms(new DataMap(paramMap));
        }catch(Exception e) {
			DataMap result = new DataMap(false);
			result.put(ResultConst.META, ResultBuilder.failure(e));
			return result;
		}
	}
}
