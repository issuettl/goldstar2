/**
 * 
 */
package kr.co.lge.goldstar.mvc.test.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.rebel9.web.data.DataMap;

/**
 * @author issuettl, data@rebel9.co.kr
 * @since 1.0
 * @date 2018. 4. 11.
 *
 */
@RestController
@RequestMapping(value = "abc")
public class TestRestController {
	
	/**
	 * @return 카테고리 별 갯수
	 */
	@RequestMapping(value = "test.do")
	public DataMap signIn() {
		
		System.out.println("abc");
		return new DataMap();
	}
}
