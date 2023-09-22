/**
 * 
 */
package kr.co.lge.goldstar.mvc.u.sign.service;

import kr.co.lge.goldstar.orm.jpa.entity.member.MemberEntity;
import kr.co.lge.goldstar.orm.jpa.entity.member.SignEntity;
import kr.co.rebel9.web.data.DataMap;

/**
 * @author issuettl
 *
 */
public interface SignService {

	/**
	 * @return
	 */
	boolean existSignIn();

	/**
	 * @return
	 */
	boolean existMemberIn();

	/**
	 * @return
	 */
	boolean checkSignIn();
	
	/**
	 * @param signEntity
	 * 
	 */
	void signInSession(SignEntity signEntity);
	
	/**
	 * @param member
	 * 
	 */
	void signInMember(MemberEntity member);

	/**
	 * @param signEntity
	 * @return
	 */
	DataMap signIn(SignEntity signEntity);

	/**
	 * @return
	 */
	MemberEntity getMemberIn();

	/**
	 * @return
	 */
	SignEntity getSignIn();

	/**
	 * 
	 */
	void signOut();

	/**
	 * 
	 */
	void memberOut();

	/**
	 * 
	 */
	void signRemove();

	/**
	 * @param signEntity
	 * @return
	 */
	DataMap saveWorry(SignEntity signEntity);

	/**
	 * @param signEntity
	 * @return
	 */
	DataMap savePursue(SignEntity signEntity);

	/**
	 * @return
	 */
	SignEntity getSignEntity();

	/**
	 * @param memberSn
	 * @return
	 */
	MemberEntity getMemberEntity(int memberSn);

	/**
	 * @param account
	 * @param data
	 */
	void signInMember(DataMap account, DataMap data);
}
