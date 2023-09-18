/**
 * 
 */
package kr.co.lge.goldstar.mvc.m.member.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.co.lge.goldstar.mvc.m.member.service.MemberService;
import kr.co.rebel9.web.data.DataMap;
import kr.co.rebel9.web.rest.result.ResultBuilder;
import kr.co.rebel9.web.rest.result.ResultConst;

/**
 * @author issuettl
 *
 */
@RestController("m.MemberRestController")
@RequestMapping(value = "m/member")
public class MemberRestController {

	@Autowired
	private MemberService memberService;
	
	/**
	 * @param param
	 * @param session
	 * @return 로그인 결과
	 */
	@PostMapping("staff/check.do")
	public DataMap signIn(@RequestParam MultiValueMap<String, Object> paramMap) {
        try {
            return this.memberService.staffCheck(new DataMap(paramMap));
        }catch(Exception e) {
			DataMap result = new DataMap(false);
			result.put(ResultConst.META, ResultBuilder.failure(e));
			return result;
		}
	}
	
	@PostMapping(value = "jammy/issue.do")
	public DataMap jammy(@RequestParam MultiValueMap<String, Object> paramMap) {
		try {
			return this.memberService.jammyIssue(new DataMap(paramMap));
		}catch(Exception e) {
			DataMap result = new DataMap(false);
			result.put(ResultConst.META, ResultBuilder.failure(e));
			return result;
		}
	}
}
