/**
 * 
 */
package kr.co.lge.goldstar.mvc.m.sign.service;

import kr.co.lge.goldstar.orm.jpa.entity.member.SignEntity;
import kr.co.rebel9.web.data.DataMap;

/**
 * @author issuettl
 *
 */
public interface SignService {

	/**
	 * @param memberSn
	 * @param created
	 * @return
	 */
	DataMap getSign(int memberSn, String created);

	/**
	 * @param count
	 * @return
	 */
	DataMap getSigns(int count);

	/**
	 * @param sn
	 * @param created
	 * @return
	 */
	SignEntity getSignEntity(int sn, String created);

}
