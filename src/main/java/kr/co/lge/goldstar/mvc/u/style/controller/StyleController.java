/**
 * 
 */
package kr.co.lge.goldstar.mvc.u.style.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.lge.goldstar.mvc.u.style.service.StyleService;
import kr.co.lge.goldstar.orm.jpa.entity.CornerType;
import kr.co.lge.goldstar.orm.jpa.entity.style.StyleEntity;

/**
 * @author issuettl
 *
 */
@Controller("u.StyleController")
@RequestMapping(value = "u/style")
public class StyleController {
	
	@Autowired
	private StyleService styleService;
	
	@RequestMapping(value = "question.do")
	public String style(ModelMap model) {
		
		List<StyleEntity> questions = this.styleService.getQuestions();
		model.put("questions", questions);
		model.put("corner", CornerType.style);
		
		return "u/style/question";
	}
}
