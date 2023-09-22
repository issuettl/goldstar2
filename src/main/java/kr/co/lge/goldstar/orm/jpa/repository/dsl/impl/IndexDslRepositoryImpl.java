/**
 * 
 */
package kr.co.lge.goldstar.orm.jpa.repository.dsl.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.SubQueryExpression;
import com.querydsl.sql.SQLQuery;

import kr.co.lge.goldstar.orm.jpa.repository.dsl.IndexDslRepository;
import kr.co.lge.goldstar.orm.jpa.repository.dsl.MindPartDslRepository;

/**
 * @author NEOFLOW
 *
 */
//@Repository("IndexDslRepository")
public class IndexDslRepositoryImpl implements IndexDslRepository {
	
	//@Autowired
	private MindPartDslRepository mindPartDslRepository;

	@Override
	public long getTodayMemberCount(String today) {

		SubQueryExpression<Tuple> mindPart1 = this.mindPartDslRepository.getTodayMemberCount(today);
		SubQueryExpression<Tuple> mindPart2 = this.mindPartDslRepository.getTodayMemberCount(today);
		SubQueryExpression<Tuple> mindPart3 = this.mindPartDslRepository.getTodayMemberCount(today);
		
		return new SQLQuery<Void>().union(mindPart1, mindPart2, mindPart3).fetchCount();
	}

	@Override
	public long getTotalMemberCount(String startDate, String endDate) {
		// TODO Auto-generated method stub
		return 0;
	}

}
