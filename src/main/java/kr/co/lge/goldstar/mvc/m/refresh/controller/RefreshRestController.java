/**
 * 
 */
package kr.co.lge.goldstar.mvc.m.refresh.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.lge.goldstar.mvc.m.refresh.service.RefreshService;
import kr.co.lge.goldstar.orm.jpa.entity.member.SignId;
import kr.co.rebel9.web.data.DataMap;
import kr.co.rebel9.web.rest.result.ResultBuilder;
import kr.co.rebel9.web.rest.result.ResultConst;

/**
 * @author issuettl
 *
 */
@RestController("m.RefreshRestController")
@RequestMapping(value = "m/refresh")
public class RefreshRestController {
	
	@Autowired
	private RefreshService refreshService;

	@PostMapping(value = "answer/action.do")
	public DataMap answer(@RequestBody SignId signId) {
		
		try {
			return this.refreshService.memberAnswers(signId);
			
		}catch(Exception e) {
			DataMap result = new DataMap(false);
			result.put(ResultConst.META, ResultBuilder.failure(e));
			return result;
		}
	}
}
