package kr.co.lge.goldstar.orm.jpa.repository.spring;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.co.lge.goldstar.orm.jpa.entity.StaffCheck;
import kr.co.lge.goldstar.orm.jpa.entity.mind.MindPartEntity;

/**
 * @author cerakoted, base@rebel9.co.kr, cerakoted@gmail.com
 * @since 1.0
 * @date 2019. 11. 14
 *
 */
@Repository("MindPartDslRepository")
public interface MindPartRepository extends JpaRepository<MindPartEntity, Integer> {

	/**
	 * @param memberSn
	 * @param created
	 * @return
	 */
	MindPartEntity findBySignMemberSnAndSignCreated(int memberSn, String created);

	/**
	 * @param sn
	 * @return
	 */
	long countBySignMemberSnAndStaffCheck(int sn, StaffCheck staffCheck);

	/**
	 * @param signCreated
	 * @param staffCheck
	 * @return
	 */
	long countBySignCreatedAndStaffCheck(String signCreated, StaffCheck staffCheck);

	/**
	 * @param memberSn
	 * @param created
	 * @return
	 */
	long countBySignMemberSnAndSignCreated(int memberSn, String created);

	/**
	 * @param startDate
	 * @param endDate
	 * @param present
	 * @return
	 */
	long countBySignCreatedGreaterThanEqualAndSignCreatedLessThanEqualAndStaffCheck(String startDate, String endDate,
			StaffCheck present);


}
