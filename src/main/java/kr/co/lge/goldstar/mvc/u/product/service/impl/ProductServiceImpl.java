/**
 * 
 */
package kr.co.lge.goldstar.mvc.u.product.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.lge.goldstar.mvc.u.product.service.ProductService;
import kr.co.lge.goldstar.orm.jpa.entity.PursueAnswerType;
import kr.co.lge.goldstar.orm.jpa.entity.YesOrNo;
import kr.co.lge.goldstar.orm.jpa.entity.product.ProductEntity;
import kr.co.lge.goldstar.orm.jpa.repository.spring.ProductRepository;

/**
 * @author issuettl
 *
 */
@Service("u.ProductService")
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Override
	public List<ProductEntity> products(PursueAnswerType pursueType) {
		
		return this.productRepository.findByTypeAndDeletedOrderByOrdinalAsc(pursueType, YesOrNo.N);
	}
}
