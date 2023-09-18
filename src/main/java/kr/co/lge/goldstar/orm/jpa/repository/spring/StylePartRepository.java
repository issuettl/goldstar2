package kr.co.lge.goldstar.orm.jpa.repository.spring;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.co.lge.goldstar.orm.jpa.entity.StaffCheck;
import kr.co.lge.goldstar.orm.jpa.entity.style.StylePartEntity;

/**
 * @author cerakoted, base@rebel9.co.kr, cerakoted@gmail.com
 * @since 1.0
 * @date 2019. 11. 14
 *
 */
@Repository("StylePartRepository")
public interface StylePartRepository extends JpaRepository<StylePartEntity, Integer> {

	/**
	 * @param memberSn
	 * @param created
	 * @return
	 */
	StylePartEntity findBySignMemberSnAndSignCreated(int memberSn, String created);

	/**
	 * @param sn
	 * @param present
	 * @return
	 */
	long countBySignMemberSnAndStaffCheck(int sn, StaffCheck present);

	/**
	 * @param today
	 * @param present
	 * @return
	 */
	long countBySignCreatedAndStaffCheck(String today, StaffCheck present);

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
