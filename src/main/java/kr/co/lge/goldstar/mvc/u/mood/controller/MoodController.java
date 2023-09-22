/**
 * 
 */
package kr.co.lge.goldstar.mvc.u.mood.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.lge.goldstar.mvc.u.mood.service.MoodService;
import kr.co.lge.goldstar.orm.jpa.entity.CornerType;
import kr.co.lge.goldstar.orm.jpa.entity.mood.MoodEntity;

/**
 * @author issuettl
 *
 */
@Controller("u.MoodController")
@RequestMapping(value = "u/mood")
public class MoodController {
	
	@Autowired
	private MoodService moodService;
	
	@RequestMapping(value = "question.do")
	public String mood(ModelMap model) {
		
		List<MoodEntity> questions = this.moodService.getQuestions();
		model.put("questions", questions);
		model.put("corner", CornerType.mood);
		
		return "u/mood/question";
	}
}
