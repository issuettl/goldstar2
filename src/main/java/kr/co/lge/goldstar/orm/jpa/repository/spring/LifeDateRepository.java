package kr.co.lge.goldstar.orm.jpa.repository.spring;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.co.lge.goldstar.orm.jpa.entity.life.LifeDateEntity;

/**
 * @author cerakoted, base@rebel9.co.kr, cerakoted@gmail.com
 * @since 1.0
 * @date 2019. 11. 14
 *
 */
@Repository("LifeDateRepository")
public interface LifeDateRepository extends JpaRepository<LifeDateEntity, Integer> {

	/**
	 * @param date
	 * @return
	 */
	LifeDateEntity findByDate(String date);

	/**
	 * @return
	 */
	List<LifeDateEntity> findAllByOrderByDateAsc();

}
