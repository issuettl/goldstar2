/**
 * 
 */
package kr.co.lge.goldstar.orm.jpa.repository.dsl.impl;

import org.springframework.stereotype.Repository;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.SubQueryExpression;
import com.querydsl.jpa.JPQLQuery;

import kr.co.lge.goldstar.core.repository.GoldStarQuerydslRepositorySupport;
import kr.co.lge.goldstar.orm.jpa.entity.StaffCheck;
import kr.co.lge.goldstar.orm.jpa.entity.mind.MindPartEntity;
import kr.co.lge.goldstar.orm.jpa.entity.mind.QMindPartEntity;
import kr.co.lge.goldstar.orm.jpa.repository.dsl.MindPartDslRepository;

/**
 * @author NEOFLOW
 *
 */
//@Repository("MindPartDslRepository")
public class MindPartDslRepositoryImpl extends GoldStarQuerydslRepositorySupport implements MindPartDslRepository {

	/**
	 * @param domainClass
	 */
	public MindPartDslRepositoryImpl() {
		super(MindPartEntity.class);
	}

	@Override
	public SubQueryExpression<Tuple> getTodayMemberCount(String today) {
		
		QMindPartEntity entity = QMindPartEntity.mindPartEntity;
        JPQLQuery<MindPartEntity> query = from(entity);
        
        query.where(entity.signCreated.eq(today));
        query.where(entity.staffCheck.eq(StaffCheck.present));
        
		return query.select(new Expression[] {entity.signCreated, entity.signMemberSn});
	}
}
