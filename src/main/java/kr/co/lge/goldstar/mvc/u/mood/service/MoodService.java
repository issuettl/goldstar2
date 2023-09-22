/**
 * 
 */
package kr.co.lge.goldstar.mvc.u.mood.service;

import java.util.List;

import kr.co.lge.goldstar.orm.jpa.entity.mood.MoodEntity;
import kr.co.lge.goldstar.orm.jpa.entity.mood.MoodPartEntity;
import kr.co.rebel9.web.data.DataMap;

/**
 * @author issuettl
 *
 */
public interface MoodService {

	/**
	 * @return
	 */
	List<MoodEntity> getQuestions();

	/**
	 * @param answers
	 * @return
	 */
	DataMap memberAnswers(List<Integer> answers);

	/**
	 * @return
	 */
	MoodPartEntity getMoodPart();

}
