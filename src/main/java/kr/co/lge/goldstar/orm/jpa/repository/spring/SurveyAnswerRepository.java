package kr.co.lge.goldstar.orm.jpa.repository.spring;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import kr.co.lge.goldstar.orm.jpa.entity.YesOrNo;
import kr.co.lge.goldstar.orm.jpa.entity.survey.SurveyAnswerEntity;

/**
 * @author cerakoted, base@rebel9.co.kr, cerakoted@gmail.com
 * @since 1.0
 * @date 2019. 11. 14
 *
 */
@Repository("SurveyAnswerRepository")
public interface SurveyAnswerRepository extends JpaRepository<SurveyAnswerEntity, Integer> {

	/**
	 * @param surveySn
	 * @param deleted
	 * @return
	 */
    @Query("select coalesce(max(a.ordinal), 0) + 1 as max from surveyAnswer a where a.surveySn = ?1 and a.deleted = ?2")
	Long maxOrdinal(int surveySn, YesOrNo deleted);

	/**
	 * @param sn
	 * @param deleted
	 * @return
	 */
	SurveyAnswerEntity findBySnAndDeleted(int sn, YesOrNo deleted);

	/**
	 * @param snList
	 * @return
	 */
	List<SurveyAnswerEntity> findBySnIn(List<Integer> snList);


}
