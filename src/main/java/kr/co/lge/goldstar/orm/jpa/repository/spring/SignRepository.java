package kr.co.lge.goldstar.orm.jpa.repository.spring;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.co.lge.goldstar.orm.jpa.entity.PursueAnswerType;
import kr.co.lge.goldstar.orm.jpa.entity.WorryType;
import kr.co.lge.goldstar.orm.jpa.entity.member.SignEntity;
import kr.co.lge.goldstar.orm.jpa.entity.member.SignId;

/**
 * @author cerakoted, base@rebel9.co.kr, cerakoted@gmail.com
 * @since 1.0
 * @date 2019. 11. 14
 *
 */
@Repository("SignRepository")
public interface SignRepository extends JpaRepository<SignEntity, SignId> {

	/**
	 * @param topCount
	 * @return
	 */
	List<SignEntity> findBy(Pageable topCount);

	/**
	 * @param memberSn 
	 * @return
	 */
	int countByIdMemberSn(int memberSn);

	/**
	 * @param today
	 * @return
	 */
	long countByIdCreatedAndWorryTypeNotNull(String today);

	/**
	 * @param today
	 * @return
	 */
	long countByIdCreatedAndPursueTypeNotNull(String today);

	/**
	 * @param today
	 * @return
	 */
	long countByWorryTypeNotNull();

	/**
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	long countByIdCreatedGreaterThanEqualAndIdCreatedLessThanEqualAndWorryTypeNotNull(String startDate, String endDate);

	/**
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	long countByIdCreatedGreaterThanEqualAndIdCreatedLessThanEqualAndPursueTypeNotNull(String startDate, String endDate);

	/**
	 * @param sn
	 * @return
	 */
	long countByIdMemberSnAndWorryTypeNotNull(int sn);
	
	/**
	 * @param sn
	 * @return
	 */
	long countByIdMemberSnAndPursueTypeNotNull(int sn);

	/**
	 * @param startDate
	 * @param endDate
	 * @param type
	 * @return
	 */
	long countByIdCreatedGreaterThanEqualAndIdCreatedLessThanEqualAndWorryType(String startDate, String endDate,
			WorryType type);

	/**
	 * @param startDate
	 * @param endDate
	 * @param type
	 * @return
	 */
	long countByIdCreatedGreaterThanEqualAndIdCreatedLessThanEqualAndPursueType(String startDate, String endDate,
			PursueAnswerType type);


}
