package kr.co.lge.goldstar.orm.jpa.repository.spring;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.co.lge.goldstar.orm.jpa.entity.indiv.IndivMemberEntity;
import kr.co.lge.goldstar.orm.jpa.entity.indiv.IndivMemberId;

/**
 * @author cerakoted, base@rebel9.co.kr, cerakoted@gmail.com
 * @since 1.0
 * @date 2019. 11. 14
 *
 */
@Repository("IndivMemberRepository")
public interface IndivMemberRepository extends JpaRepository<IndivMemberEntity, IndivMemberId> {

	/**
	 * @param start
	 * @param end
	 * @param sn
	 * @return
	 */
	long countByCreatedGreaterThanEqualAndCreatedLessThanEqualAndAnswerSn(String start, String end, int sn);


}
