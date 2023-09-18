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
import org.springframework.util.StringUtils;

import com.querydsl.jpa.JPQLQuery;

import kr.co.lge.goldstar.core.repository.GoldStarQuerydslRepositorySupport;
import kr.co.lge.goldstar.orm.jpa.entity.YesOrNo;
import kr.co.lge.goldstar.orm.jpa.entity.event.EventEntity;
import kr.co.lge.goldstar.orm.jpa.entity.event.QEventEntity;
import kr.co.lge.goldstar.orm.jpa.repository.dsl.EventDslRepository;
import kr.co.rebel9.web.data.DataMap;

/**
 * @author cerakoted, base@rebel9.co.kr, cerakoted@gmail.com
 * @since 1.0
 * @date 2019. 06. 27
 *
 */

@Repository("EventDslRepository")
public class EventDslRepositoryImpl extends GoldStarQuerydslRepositorySupport implements EventDslRepository {

    /**
     * @param domainClass
     */
    public EventDslRepositoryImpl() {
        super(EventEntity.class);
    }

    @Override
    public Page<EventEntity> findPageBySearch(DataMap params, Pageable pageable) {
        
        QEventEntity entity = QEventEntity.eventEntity;
        JPQLQuery<EventEntity> query = from(entity);
        
        //삭제여부
        query.where(entity.deleted.eq(YesOrNo.N));
        
        //시작일
        String dateStart = params.getAsString("dateStart");
        if(StringUtils.hasText(dateStart)){
            query.where(entity.startDate.goe(dateStart));
        }
        
        //종료일
        String dateEnd = params.getAsString("dateEnd");
        if(StringUtils.hasText(dateEnd)){
            query.where(entity.endDate.loe(dateEnd));
        }
        
        getQuerydsl().applyPagination(pageable, query);
        getQuerydsl().applySorting(Sort.by(Direction.DESC, "sn"), query);
        
        return new PageImpl<>(query.fetch(), pageable, query.fetchCount());
    }

    @Override
    public Page<EventEntity> findPageByHome(DataMap params, Pageable pageable) {
        
        QEventEntity entity = QEventEntity.eventEntity;
        JPQLQuery<EventEntity> query = from(entity);
        
        //삭제여부
        query.where(entity.deleted.eq(YesOrNo.N));
        
        getQuerydsl().applyPagination(pageable, query);
        getQuerydsl().applySorting(Sort.by(Direction.DESC, "sn"), query);
        
        return new PageImpl<>(query.fetch(), pageable, query.fetchCount());
    }

}
