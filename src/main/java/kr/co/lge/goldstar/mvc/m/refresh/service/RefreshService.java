/**
 * 
 */
package kr.co.lge.goldstar.mvc.m.refresh.service;

import kr.co.lge.goldstar.orm.jpa.entity.member.SignId;
import kr.co.lge.goldstar.orm.jpa.entity.refresh.RefreshPartEntity;
import kr.co.rebel9.web.data.DataMap;

/**
 * @author issuettl
 *
 */
public interface RefreshService {

	/**
	 * @param refreshPartEntity
	 * @return
	 */
	DataMap memberAnswers(SignId signId);

	/**
	 * @param memberSn
	 * @param created
	 * @return
	 */
	RefreshPartEntity getRefreshPart();

	/**
	 * @param memberSn
	 * @param created
	 * @return
	 */
	RefreshPartEntity getRefreshPart(int memberSn, String created);

}
