/**
 * 
 */
package kr.co.lge.goldstar.mvc.u.life.service;

import java.util.List;

import kr.co.lge.goldstar.orm.jpa.entity.life.LifePartEntity;
import kr.co.rebel9.web.data.DataMap;

/**
 * @author issuettl
 *
 */
public interface LifeService {

	/**
	 * @param lifePartEntity
	 * @return
	 */
	DataMap bookingAction(LifePartEntity lifePartEntity);

	/**
	 * @param lifePartEntity
	 * @return
	 */
	DataMap bookingTimes(LifePartEntity lifePartEntity);

	/**
	 * @return
	 */
	LifePartEntity getLifePart();

	/**
	 * @return
	 */
	List<String> getWeekdayDates();

	/**
	 * @return
	 */
	List<String> getWeekendDates();

	/**
	 * @return
	 */
	List<LifePartEntity> myList();

	/**
	 * @param lifePartEntity
	 * @return
	 */
	DataMap bookingCancel(LifePartEntity lifePartEntity);

}
