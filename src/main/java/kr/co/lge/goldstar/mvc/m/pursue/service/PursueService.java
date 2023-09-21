/**
 * 
 */
package kr.co.lge.goldstar.mvc.m.pursue.service;

import kr.co.lge.goldstar.orm.jpa.entity.pursue.PursueEntity;
import kr.co.rebel9.web.data.DataMap;

/**
 * @author issuettl
 *
 */
public interface PursueService {

	/**
	 * @return
	 */
	DataMap getPursue();

	/**
	 * @param pursueEntity
	 * @return
	 */
	DataMap update(PursueEntity pursueEntity);

	/**
	 * @param pursueEntity
	 * @return
	 */
	DataMap status(PursueEntity pursueEntity);

	/**
	 * @param pursueEntity
	 * @return
	 */
	DataMap get(PursueEntity pursueEntity);

	/**
	 * @param params
	 * @return
	 */
	DataMap sort(DataMap params);

}
