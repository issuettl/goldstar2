package kr.co.lge.goldstar.orm.jpa.repository.spring;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.co.lge.goldstar.orm.jpa.entity.pursue.PursueMemberEntity;
import kr.co.lge.goldstar.orm.jpa.entity.pursue.PursueMemberId;

/**
 * @author cerakoted, base@rebel9.co.kr, cerakoted@gmail.com
 * @since 1.0
 * @date 2019. 11. 14
 *
 */
@Repository("PursueMemberRepository")
public interface PursueMemberRepository extends JpaRepository<PursueMemberEntity, PursueMemberId> {

	/**
	 * @param start
	 * @param end
	 * @param sn
	 * @return
	 */
	long countByCreatedGreaterThanEqualAndCreatedLessThanEqualAndAnswerSn(String start, String end, int sn);


}
