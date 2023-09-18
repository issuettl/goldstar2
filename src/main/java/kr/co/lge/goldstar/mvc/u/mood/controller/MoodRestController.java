/**
 * 
 */
package kr.co.lge.goldstar.mvc.u.mood.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.lge.goldstar.mvc.u.mood.service.MoodService;
import kr.co.rebel9.web.data.DataMap;
import kr.co.rebel9.web.rest.result.ResultBuilder;
import kr.co.rebel9.web.rest.result.ResultConst;

/**
 * @author issuettl
 *
 */
@RestController("u.MoodRestController")
@RequestMapping(value = "u/mood")
public class MoodRestController {
	
	@Autowired
	private MoodService moodService;

	@PostMapping(value = "answer/action.do")
	public DataMap answer(@RequestBody List<Integer> answers) {
		
		try {
			return this.moodService.memberAnswers(answers);
			
		}catch(Exception e) {
			DataMap result = new DataMap(false);
			result.put("reason", e.getMessage());
			result.put(ResultConst.META, ResultBuilder.failure(e));
			return result;
		}
	}
}
