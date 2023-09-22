package kr.co.lge.goldstar.orm.jpa.repository.spring;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.co.lge.goldstar.orm.jpa.entity.StaffCheck;
import kr.co.lge.goldstar.orm.jpa.entity.refresh.RefreshPartEntity;

/**
 * @author cerakoted, base@rebel9.co.kr, cerakoted@gmail.com
 * @since 1.0
 * @date 2019. 11. 14
 *
 */
@Repository("RefreshPartRepository")
public interface RefreshPartRepository extends JpaRepository<RefreshPartEntity, Integer> {

	/**
	 * @param signMemberSn
	 * @param signCreated
	 * @return
	 */
	RefreshPartEntity findBySignMemberSnAndSignCreated(int signMemberSn, String signCreated);

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
	 * @param startDate
	 * @param endDate
	 * @param present
	 * @return
	 */
	long countBySignCreatedGreaterThanEqualAndSignCreatedLessThanEqualAndStaffCheck(String startDate, String endDate,
			StaffCheck present);
    

}
