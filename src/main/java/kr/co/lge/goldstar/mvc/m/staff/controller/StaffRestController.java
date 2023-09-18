/**
 * 
 */
package kr.co.lge.goldstar.mvc.m.staff.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.lge.goldstar.mvc.m.staff.service.StaffService;
import kr.co.lge.goldstar.orm.jpa.entity.StaffCheck;
import kr.co.lge.goldstar.orm.jpa.entity.member.SignId;
import kr.co.rebel9.web.data.DataMap;
import kr.co.rebel9.web.rest.result.ResultBuilder;
import kr.co.rebel9.web.rest.result.ResultConst;

/**
 * @author issuettl
 *
 */
@CrossOrigin(origins = "*")
@RestController("m.StaffRestController")
@RequestMapping(value = "m/staff")
public class StaffRestController {
	
	@Autowired
	private StaffService staffService;

	@PostMapping(value = "sign.do")
	public DataMap sign(@RequestBody SignId signId) {
		
		try {
			return this.staffService.getSign(signId);
			
		}catch(Exception e) {
			DataMap result = new DataMap(false);
			result.put(ResultConst.META, ResultBuilder.failure(e));
			return result;
		}
	}

	@PostMapping(value = "mind/{staffCheck}.do")
	public DataMap mind(@RequestBody SignId signId, @PathVariable StaffCheck staffCheck) {
		
		try {
			return this.staffService.mindStaffCheck(signId, staffCheck);
			
		}catch(Exception e) {
			DataMap result = new DataMap(false);
			result.put(ResultConst.META, ResultBuilder.failure(e));
			return result;
		}
	}

	@PostMapping(value = "indiv/{staffCheck}.do")
	public DataMap indiv(@RequestBody SignId signId, @PathVariable StaffCheck staffCheck) {
		
		try {
			return this.staffService.indivStaffCheck(signId, staffCheck);
			
		}catch(Exception e) {
			DataMap result = new DataMap(false);
			result.put(ResultConst.META, ResultBuilder.failure(e));
			return result;
		}
	}

	@PostMapping(value = "style/{staffCheck}.do")
	public DataMap style(@RequestBody SignId signId, @PathVariable StaffCheck staffCheck) {
		
		try {
			return this.staffService.styleStaffCheck(signId, staffCheck);
			
		}catch(Exception e) {
			DataMap result = new DataMap(false);
			result.put(ResultConst.META, ResultBuilder.failure(e));
			return result;
		}
	}

	@PostMapping(value = "life/{staffCheck}.do")
	public DataMap life(@RequestBody SignId signId, @PathVariable StaffCheck staffCheck) {
		
		try {
			return this.staffService.lifeStaffCheck(signId, staffCheck);
			
		}catch(Exception e) {
			DataMap result = new DataMap(false);
			result.put(ResultConst.META, ResultBuilder.failure(e));
			return result;
		}
	}

	@PostMapping(value = "jammy/issue.do")
	public DataMap jammy(@RequestBody SignId signId) {
		
		try {
			return this.staffService.jammyIssue(signId);
			
		}catch(Exception e) {
			DataMap result = new DataMap(false);
			result.put(ResultConst.META, ResultBuilder.failure(e));
			return result;
		}
	}
}
