package kr.co.lge.goldstar.orm.jpa.repository.spring;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.co.lge.goldstar.orm.jpa.entity.YesOrNo;
import kr.co.lge.goldstar.orm.jpa.entity.board.BbsEntity;

/**
 * @author cerakoted, base@rebel9.co.kr, cerakoted@gmail.com
 * @since 1.0
 * @date 2019. 11. 14
 *
 */
@Repository("BbsRepository")
public interface BbsRepository extends JpaRepository<BbsEntity, Integer> {
    
    /**
     * @param sn
     * @param used
     * @return HomeBbsEntity
     */
	BbsEntity findBySnAndUsed(int sn, YesOrNo used);

}
