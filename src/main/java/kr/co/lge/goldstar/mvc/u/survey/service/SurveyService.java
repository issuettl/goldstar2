/**
 * 
 */
package kr.co.lge.goldstar.mvc.u.survey.service;

import java.util.List;

import kr.co.lge.goldstar.orm.jpa.entity.survey.SurveyEntity;
import kr.co.lge.goldstar.orm.jpa.entity.survey.SurveyPartEntity;
import kr.co.rebel9.web.data.DataMap;

/**
 * @author issuettl
 *
 */
public interface SurveyService {

	/**
	 * @return
	 */
	List<SurveyEntity> getQuestions();

	/**
	 * @param answers
	 * @return
	 */
	DataMap memberAnswers(List<Integer> answers);

	/**
	 * @return
	 */
	SurveyPartEntity getSurveyPart();

}
