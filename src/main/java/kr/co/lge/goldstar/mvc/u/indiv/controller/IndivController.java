/**
 * 
 */
package kr.co.lge.goldstar.mvc.u.indiv.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.lge.goldstar.mvc.u.indiv.service.IndivService;
import kr.co.lge.goldstar.orm.jpa.entity.CornerType;
import kr.co.lge.goldstar.orm.jpa.entity.indiv.IndivEntity;

/**
 * @author issuettl
 *
 */
@Controller("u.IndivController")
@RequestMapping(value = "u/indiv")
public class IndivController {
	
	@Autowired
	private IndivService indivService;
	
	@RequestMapping(value = "question.do")
	public String indiv(ModelMap model) {
		
		List<IndivEntity> questions = this.indivService.getQuestions();
		model.put("questions", questions);
		model.put("corner", CornerType.indiv);
		
		return "u/indiv/question";
	}
}
