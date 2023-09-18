package kr.co.lge.goldstar.orm.jpa.repository.spring;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.co.lge.goldstar.orm.jpa.entity.member.JammyEntity;

/**
 * @author cerakoted, base@rebel9.co.kr, cerakoted@gmail.com
 * @since 1.0
 * @date 2019. 11. 14
 *
 */
@Repository("JammyRepository")
public interface JammyRepository extends JpaRepository<JammyEntity, Integer> {

	/**
	 * @return
	 */
	JammyEntity findFirstByIssuedNullOrderBySnAsc();
}
