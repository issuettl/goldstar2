/**
 * 
 */
package kr.co.lge.goldstar.mvc.m.system.service;

import kr.co.lge.goldstar.orm.jpa.entity.system.SystemEntity;
import kr.co.rebel9.web.data.DataMap;

/**
 * @author issuettl
 *
 */
public interface SystemService {

	/**
	 * @param sn
	 * @return
	 */
	SystemEntity getEntity(int sn);

	/**
	 * @param systemEntity
	 * @return
	 */
	DataMap survey(SystemEntity systemEntity);

	/**
	 * @param systemEntity
	 * @return
	 */
	DataMap product(SystemEntity systemEntity);

}
