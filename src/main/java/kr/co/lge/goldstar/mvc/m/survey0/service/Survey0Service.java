/**
 * 
 */
package kr.co.lge.goldstar.mvc.m.survey0.service;

import kr.co.lge.goldstar.orm.jpa.entity.indiv.IndivEntity;
import kr.co.lge.goldstar.orm.jpa.entity.mind.MindEntity;
import kr.co.lge.goldstar.orm.jpa.entity.mood.MoodEntity;
import kr.co.lge.goldstar.orm.jpa.entity.style.StyleEntity;
import kr.co.rebel9.web.data.DataMap;

/**
 * @author issuettl
 *
 */
public interface Survey0Service {

	/**
	 * @return
	 */
	DataMap getSurvey();

	/**
	 * @param params 
	 * @return
	 */
	DataMap getAnswer(DataMap params);

	/**
	 * @param mindEntity
	 * @return
	 */
	DataMap save(MindEntity mindEntity);

	/**
	 * @param indivEntity
	 * @return
	 */
	DataMap save(IndivEntity indivEntity);

	/**
	 * @param styleEntity
	 * @return
	 */
	DataMap save(StyleEntity styleEntity);

	/**
	 * @param moodEntity
	 * @return
	 */
	DataMap save(MoodEntity moodEntity);

	/**
	 * @param mindEntity
	 * @return
	 */
	DataMap update(MindEntity mindEntity);

	/**
	 * @param indivEntity
	 * @return
	 */
	DataMap update(IndivEntity indivEntity);

	/**
	 * @param styleEntity
	 * @return
	 */
	DataMap update(StyleEntity styleEntity);

	/**
	 * @param moodEntity
	 * @return
	 */
	DataMap update(MoodEntity moodEntity);

	/**
	 * @param mindEntity
	 * @return
	 */
	DataMap remove(MindEntity mindEntity);

	/**
	 * @param indivEntity
	 * @return
	 */
	DataMap remove(IndivEntity indivEntity);

	/**
	 * @param styleEntity
	 * @return
	 */
	DataMap remove(StyleEntity styleEntity);

	/**
	 * @param moodEntity
	 * @return
	 */
	DataMap remove(MoodEntity moodEntity);

	/**
	 * @param mindEntity
	 * @return
	 */
	DataMap status(MindEntity mindEntity);

	/**
	 * @param indivEntity
	 * @return
	 */
	DataMap status(IndivEntity indivEntity);

	/**
	 * @param styleEntity
	 * @return
	 */
	DataMap status(StyleEntity styleEntity);

	/**
	 * @param moodEntity
	 * @return
	 */
	DataMap status(MoodEntity moodEntity);

	/**
	 * @param mindEntity
	 * @return
	 */
	DataMap get(MindEntity mindEntity);

	/**
	 * @param indivEntity
	 * @return
	 */
	DataMap get(IndivEntity indivEntity);

	/**
	 * @param styleEntity
	 * @return
	 */
	DataMap get(StyleEntity styleEntity);

	/**
	 * @param moodEntity
	 * @return
	 */
	DataMap get(MoodEntity moodEntity);

	/**
	 * @param params
	 * @return
	 */
	DataMap mindSort(DataMap params);

	/**
	 * @param params
	 * @return
	 */
	DataMap indivSort(DataMap params);

	/**
	 * @param params
	 * @return
	 */
	DataMap styleSort(DataMap params);

	/**
	 * @param params
	 * @return
	 */
	DataMap moodSort(DataMap params);

}
