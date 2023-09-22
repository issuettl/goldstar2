package kr.co.lge.goldstar.orm.jpa.repository.spring;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.co.lge.goldstar.orm.jpa.entity.YesOrNo;
import kr.co.lge.goldstar.orm.jpa.entity.event.EventEntity;

/**
 * @author cerakoted, base@rebel9.co.kr, cerakoted@gmail.com
 * @since 1.0
 * @date 2019. 11. 15
 *
 */
@Repository("EventRepository")
public interface EventRepository extends JpaRepository<EventEntity, Integer> {

	/**
	 * @param sn
	 * @param n
	 * @return
	 */
	EventEntity findBySnAndDeleted(int sn, YesOrNo n);

	/**
	 * @param deleted
	 * @param today1
	 * @param today2
	 * @return
	 */
	List<EventEntity> findAllByDeletedAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByStartDateDescEndDateAsc(YesOrNo deleted, String today1, String today2);

	/**
	 * @param deleted
	 * @param today1
	 * @param today2
	 * @return
	 */
	List<EventEntity> findFirst3ByDeletedAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByStartDateDescEndDateAsc(
			YesOrNo deleted, String today1, String today2);

}
