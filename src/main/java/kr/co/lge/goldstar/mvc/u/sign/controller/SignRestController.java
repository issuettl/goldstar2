/**
 * 
 */
package kr.co.lge.goldstar.mvc.u.sign.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.lge.goldstar.mvc.u.sign.service.SignService;
import kr.co.lge.goldstar.orm.jpa.entity.member.SignEntity;
import kr.co.rebel9.web.data.DataMap;
import kr.co.rebel9.web.rest.result.ResultBuilder;
import kr.co.rebel9.web.rest.result.ResultConst;

/**
 * @author issuettl
 *
 */
@RestController("u.SignRestController")
@RequestMapping(value = "u/sign")
public class SignRestController {
	
	@Autowired
	private SignService signService;

	@PostMapping(value = "name/action.do")
	public DataMap name(@RequestBody SignEntity signEntity) {
		
		try {
			return this.signService.signIn(signEntity);
			
		}catch(Exception e) {
			DataMap result = new DataMap(false);
			result.put(ResultConst.META, ResultBuilder.failure(e));
			return result;
		}
	}

	@PostMapping(value = "worry/action.do")
	public DataMap worry(@RequestBody SignEntity signEntity) {
		
		try {
			return this.signService.saveWorry(signEntity);
			
		}catch(Exception e) {
			DataMap result = new DataMap(false);
			result.put(ResultConst.META, ResultBuilder.failure(e));
			return result;
		}
	}
}
