/**
 * 
 */
package kr.co.lge.goldstar.mvc.m.mood.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.lge.goldstar.mvc.m.mood.service.MoodService;
import kr.co.lge.goldstar.orm.jpa.entity.member.SignId;
import kr.co.rebel9.web.data.DataMap;
import kr.co.rebel9.web.rest.result.ResultBuilder;
import kr.co.rebel9.web.rest.result.ResultConst;

/**
 * @author issuettl
 *
 */
@RestController("m.MoodRestController")
@RequestMapping(value = "m/mood")
public class MoodRestController {
	
	@Autowired
	private MoodService moodService;

	@PostMapping(value = "answer/action.do")
	public DataMap answer(@RequestBody SignId signId) {
		
		try {
			return this.moodService.memberAnswers(signId);
			
		}catch(Exception e) {
			DataMap result = new DataMap(false);
			result.put(ResultConst.META, ResultBuilder.failure(e));
			return result;
		}
	}
}
