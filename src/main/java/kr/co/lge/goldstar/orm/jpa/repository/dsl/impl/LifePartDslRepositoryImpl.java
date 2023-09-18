/**
 * 
 */
package kr.co.lge.goldstar.orm.jpa.repository.dsl.impl;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;

import kr.co.lge.goldstar.core.repository.GoldStarQuerydslRepositorySupport;
import kr.co.lge.goldstar.mvc.c.encrypt.service.EncryptService;
import kr.co.lge.goldstar.orm.jpa.entity.LifeStatus;
import kr.co.lge.goldstar.orm.jpa.entity.LifeTime;
import kr.co.lge.goldstar.orm.jpa.entity.LifeType;
import kr.co.lge.goldstar.orm.jpa.entity.life.LifePartEntity;
import kr.co.lge.goldstar.orm.jpa.entity.life.QLifePartEntity;
import kr.co.lge.goldstar.orm.jpa.repository.dsl.LifePartDslRepository;
import kr.co.rebel9.core.utils.DateUtils;
import kr.co.rebel9.web.data.DataMap;

/**
 * @author cerakoted, base@rebel9.co.kr, cerakoted@gmail.com
 * @since 1.0
 * @date 2019. 06. 27
 *
 */

@Repository("LifePartDslRepository")
public class LifePartDslRepositoryImpl extends GoldStarQuerydslRepositorySupport implements LifePartDslRepository {

	@Autowired
	private EncryptService encryptService;
	
    /**
     * @param domainClass
     */
    public LifePartDslRepositoryImpl() {
        super(LifePartEntity.class);
    }

    @Override
    public Page<LifePartEntity> findPageByWeekday(DataMap params, Pageable pageable) {
        
        QLifePartEntity entity = QLifePartEntity.lifePartEntity;
        JPQLQuery<LifePartEntity> query = from(entity);
        
        query.where(entity.type.eq(LifeType.weekday).or(entity.type.eq(LifeType.weekend)));

        //상태
        query.where(
        		entity.status.eq(LifeStatus.status2)
        			.or(entity.status.eq(LifeStatus.status4))
        				.or(entity.status.eq(LifeStatus.status6)));
        
        //제목
        String name = params.getAsString("name");
        if(StringUtils.hasText(name)) {
            try {
				query.where(entity.member.name.eq(this.encryptService.encryptAES256(name)).or(entity.member.name.eq(name)));
			} catch (Exception e) {
			}
        }
        //전화번호
        String phone = params.getAsString("phone");
        if(StringUtils.hasText(phone)) {
            try {
				query.where(entity.member.phone.eq(this.encryptService.encryptAES256(phone)).or(entity.member.phone.eq(phone)));
			} catch (Exception e) {
			}
        }
        
        //시작일
        String dateStart = params.getAsString("dateStart");
        if(StringUtils.hasText(dateStart)){
            query.where(entity.date.goe(dateStart));
        }
        
        //종료일
        String dateEnd = params.getAsString("dateEnd");
        if(StringUtils.hasText(dateEnd)){
            query.where(entity.date.loe(dateEnd));
        }
        
        //당일
        String today = params.getAsString("today");
        if("Y".equals(today)){
            query.where(entity.date.eq(DateUtils.getToday("yyyyMMdd")));
        }
        
        //익일
        String tomarrow = params.getAsString("tomarrow");
        if("Y".equals(tomarrow)){
            query.where(entity.date.eq(DateUtils.getAddDate(DateUtils.getToday("yyyyMMdd"), Calendar.DATE, 1, "yyyyMMdd")));
        }
        
        //시간
        LifeTime time = params.getAsEnum("time", LifeTime.class);
        if(!ObjectUtils.isEmpty(time)){
            query.where(entity.time.eq(time));
        }
        
        BooleanExpression statusSearch = null;
        //예약
        if("Y".equals(params.getAsString("status2"))) {
        	statusSearch = statusSearch == null ? 
        			entity.status.eq(LifeStatus.status2) : 
        				statusSearch.or(entity.status.eq(LifeStatus.status2));
        }
        //체험완료
        if("Y".equals(params.getAsString("status4"))) {
        	statusSearch = statusSearch == null ? 
        			entity.status.eq(LifeStatus.status4) : 
        				statusSearch.or(entity.status.eq(LifeStatus.status4));
        }
        //권한삭제
        if("Y".equals(params.getAsString("status6"))) {
        	statusSearch = statusSearch == null ? 
        			entity.status.eq(LifeStatus.status6) : 
        				statusSearch.or(entity.status.eq(LifeStatus.status6));
        }
        //기간만료
        if("Y".equals(params.getAsString("status0"))) {
        	statusSearch = statusSearch == null ? 
        			entity.status.eq(LifeStatus.status2).and(entity.date.lt(DateUtils.getToday("yyyyMMdd"))) : 
        				statusSearch.or(entity.status.eq(LifeStatus.status2).and(entity.date.lt(DateUtils.getToday("yyyyMMdd"))));
        }
        if(statusSearch != null) {
        	query.where(statusSearch);
        }
        
        getQuerydsl().applyPagination(pageable, query);
        getQuerydsl().applySorting(Sort.by(Direction.ASC, "date", "time", "sn"), query);
        
        return new PageImpl<>(query.fetch(), pageable, query.fetchCount());
    }
    
