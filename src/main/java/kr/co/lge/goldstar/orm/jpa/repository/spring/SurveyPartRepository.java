package kr.co.lge.goldstar.orm.jpa.repository.spring;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.co.lge.goldstar.orm.jpa.entity.survey.SurveyPartEntity;

/**
 * @author cerakoted, base@rebel9.co.kr, cerakoted@gmail.com
 * @since 1.0
 * @date 2019. 11. 14
 *
 */
@Repository("SurveyPartRepository")
public interface SurveyPartRepository extends JpaRepository<SurveyPartEntity, Integer> {

	/**
	 * @param memberSn
	 * @param created
	 * @return
	 */
	SurveyPartEntity findBySignMemberSnAndSignCreated(int memberSn, String created);

	/**
	 * @param sn
	 * @param present
	 * @return
	 */
	long countBySignMemberSn(int sn);

	/**
	 * @param today
	 * @param present
	 * @return
	 */
	long countBySignCreated(String today);

	/**
	 * @param memberSn
	 * @param created
	 * @return
	 */
	long countBySignMemberSnAndSignCreated(int memberSn, String created);

	/**
	 * @param startDate
	 * @param endDate
	 * @param present
	 * @return
	 */
	long countBySignCreatedGreaterThanEqualAndSignCreatedLessThanEqual(String startDate, String endDate);


}
