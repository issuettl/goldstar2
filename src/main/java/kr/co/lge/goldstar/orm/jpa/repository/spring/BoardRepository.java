package kr.co.lge.goldstar.orm.jpa.repository.spring;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.co.lge.goldstar.orm.jpa.entity.YesOrNo;
import kr.co.lge.goldstar.orm.jpa.entity.board.BoardEntity;

/**
 * @author cerakoted, base@rebel9.co.kr, cerakoted@gmail.com
 * @since 1.0
 * @date 2019. 11. 15
 *
 */
@Repository("BoardRepository")
public interface BoardRepository extends JpaRepository<BoardEntity, Integer> {
    
    /**
     * @param bbsSn
     * @param sn
     * @param deleted
     * @return home board entity
     */
	BoardEntity findByBbsSnAndSnAndDeleted(int bbsSn, int sn, YesOrNo deleted);
    
    /**
     * @param sn
     * @param deleted
     * @return home board entity
     */
	BoardEntity findBySnAndDeleted(int sn, YesOrNo deleted);
    
    /**
     * @param snList
     * @return home board entities
     */
    List<BoardEntity> findBySnIn(List<Integer> snList);

	/**
	 * @param bbsSn
	 * @param deleted
	 * @param sn
	 * @return
	 */
	BoardEntity findFirstByBbsSnAndDeletedAndSnGreaterThanOrderBySnAsc(int bbsSn, YesOrNo deleted, int sn);

	/**
	 * @param bbsSn
	 * @param deleted
	 * @param sn
	 * @return
	 */
	BoardEntity findFirstByBbsSnAndDeletedAndSnLessThanOrderBySnDesc(int bbsSn, YesOrNo deleted, int sn);

}
