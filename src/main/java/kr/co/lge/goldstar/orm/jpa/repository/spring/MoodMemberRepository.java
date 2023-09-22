package kr.co.lge.goldstar.orm.jpa.repository.spring;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.co.lge.goldstar.orm.jpa.entity.mood.MoodMemberEntity;
import kr.co.lge.goldstar.orm.jpa.entity.mood.MoodMemberId;

/**
 * @author cerakoted, base@rebel9.co.kr, cerakoted@gmail.com
 * @since 1.0
 * @date 2019. 11. 14
 *
 */
@Repository("MoodMemberRepository")
public interface MoodMemberRepository extends JpaRepository<MoodMemberEntity, MoodMemberId> {

	/**
	 * @param start
	 * @param end
	 * @param sn
	 * @return
	 */
	long countByCreatedGreaterThanEqualAndCreatedLessThanEqualAndAnswerSn(String start, String end, int sn);


}
