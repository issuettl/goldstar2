package kr.co.lge.goldstar.orm.jpa.repository.spring;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.co.lge.goldstar.orm.jpa.entity.style.StyleMemberEntity;
import kr.co.lge.goldstar.orm.jpa.entity.style.StyleMemberId;

/**
 * @author cerakoted, base@rebel9.co.kr, cerakoted@gmail.com
 * @since 1.0
 * @date 2019. 11. 14
 *
 */
@Repository("StyleMemberRepository")
public interface StyleMemberRepository extends JpaRepository<StyleMemberEntity, StyleMemberId> {

	/**
	 * @param start
	 * @param end
	 * @param sn
	 * @return
	 */
	long countByCreatedGreaterThanEqualAndCreatedLessThanEqualAndAnswerSn(String start, String end, int sn);


}
