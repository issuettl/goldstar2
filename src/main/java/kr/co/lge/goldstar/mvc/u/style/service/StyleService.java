/**
 * 
 */
package kr.co.lge.goldstar.mvc.u.style.service;

import java.util.List;

import kr.co.lge.goldstar.orm.jpa.entity.style.StyleEntity;
import kr.co.lge.goldstar.orm.jpa.entity.style.StylePartEntity;
import kr.co.rebel9.web.data.DataMap;

/**
 * @author issuettl
 *
 */
public interface StyleService {

	/**
	 * @return
	 */
	List<StyleEntity> getQuestions();

	/**
	 * @param answers
	 * @return
	 */
	DataMap memberAnswers(List<Integer> answers);

	/**
	 * @return
	 */
	StylePartEntity getStylePart();

}
