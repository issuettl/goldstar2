/**
 * 
 */
package kr.co.lge.goldstar.orm.jpa.repository.dsl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import kr.co.lge.goldstar.orm.jpa.entity.life.LifePartEntity;
import kr.co.rebel9.web.data.DataMap;

/**
 * @author cerakoted, base@rebel9.co.kr, cerakoted@gmail.com
 * @since 1.0
 * @date 2019. 11. 14
 *
 */
public interface LifePartDslRepository {

    /**
     * @param params
     * @param pageable
     * @return 프로그램 페이징
     */
    Page<LifePartEntity> findPageByWeekday(DataMap params, Pageable pageable);

    /**
     * @param params
     * @param pageable
     * @return 프로그램 페이징
     */
    Page<LifePartEntity> findPageByWeekend(DataMap params, Pageable pageable);

	/**
	 * @param sn
	 * @param name
	 * @param phone
	 * @return
	 */
	List<LifePartEntity> findAllByMemberSnOrderByDateDescTimeDesc(int sn, String name, String phone);
}
