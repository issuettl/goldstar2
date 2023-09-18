/**
 * 
 */
package kr.co.lge.goldstar.orm.jpa.repository.dsl.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Repository;

import com.querydsl.jpa.JPQLQuery;

import kr.co.lge.goldstar.core.repository.GoldStarQuerydslRepositorySupport;
import kr.co.lge.goldstar.orm.jpa.entity.member.ManagerEntity;
import kr.co.lge.goldstar.orm.jpa.entity.member.QManagerEntity;
import kr.co.lge.goldstar.orm.jpa.repository.dsl.ManagerDslRepository;
import kr.co.rebel9.web.data.DataMap;

/**
 * @author cerakoted, base@rebel9.co.kr, cerakoted@gmail.com
 * @since 1.0
 * @date 2019. 06. 27
 *
 */

@Repository("ManagerDslRepository")
public class ManagerDslRepositoryImpl extends GoldStarQuerydslRepositorySupport implements ManagerDslRepository {

    /**
     * @param domainClass
     */
    public ManagerDslRepositoryImpl() {
        super(ManagerEntity.class);
    }

    @Override
    public Page<ManagerEntity> findPageBySearch(DataMap params, Pageable pageable) {
        
        QManagerEntity entity = QManagerEntity.managerEntity;
        JPQLQuery<ManagerEntity> query = from(entity);
        
        getQuerydsl().applyPagination(pageable, query);
        getQuerydsl().applySorting(Sort.by(Direction.DESC, "sn"), query);
        
        return new PageImpl<>(query.fetch(), pageable, query.fetchCount());
    }

}
