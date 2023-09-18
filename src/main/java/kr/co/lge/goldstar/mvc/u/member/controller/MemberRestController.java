/**
 * 
 */
package kr.co.lge.goldstar.mvc.u.member.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.lge.goldstar.mvc.u.member.service.MemberService;
import kr.co.lge.goldstar.orm.jpa.entity.member.MemberEntity;
import kr.co.rebel9.web.data.DataMap;
import kr.co.rebel9.web.rest.result.ResultBuilder;
import kr.co.rebel9.web.rest.result.ResultConst;

/**
 * @author issuettl
 *
 */
@RestController("u.MemberRestController")
@RequestMapping(value = "u/member")
public class MemberRestController {
	
	@Autowired
	private MemberService memberService;
	
	@PostMapping(value = "gender/action.do")
	public DataMap gender(@RequestBody MemberEntity memberEntity) {
		
		try {
			return this.memberService.saveGender(memberEntity);
			
		}catch(Exception e) {
			DataMap result = new DataMap(false);
			result.put(ResultConst.META, ResultBuilder.failure(e));
			return result;
		}
	}

	@PostMapping(value = "age/action.do")
	public DataMap age(@RequestBody MemberEntity memberEntity) {
		
		try {
			return this.memberService.saveAge(memberEntity);
			
		}catch(Exception e) {
			DataMap result = new DataMap(false);
			result.put(ResultConst.META, ResultBuilder.failure(e));
			return result;
		}
	}

	@PostMapping(value = "personal.do")
	public DataMap memberScheduler(@RequestBody DataMap dataMap) {
		
		try {
			this.memberService.personal(dataMap);
			return new DataMap(true);
			
		}catch(Exception e) {
			DataMap result = new DataMap(false);
			result.put(ResultConst.META, ResultBuilder.failure(e));
			return result;
		}
	}
}
