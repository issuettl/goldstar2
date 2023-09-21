package kr.co.lge.goldstar.orm.jpa.repository.spring;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import kr.co.lge.goldstar.orm.jpa.entity.YesOrNo;
import kr.co.lge.goldstar.orm.jpa.entity.survey.SurveyEntity;

/**
 * @author cerakoted, base@rebel9.co.kr, cerakoted@gmail.com
 * @since 1.0
 * @date 2019. 11. 14
 *
 */
@Repository("SurveyRepository")
public interface SurveyRepository extends JpaRepository<SurveyEntity, Integer> {

	/**
	 * @param status
     * @param deleted
	 * @return
	 */
	List<SurveyEntity> findByStatusAndDeletedOrderByOrdinalAsc(YesOrNo status, YesOrNo deleted);
	
	/**
     * @param deleted
     * @return 
     */
    @Query("select coalesce(max(a.ordinal), 0) + 1 as max from survey a where a.deleted = ?1")
    Long maxOrdinal(YesOrNo deleted);

	/**
	 * @param snList
	 * @return
	 */
	List<SurveyEntity> findBySnIn(List<Integer> snList);

	/**
	 * @param n
	 * @return
	 */
	List<SurveyEntity> findByDeletedOrderByOrdinalAsc(YesOrNo n);


}
