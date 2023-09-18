package kr.co.lge.goldstar.orm.jpa.repository.spring;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.co.lge.goldstar.orm.jpa.entity.mind.MindMemberEntity;
import kr.co.lge.goldstar.orm.jpa.entity.mind.MindMemberId;

/**
 * @author cerakoted, base@rebel9.co.kr, cerakoted@gmail.com
 * @since 1.0
 * @date 2019. 11. 14
 *
 */
@Repository("MindMemberRepository")
public interface MindMemberRepository extends JpaRepository<MindMemberEntity, MindMemberId> {

	/**
	 * @param startDate
	 * @param endDate
	 * @param sn
	 * @return
	 */
	long countByCreatedGreaterThanEqualAndCreatedLessThanEqualAndAnswerSn(String startDate, String endDate, int sn);

}
