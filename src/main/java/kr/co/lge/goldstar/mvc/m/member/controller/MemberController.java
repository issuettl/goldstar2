/**
 * 
 */
package kr.co.lge.goldstar.mvc.m.member.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import kr.co.lge.goldstar.core.pagination.Paging;
import kr.co.lge.goldstar.mvc.m.manager.service.ManagerService;
import kr.co.lge.goldstar.mvc.m.member.service.MemberService;
import kr.co.lge.goldstar.orm.jpa.entity.member.MemberEntity;
import kr.co.rebel9.web.data.DataMap;

/**
 * @author issuettl
 *
 */
@Controller("m.MemberController")
@RequestMapping(value = "m/member")
public class MemberController {
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private ManagerService managerService;
	
	@RequestMapping(value = "list.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String list(@RequestParam MultiValueMap<String, Object> paramMap, ModelMap model) {
		
		DataMap params = new DataMap(paramMap);
        params.put("size", params.getAsInt("size", 10));
        
        Page<MemberEntity> page = this.memberService.getPage(params);
        
        model.put("page", page);
        model.put("paging", new Paging(page));
        
        return "m/member/list";
	}

	@PostMapping(value = "view.do")
	public String view(@RequestParam MultiValueMap<String, Object> paramMap, ModelMap model) {
		
		DataMap params = new DataMap(paramMap);
		
		boolean isChecked = this.managerService.checkPassword(params.getAsString("password"));
		if(!isChecked) {
	        return "m/member/invalid_password";
		}
        
		model.putAll(this.memberService.getMember(params));
        
        return "m/member/view";
	}

	@GetMapping(value = "encode.do")
	public String all(ModelMap model) {
		
		model.put("members", this.memberService.encode());
        
        return "m/member/encode";
	}
}
