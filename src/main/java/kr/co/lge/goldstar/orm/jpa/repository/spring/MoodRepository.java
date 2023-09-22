package kr.co.lge.goldstar.orm.jpa.repository.spring;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import kr.co.lge.goldstar.orm.jpa.entity.YesOrNo;
import kr.co.lge.goldstar.orm.jpa.entity.mood.MoodEntity;

/**
 * @author cerakoted, base@rebel9.co.kr, cerakoted@gmail.com
 * @since 1.0
 * @date 2019. 11. 14
 *
 */
@Repository("MoodRepository")
public interface MoodRepository extends JpaRepository<MoodEntity, Integer> {

	/**
	 * @param status
     * @param deleted
	 * @return
	 */
	List<MoodEntity> findByStatusAndDeletedOrderByOrdinalAsc(YesOrNo status, YesOrNo deleted);
	
	/**
     * @param deleted
     * @return 
     */
    @Query("select coalesce(max(a.ordinal), 0) + 1 as max from mood a where a.deleted = ?1")
    Long maxOrdinal(YesOrNo deleted);

	/**
	 * @param snList
	 * @return
	 */
	List<MoodEntity> findBySnIn(List<Integer> snList);

	/**
	 * @param n
	 * @return
	 */
	List<MoodEntity> findByDeletedOrderByOrdinalAsc(YesOrNo n);


}
