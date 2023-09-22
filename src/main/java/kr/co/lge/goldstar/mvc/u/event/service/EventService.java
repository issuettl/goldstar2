/**
 * 
 */
package kr.co.lge.goldstar.mvc.u.event.service;

import java.util.List;

import kr.co.lge.goldstar.orm.jpa.entity.event.EventEntity;
import kr.co.rebel9.web.data.DataMap;

/**
 * @author issuettl
 *
 */
public interface EventService {

	/**
	 * @return
	 */
	List<EventEntity> getList();

	/**
	 * @return
	 */
	List<EventEntity> getIndex();

	/**
	 * @param sn
	 * @return
	 */
	EventEntity getEventEntity(int sn);

	/**
	 * @param sn
	 * @return
	 */
	DataMap getThumbFile(int sn);

	/**
	 * @param sn
	 * @return
	 */
	DataMap getPcViewFile(int sn);

	/**
	 * @param sn
	 * @return
	 */
	DataMap getMoViewFile(int sn);

}
