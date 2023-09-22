/**
 * 
 */
package kr.co.lge.goldstar.mvc.u.product.service;

import java.util.List;

import kr.co.lge.goldstar.orm.jpa.entity.PursueAnswerType;
import kr.co.lge.goldstar.orm.jpa.entity.product.ProductEntity;
import kr.co.rebel9.web.data.DataMap;

/**
 * @author issuettl
 *
 */
public interface ProductService {

	/**
	 * @param pursueType
	 * @return
	 */
	List<ProductEntity> products(PursueAnswerType pursueType);

	/**
	 * @param sn
	 * @return
	 */
	DataMap getListFile(int sn);

	/**
	 * @param sn
	 * @return
	 */
	DataMap getViewFile(int sn);

}
