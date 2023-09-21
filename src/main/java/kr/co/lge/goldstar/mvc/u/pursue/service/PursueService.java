/**
 * 
 */
package kr.co.lge.goldstar.mvc.u.pursue.service;

import java.util.List;

import kr.co.lge.goldstar.orm.jpa.entity.pursue.PursueEntity;
import kr.co.lge.goldstar.orm.jpa.entity.pursue.PursuePartEntity;
import kr.co.rebel9.web.data.DataMap;

/**
 * @author issuettl
 *
 */
public interface PursueService {

	/**
	 * @return
	 */
	List<PursueEntity> getQuestions();

	/**
	 * @param answers
	 * @return
	 */
	DataMap memberAnswers(List<Integer> answers);

	/**
	 * @return
	 */
	PursuePartEntity getPursuePart();

}
