/**
 * 
 */
package kr.co.lge.goldstar.orm.jpa.repository.dsl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import kr.co.lge.goldstar.orm.jpa.entity.event.EventEntity;
import kr.co.rebel9.web.data.DataMap;

/**
 * @author cerakoted, base@rebel9.co.kr, cerakoted@gmail.com
 * @since 1.0
 * @date 2019. 11. 14
 *
 */
public interface EventDslRepository {

    /**
     * @param bbsSn 
     * @param params
     * @param pageable
     * @return 프로그램 페이징
     */
    Page<EventEntity> findPageBySearch(DataMap params, Pageable pageable);

    /**
     * @param bbsSn 
     * @param params
     * @param pageable
     * @return 프로그램 페이징
     */
    Page<EventEntity> findPageByHome(DataMap params, Pageable pageable);
}
