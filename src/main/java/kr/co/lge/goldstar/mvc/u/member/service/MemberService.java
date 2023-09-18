/**
 * 
 */
package kr.co.lge.goldstar.mvc.u.member.service;

import kr.co.lge.goldstar.orm.jpa.entity.member.MemberEntity;
import kr.co.rebel9.web.data.DataMap;

/**
 * @author issuettl
 *
 */
public interface MemberService {

	/**
	 * @param memberEntity
	 * @return
	 */
	DataMap saveGender(MemberEntity memberEntity);

	/**
	 * @param memberEntity
	 * @return
	 */
	DataMap saveAge(MemberEntity memberEntity);

	/**
	 * @param params
	 */
	void personal(DataMap params);

}
