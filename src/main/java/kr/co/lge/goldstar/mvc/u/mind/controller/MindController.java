/**
 * 
 */
package kr.co.lge.goldstar.mvc.u.mind.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.lge.goldstar.mvc.u.mind.service.MindService;
import kr.co.lge.goldstar.orm.jpa.entity.CornerType;
import kr.co.lge.goldstar.orm.jpa.entity.mind.MindEntity;

/**
 * @author issuettl
 *
 */
@Controller("u.MindController")
@RequestMapping(value = "u/mind")
public class MindController {
	
	@Autowired
	private MindService mindService;
	
	@RequestMapping(value = "question.do")
	public String mind(ModelMap model) {
		
		List<MindEntity> questions = this.mindService.getQuestions();
		model.put("questions", questions);
		model.put("corner", CornerType.mind);
		
		return "u/mind/question";
	}
}
