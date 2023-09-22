/**
 * 
 */
package kr.co.lge.goldstar.mvc.u.product.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import kr.co.lge.goldstar.mvc.u.product.service.ProductService;
import kr.co.lge.goldstar.orm.jpa.entity.PursueAnswerType;
import kr.co.lge.goldstar.orm.jpa.entity.YesOrNo;
import kr.co.lge.goldstar.orm.jpa.entity.product.ProductEntity;
import kr.co.lge.goldstar.orm.jpa.repository.spring.ProductRepository;
import kr.co.rebel9.web.data.DataMap;

/**
 * @author issuettl
 *
 */
@Service("u.ProductService")
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;
    
    @Value("${multipart.path.product}")
    private String productPath;

	@Override
	public List<ProductEntity> products(PursueAnswerType pursueType) {
		
		return this.productRepository.findByTypeAndDeletedOrderByOrdinalAsc(pursueType, YesOrNo.N);
	}

	@Override
	public DataMap getListFile(int sn) {
		
		ProductEntity productEntity = this.productRepository.findBySnAndDeleted(sn, YesOrNo.N);
		if(ObjectUtils.isEmpty(productEntity)) {
			DataMap result = new DataMap(false);
			result.put("reason", "데이터를 찾을 수 없습니다.");
			return result;
		}
		
		DataMap result = new DataMap();
		result.put("defaultPath", this.productPath);
		result.put("filePath", productEntity.getImageList());
		result.put("contentType", productEntity.getListContentType());
		
		return result;
	}

	@Override
	public DataMap getViewFile(int sn) {
		
		ProductEntity productEntity = this.productRepository.findBySnAndDeleted(sn, YesOrNo.N);
		if(ObjectUtils.isEmpty(productEntity)) {
			DataMap result = new DataMap(false);
			result.put("reason", "데이터를 찾을 수 없습니다.");
			return result;
		}
		
		DataMap result = new DataMap();
		result.put("defaultPath", this.productPath);
		result.put("filePath", productEntity.getImageView());
		result.put("contentType", productEntity.getViewContentType());
		
		return result;
	}
}
