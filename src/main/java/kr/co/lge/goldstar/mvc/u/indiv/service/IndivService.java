/**
 * 
 */
package kr.co.lge.goldstar.mvc.u.indiv.service;

import java.util.List;

import kr.co.lge.goldstar.orm.jpa.entity.indiv.IndivEntity;
import kr.co.lge.goldstar.orm.jpa.entity.indiv.IndivPartEntity;
import kr.co.rebel9.web.data.DataMap;

/**
 * @author issuettl
 *
 */
public interface IndivService {

	/**
	 * @return
	 */
	List<IndivEntity> getQuestions();

	/**
	 * @param answers
	 * @return
	 */
	DataMap memberAnswers(List<Integer> answers);

	/**
	 * @return
	 */
	IndivPartEntity getIndivPart();

}
