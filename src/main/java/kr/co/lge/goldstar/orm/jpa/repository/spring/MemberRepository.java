package kr.co.lge.goldstar.orm.jpa.repository.spring;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.co.lge.goldstar.orm.jpa.entity.member.MemberEntity;

/**
 * @author cerakoted, base@rebel9.co.kr, cerakoted@gmail.com
 * @since 1.0
 * @date 2019. 11. 14
 *
 */
@Repository("MemberRepository")
public interface MemberRepository extends JpaRepository<MemberEntity, Integer> {
	
	/**
	 * @param uid
	 * @return
	 */
	MemberEntity findByUid(String uid);

	/**
	 * @param target
	 * @return
	 */
	List<MemberEntity> findAllByUpdatedLessThanEqualAndUpdatedPersonalIsNull(String target);


}
