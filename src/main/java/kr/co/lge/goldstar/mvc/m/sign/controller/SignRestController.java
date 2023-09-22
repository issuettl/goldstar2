/**
 * 
 */
package kr.co.lge.goldstar.mvc.m.sign.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.lge.goldstar.mvc.m.sign.service.SignService;
import kr.co.rebel9.web.data.DataMap;
import kr.co.rebel9.web.rest.result.ResultBuilder;
import kr.co.rebel9.web.rest.result.ResultConst;

/**
 * @author issuettl
 *
 */
@CrossOrigin(origins = "*")
@RestController("m.SignRestController")
@RequestMapping(value = "m/sign")
public class SignRestController {
	
	@Autowired
	private SignService signService;

	@PostMapping(value = "qr/{memberSn}/{created}.do")
	public DataMap qr(@PathVariable int memberSn, @PathVariable String created) {
		
		try {
			return this.signService.getSign(memberSn, created);
			
		}catch(Exception e) {
			DataMap result = new DataMap(false);
			result.put(ResultConst.META, ResultBuilder.failure(e));
			return result;
		}
	}

	@PostMapping(value = "wall/{count}.do")
	public DataMap wall(@PathVariable int count) {
		
		try {
			return this.signService.getSigns(count);
			
		}catch(Exception e) {
			DataMap result = new DataMap(false);
			result.put(ResultConst.META, ResultBuilder.failure(e));
			return result;
		}
	}
}
