/**
 * 
 */
package kr.co.lge.goldstar.orm.jpa.repository.dsl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import kr.co.lge.goldstar.orm.jpa.entity.member.MemberEntity;
import kr.co.rebel9.web.data.DataMap;

/**
 * @author cerakoted, base@rebel9.co.kr, cerakoted@gmail.com
 * @since 1.0
 * @date 2019. 11. 14
 *
 */
public interface MemberDslRepository {

    /**
     * @param bbsSn 
     * @param params
     * @param pageable
     * @return 프로그램 페이징
     */
    Page<MemberEntity> findPageBySearch(DataMap params, Pageable pageable);
}
