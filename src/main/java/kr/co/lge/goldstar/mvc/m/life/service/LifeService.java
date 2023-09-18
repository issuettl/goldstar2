/**
 * 
 */
package kr.co.lge.goldstar.mvc.m.life.service;

import java.util.List;

import org.springframework.data.domain.Page;

import kr.co.lge.goldstar.orm.jpa.entity.life.LifeDateEntity;
import kr.co.lge.goldstar.orm.jpa.entity.life.LifePartEntity;
import kr.co.rebel9.web.data.DataMap;

/**
 * @author issuettl
 *
 */
public interface LifeService {

	/**
	 * @param memberSn
	 * @param created
	 * @return
	 */
	List<LifePartEntity> getLifePart(int memberSn, String created);

	/**
	 * @param params
	 * @return
	 */
	Page<LifePartEntity> getPageWeekday(DataMap params);

	/**
	 * @param params
	 * @return
	 */
	Page<LifePartEntity> getPageWeekend(DataMap params);

	/**
	 * @param dataMap
	 * @return
	 */
	DataMap adminCancel(DataMap dataMap);

	/**
	 * @param dataMap
	 * @return
	 */
	DataMap weekdayStaff(DataMap dataMap);

	/**
	 * @param dataMap
	 * @return
	 */
	DataMap adminConfirm(DataMap dataMap);

	/**
	 * @param dataMap
	 * @return
	 */
	DataMap adminConfirms(DataMap dataMap);

	/**
	 * @param dataMap
	 * @return
	 */
	DataMap weekdayDateAdd(DataMap dataMap);

	/**
	 * @param dataMap
	 * @return
	 */
	DataMap weekdayDateRemove(DataMap dataMap);

	/**
	 * @return
	 */
	List<LifeDateEntity> getNotDates();

}
