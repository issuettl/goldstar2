/**
 * 
 */
package kr.co.lge.goldstar.mvc.u.indiv.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.lge.goldstar.mvc.u.indiv.service.IndivService;
import kr.co.rebel9.web.data.DataMap;
import kr.co.rebel9.web.rest.result.ResultBuilder;
import kr.co.rebel9.web.rest.result.ResultConst;

/**
 * @author issuettl
 *
 */
@RestController("u.IndivRestController")
@RequestMapping(value = "u/indiv")
public class IndivRestController {
	
	@Autowired
	private IndivService indivService;

	@PostMapping(value = "answer/action.do")
	public DataMap answer(@RequestBody List<Integer> answers) {
		
		try {
			return this.indivService.memberAnswers(answers);
			
		}catch(Exception e) {
			DataMap result = new DataMap(false);
			result.put("reason", e.getMessage());
			result.put(ResultConst.META, ResultBuilder.failure(e));
			return result;
		}
	}
}
