package kr.co.lge.goldstar.orm.jpa.repository.spring;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
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
	 * @param status
     * @param deleted
	 * @return
	 */
	List<ProductEntity> findByTypeAndDeletedOrderByOrdinalAsc(PursueAnswerType type, YesOrNo deleted);

}
