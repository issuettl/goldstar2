/**
 * 
 */
package kr.co.lge.goldstar.mvc.m.mood.service;

import kr.co.lge.goldstar.orm.jpa.entity.member.SignId;
import kr.co.lge.goldstar.orm.jpa.entity.mood.MoodPartEntity;
import kr.co.rebel9.web.data.DataMap;

/**
 * @author issuettl
 *
 */
public interface MoodService {

	/**
	 * @param signId
	 * @return
	 */
	DataMap memberAnswers(SignId signId);

	/**
	 * @param memberSn
	 * @param created
	 * @return
	 */
	MoodPartEntity getMoodPart(int memberSn, String created);

}
