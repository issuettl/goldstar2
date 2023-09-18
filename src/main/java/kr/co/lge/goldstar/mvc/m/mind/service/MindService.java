/**
 * 
 */
package kr.co.lge.goldstar.mvc.m.mind.service;

import kr.co.lge.goldstar.orm.jpa.entity.mind.MindPartEntity;

/**
 * @author issuettl
 *
 */
public interface MindService {

	/**
	 * @param create 
	 * @param memberSn 
	 * @return
	 */
	MindPartEntity getMindPart(int memberSn, String create);

	/**
	 * @param mindPart
	 */
	void save(MindPartEntity mindPart);

}
