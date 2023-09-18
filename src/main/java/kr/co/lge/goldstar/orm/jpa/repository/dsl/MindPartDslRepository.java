/**
 * 
 */
package kr.co.lge.goldstar.orm.jpa.repository.dsl;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.SubQueryExpression;

/**
 * @author NEOFLOW
 *
 */
public interface MindPartDslRepository {

	/**
	 * @param today
	 * @return
	 */
	SubQueryExpression<Tuple> getTodayMemberCount(String today);

}
