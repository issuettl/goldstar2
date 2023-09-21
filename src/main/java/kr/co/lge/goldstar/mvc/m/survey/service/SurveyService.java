/**
 * 
 */
package kr.co.lge.goldstar.mvc.m.survey.service;

import kr.co.lge.goldstar.orm.jpa.entity.survey.SurveyEntity;
import kr.co.rebel9.web.data.DataMap;

/**
 * @author issuettl
 *
 */
public interface SurveyService {

	/**
	 * @return
	 */
	DataMap getSurvey();

	/**
	 * @param surveyEntity
	 * @return
	 */
	DataMap save(SurveyEntity surveyEntity);

	/**
	 * @param surveyEntity
	 * @return
	 */
	DataMap update(SurveyEntity surveyEntity);

	/**
	 * @param surveyEntity
	 * @return
	 */
	DataMap remove(SurveyEntity surveyEntity);

	/**
	 * @param surveyEntity
	 * @return
	 */
	DataMap status(SurveyEntity surveyEntity);

	/**
	 * @param surveyEntity
	 * @return
	 */
	DataMap get(SurveyEntity surveyEntity);

	/**
	 * @param params
	 * @return
	 */
	DataMap sort(DataMap params);

}
