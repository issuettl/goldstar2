/**
 * 
 */
package kr.co.lge.goldstar.mvc.m.staff.service;

import kr.co.lge.goldstar.orm.jpa.entity.StaffCheck;
import kr.co.lge.goldstar.orm.jpa.entity.member.SignId;
import kr.co.rebel9.web.data.DataMap;

/**
 * @author NEOFLOW
 *
 */
public interface StaffService {

	/**
	 * @param signId
	 * @return
	 */
	DataMap getSign(SignId signId);

	/**
	 * @param signId
	 * @param staffCheck 
	 * @return
	 */
	DataMap mindStaffCheck(SignId signId, StaffCheck staffCheck);

	/**
	 * @param signId
	 * @param staffCheck 
	 * @return
	 */
	DataMap indivStaffCheck(SignId signId, StaffCheck staffCheck);

	/**
	 * @param signId
	 * @param staffCheck 
	 * @return
	 */
	DataMap styleStaffCheck(SignId signId, StaffCheck staffCheck);

	/**
	 * @param signId
	 * @param staffCheck
	 * @return
	 */
	DataMap lifeStaffCheck(SignId signId, StaffCheck staffCheck);

	/**
	 * @param signId
	 * @return
	 */
	DataMap jammyIssue(SignId signId);

}
