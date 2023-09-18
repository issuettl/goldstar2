/**
 * 
 */
package kr.co.lge.goldstar.mvc.u.mind.service;

import java.util.List;

import kr.co.lge.goldstar.orm.jpa.entity.mind.MindEntity;
import kr.co.lge.goldstar.orm.jpa.entity.mind.MindPartEntity;
import kr.co.rebel9.web.data.DataMap;

/**
 * @author issuettl
 *
 */
public interface MindService {

	/**
	 * @return
	 */
	List<MindEntity> getQuestions();

	/**
	 * @param answers
	 * @return
	 */
	DataMap memberAnswers(List<Integer> answers);

	/**
	 * @return
	 */
	MindPartEntity getMindPart();

}