    @Override
    public Page<LifePartEntity> findPageByWeekend(DataMap params, Pageable pageable) {
        
        QLifePartEntity entity = QLifePartEntity.lifePartEntity;
        JPQLQuery<LifePartEntity> query = from(entity);
        
        query.where(entity.type.eq(LifeType.weekend));
        
        //제목
        String name = params.getAsString("name");
        if(StringUtils.hasText(name)) {
            try {
				query.where(entity.member.name.eq(this.encryptService.encryptAES256(name)).or(entity.member.name.eq(name)));
			} catch (Exception e) {
				e.printStackTrace();
			}
        }
        //전화번호
        String phone = params.getAsString("phone");
        if(StringUtils.hasText(phone)) {
            try {
				query.where(entity.member.phone.eq(this.encryptService.encryptAES256(phone)).or(entity.member.phone.eq(phone)));
			} catch (Exception e) {
				e.printStackTrace();
			}
        }
        
        //시작일
        String dateStart = params.getAsString("dateStart");
        if(StringUtils.hasText(dateStart)){
            query.where(entity.date.goe(dateStart));
        }
        
        //종료일
        String dateEnd = params.getAsString("dateEnd");
        if(StringUtils.hasText(dateEnd)){
            query.where(entity.date.loe(dateEnd));
        }
        
        BooleanExpression timeSearch = null;
        if("Y".equals(params.getAsString("time" + LifeTime.AM11) )) {
        	timeSearch = timeSearch == null ? 
        			entity.time.eq(LifeTime.AM11) : 
        				timeSearch.or(entity.time.eq(LifeTime.AM11));
        }
        if("Y".equals(params.getAsString("time" + LifeTime.PM12) )) {
        	timeSearch = timeSearch == null ? 
        			entity.time.eq(LifeTime.PM12) : 
        				timeSearch.or(entity.time.eq(LifeTime.PM12));
        }
        if("Y".equals(params.getAsString("time" + LifeTime.PM13) )) {
        	timeSearch = timeSearch == null ? 
        			entity.time.eq(LifeTime.PM13) : 
        				timeSearch.or(entity.time.eq(LifeTime.PM13));
        }
        if("Y".equals(params.getAsString("time" + LifeTime.PM14) )) {
        	timeSearch = timeSearch == null ? 
        			entity.time.eq(LifeTime.PM14) : 
        				timeSearch.or(entity.time.eq(LifeTime.PM14));
        }
        if("Y".equals(params.getAsString("time" + LifeTime.PM15) )) {
        	timeSearch = timeSearch == null ? 
        			entity.time.eq(LifeTime.PM15) : 
        				timeSearch.or(entity.time.eq(LifeTime.PM15));
        }
        if("Y".equals(params.getAsString("time" + LifeTime.PM16) )) {
        	timeSearch = timeSearch == null ? 
        			entity.time.eq(LifeTime.PM16) : 
        				timeSearch.or(entity.time.eq(LifeTime.PM16));
        }
        if("Y".equals(params.getAsString("time" + LifeTime.PM17) )) {
        	timeSearch = timeSearch == null ? 
        			entity.time.eq(LifeTime.PM17) : 
        				timeSearch.or(entity.time.eq(LifeTime.PM17));
        }
        if("Y".equals(params.getAsString("time" + LifeTime.PM18) )) {
        	timeSearch = timeSearch == null ? 
        			entity.time.eq(LifeTime.PM18) : 
        				timeSearch.or(entity.time.eq(LifeTime.PM18));
        }
        if(timeSearch != null) {
        	query.where(timeSearch);
        }
        
        BooleanExpression statusSearch = null;
        //응모중
        if("Y".equals(params.getAsString("status1"))) {
        	statusSearch = statusSearch == null ? 
        			entity.status.eq(LifeStatus.status1) : 
        				statusSearch.or(entity.status.eq(LifeStatus.status1));
        }
        //당첨
        if("Y".equals(params.getAsString("status2"))) {
        	statusSearch = statusSearch == null ? 
        			entity.status.eq(LifeStatus.status2) : 
        				statusSearch.or(entity.status.eq(LifeStatus.status2));
        }
        //미당첨
        if("Y".equals(params.getAsString("status3"))) {
        	statusSearch = statusSearch == null ? 
        			entity.status.eq(LifeStatus.status3) : 
        				statusSearch.or(entity.status.eq(LifeStatus.status3));
        }
        if(statusSearch != null) {
        	query.where(statusSearch);
        }
        
        getQuerydsl().applyPagination(pageable, query);
        getQuerydsl().applySorting(Sort.by(Direction.ASC, "date", "time", "sn"), query);
        
        return new PageImpl<>(query.fetch(), pageable, query.fetchCount());
    }

	@Override
	public List<LifePartEntity> findAllByMemberSnOrderByDateDescTimeDesc(int sn, String name, String phone) {

		QLifePartEntity entity = QLifePartEntity.lifePartEntity;
        JPQLQuery<LifePartEntity> query = from(entity);
        
        query.where(
        		entity.member.sn.eq(sn).or(
        				entity.member.name.eq(name).and(entity.member.phone.eq(phone))));

        getQuerydsl().applySorting(Sort.by(Direction.DESC, "date", "time", "sn"), query);
        
		return query.fetch();
	}
}
