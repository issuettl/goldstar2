package kr.co.lge.goldstar.orm.jpa.repository.spring;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.co.lge.goldstar.orm.jpa.entity.LifeStatus;
import kr.co.lge.goldstar.orm.jpa.entity.LifeTime;
import kr.co.lge.goldstar.orm.jpa.entity.LifeType;
import kr.co.lge.goldstar.orm.jpa.entity.StaffCheck;
import kr.co.lge.goldstar.orm.jpa.entity.life.LifePartEntity;

/**
 * @author cerakoted, base@rebel9.co.kr, cerakoted@gmail.com
 * @since 1.0
 * @date 2019. 11. 14
 *
 */
@Repository("LifePartRepository")
public interface LifePartRepository extends JpaRepository<LifePartEntity, Integer> {

	/**
	 * @param date
	 * @return
	 */
	List<LifePartEntity> findByDate(String date);

	/**
	 * @param date
	 * @param time
	 * @return
	 */
	List<LifePartEntity> findByDateAndTime(String date, LifeTime time);

	/**
	 * @param memberSn
	 * @param date
	 * @return
	 */
	List<LifePartEntity> findByMemberSnAndDate(int memberSn, String date);

	/**
	 * @param snList
	 * @return
	 */
	List<LifePartEntity> findBySnIn(List<Integer> snList);

	/**
	 * @param type
	 * @param date
	 * @param time
	 * @param status
	 * @param sn
	 * @return
	 */
	List<LifePartEntity> findByTypeAndDateAndTimeAndStatusAndSnNot(LifeType type, String date, LifeTime time,
			LifeStatus status, int sn);

	/**
	 * @param sn
	 * @return
	 */
	List<LifePartEntity> findAllByMemberSnOrderByDateDescTimeDesc(int memberSn);

	/**
	 * @param type
	 * @param date
	 * @param time
	 * @param status
	 * @return
	 */
	List<LifePartEntity> findByTypeAndDateAndTimeAndStatus(LifeType type, String date, LifeTime time,
			LifeStatus status);

	/**
	 * @param type
	 * @param date
	 * @param time
	 * @param sn
	 * @return
	 */
	List<LifePartEntity> findByTypeAndDateAndTimeAndSnNot(LifeType type, String date, LifeTime time, int sn);

	/**
	 * @param sn
	 * @param status
	 * @return
	 */
	long countByMemberSnAndStatus(int sn, LifeStatus status);

	/**
	 * @param today
	 * @param status
	 * @return
	 */
	long countByDateAndStatus(String today, LifeStatus status);

	/**
	 * @param startDate
	 * @param endDate 
	 * @param status
	 * @return
	 */
	long countByDateGreaterThanEqualAndDateLessThanEqualAndStatus(String startDate, String endDate, LifeStatus status);

	/**
	 * @param memberSn
	 * @param created
	 * @param status2
	 * @param notyet
	 * @return
	 */
	LifePartEntity findByMemberSnAndDateAndStatusAndStaffCheck(int memberSn, String created, LifeStatus status2,
			StaffCheck notyet);

	/**
	 * @param date
	 * @param phone
	 * @return
	 */
	List<LifePartEntity> findByDateAndMemberPhone(String date, String phone);

	/**
	 * @param phone
	 * @param created
	 * @param status2
	 * @param notyet
	 * @return
	 */
	LifePartEntity findFirstByMemberPhoneAndDateAndStatusAndStaffCheck(String phone, String created, LifeStatus status2,
			StaffCheck notyet);
}
