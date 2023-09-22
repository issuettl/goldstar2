package kr.co.lge.goldstar.orm.jpa.repository.spring;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import kr.co.lge.goldstar.orm.jpa.entity.PursueAnswerType;
import kr.co.lge.goldstar.orm.jpa.entity.YesOrNo;
import kr.co.lge.goldstar.orm.jpa.entity.product.ProductEntity;

/**
 * @author cerakoted, base@rebel9.co.kr, cerakoted@gmail.com
 * @since 1.0
 * @date 2019. 11. 14
 *
 */
@Repository("ProductRepository")
public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {

	/**
	 * @param type
     * @param deleted
	 * @return
	 */
	List<ProductEntity> findByTypeAndDeletedOrderByOrdinalAsc(PursueAnswerType type, YesOrNo deleted);

	/**
	 * @param deleted
	 * @return
	 */
	List<ProductEntity> findByDeletedOrderByTypeAscOrdinalAsc(YesOrNo deleted);

	ProductEntity findBySnAndDeleted(int sn, YesOrNo deleted);
	
	/**
     * @param deleted
     * @return 
     */
    @Query("select coalesce(max(a.ordinal), 0) + 1 as max from survey a where a.deleted = ?1")
    Long maxOrdinal(YesOrNo deleted);

	/**
	 * @param snList
	 * @return
	 */
	List<ProductEntity> findBySnIn(List<Integer> snList);

}
