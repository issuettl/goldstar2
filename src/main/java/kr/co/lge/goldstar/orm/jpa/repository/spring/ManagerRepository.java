package kr.co.lge.goldstar.orm.jpa.repository.spring;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.co.lge.goldstar.orm.jpa.entity.member.ManagerEntity;

/**
 * @author cerakoted, base@rebel9.co.kr, cerakoted@gmail.com
 * @since 1.0
 * @date 2019. 11. 14
 *
 */
@Repository("ManagerRepository")
public interface ManagerRepository extends JpaRepository<ManagerEntity, Integer> {

	/**
	 * @param uid
	 */
	ManagerEntity findByUid(String uid);

	/**
	 * @param target
	 * @return
	 */
	List<ManagerEntity> findAllByConnectedLessThanEqual(String target);


}
