/**
 * 
 */
package kr.co.lge.goldstar.orm.jpa.repository.dsl;

/**
 * @author NEOFLOW
 *
 */
public interface IndexDslRepository {

	/**
	 * @param today
	 * @return
	 */
	long getTodayMemberCount(String today);

	/**
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	long getTotalMemberCount(String startDate, String endDate);

}
