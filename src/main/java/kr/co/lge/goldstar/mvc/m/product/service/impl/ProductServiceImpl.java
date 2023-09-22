package kr.co.lge.goldstar.mvc.m.product.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import kr.co.lge.goldstar.mvc.m.product.service.ProductService;
import kr.co.lge.goldstar.orm.jpa.entity.PursueAnswerType;
import kr.co.lge.goldstar.orm.jpa.entity.YesOrNo;
import kr.co.lge.goldstar.orm.jpa.entity.product.ProductEntity;
import kr.co.lge.goldstar.orm.jpa.repository.spring.ProductRepository;
import kr.co.rebel9.core.utils.FileUtils;
import kr.co.rebel9.web.data.DataMap;

@Service("ProductService")
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;
    
    @Value("${multipart.path.product}")
    private String productPath;

	@Override
	public DataMap getProducts() {
		
		List<ProductEntity> productEntities = this.productRepository.findByDeletedOrderByTypeAscOrdinalAsc(YesOrNo.N);
		
		List<DataMap> productList = new ArrayList<DataMap>();
		
		PursueAnswerType[] types = PursueAnswerType.values();
		for(PursueAnswerType type : types) {
			
			DataMap product = new DataMap();
			productList.add(product);
			
			List<ProductEntity> list = new ArrayList<>();
			product.put("list", list);
			product.put("type", type);
			
			if(CollectionUtils.isNotEmpty(productEntities)) {
				for(ProductEntity entity : productEntities) {
					if(type.name().equals(entity.getType().name())) {
						list.add(entity);
					}
				}
			}
		}

		DataMap result = new DataMap();
		result.put("productList", productList);
		
		return result;
	}

	@Override
	public DataMap save(MultipartFile listFile, MultipartFile viewFile, DataMap params) {
		
		if(ObjectUtils.isEmpty(listFile)){
			DataMap result = new DataMap(false);
			result.put("reason", "목록 파일을 찾을 수 없습니다.");
			return result;
        }
		if(ObjectUtils.isEmpty(viewFile)){
			DataMap result = new DataMap(false);
			result.put("reason", "상세 파일을 찾을 수 없습니다.");
			return result;
        }
		
        UUID uuid = UUID.randomUUID();
        String dateDir = FileUtils.getDateDir();
        String listPath = new StringBuilder(dateDir).append(uuid).append("_l").toString();
        String viewPath = new StringBuilder(dateDir).append(uuid).append("_v").toString();

        try {
			org.apache.commons.io.FileUtils.writeByteArrayToFile(
			        new File(new StringBuilder(this.productPath).append(listPath).toString()), 
			        listFile.getBytes());
			org.apache.commons.io.FileUtils.writeByteArrayToFile(
			        new File(new StringBuilder(this.productPath).append(viewPath).toString()), 
			        viewFile.getBytes());
		} catch (IOException e) {
			DataMap result = new DataMap(false);
			result.put("reason", e.getMessage());
			return result;
		}
		
		ProductEntity product = new ProductEntity();

		product.setName(params.getAsString("name"));
		product.setOrdinal(this.productRepository.maxOrdinal(YesOrNo.N).intValue());
		product.setSubject(params.getAsString("subject"));
		product.setContents(params.getAsString("contents"));
		product.setUrl(params.getAsString("url"));
		product.setType(PursueAnswerType.from(params.getAsString("type")));
		product.setDeleted(YesOrNo.N);

		product.setImageList(listPath);
		product.setImageView(viewPath);
		
		this.productRepository.save(product);
		
		return new DataMap(true);
	}

	@Override
	public DataMap update(MultipartFile listFile, MultipartFile viewFile, DataMap params) {
		
		ProductEntity product = this.productRepository.findBySnAndDeleted(params.getAsInt("sn"), YesOrNo.N);
		if(ObjectUtils.isEmpty(product)) {
			DataMap result = new DataMap(false);
			result.put("reason", "데이터를 찾을 수 없습니다.");
			return result;
		}
		
		UUID uuid = UUID.randomUUID();
        String dateDir = FileUtils.getDateDir();

        try {
        	if(!ObjectUtils.isEmpty(listFile)){
        		
                String listPath = new StringBuilder(dateDir).append(uuid).append("_l").toString();
				org.apache.commons.io.FileUtils.writeByteArrayToFile(
				        new File(new StringBuilder(this.productPath).append(listPath).toString()), 
				        listFile.getBytes());
				
				product.setImageList(listPath);
        	}
    		if(!ObjectUtils.isEmpty(viewFile)){
    			
    	        String pcPath = new StringBuilder(dateDir).append(uuid).append("_p").toString();
				org.apache.commons.io.FileUtils.writeByteArrayToFile(
				        new File(new StringBuilder(this.productPath).append(pcPath).toString()), 
				        viewFile.getBytes());
				
				product.setImageView(pcPath);
    		}
			
		} catch (IOException e) {
			DataMap result = new DataMap(false);
			result.put("reason", e.getMessage());
			return result;
		}

        product.setName(params.getAsString("name"));
		product.setSubject(params.getAsString("subject"));
		product.setContents(params.getAsString("contents"));
		product.setUrl(params.getAsString("url"));

		this.productRepository.save(product);
		
		return new DataMap(true);
	}

	@Override
	public DataMap remove(ProductEntity productEntity) {
		
		Optional<ProductEntity> saved = this.productRepository.findById(productEntity.getSn());
		if(saved.isEmpty()) {
			DataMap result = new DataMap(false);
			result.put("reason", "질문 데이터를 찾을 수 없습니다.");
			return result;
		}
		
		ProductEntity savedEntity = saved.get();
		savedEntity.setDeleted(YesOrNo.Y);
		this.productRepository.save(savedEntity);
		
		return new DataMap(true);
	}

	@Override
	public DataMap get(ProductEntity productEntity) {
		
		DataMap result = new DataMap(true);
		result.put("entity", this.productRepository.findById(productEntity.getSn()).get());
		
		return result;
	}

	@Override
	public DataMap sort(DataMap params) {
		
		List<Integer> snList = params.getListAsInteger("snList[]");
		
		List<ProductEntity> productEntities = this.productRepository.findBySnIn(snList);
		
		if(CollectionUtils.isEmpty(productEntities)) {
			DataMap result = new DataMap(false);
			result.put("reason", "데이터를 찾을 수 없습니다.");
			return result;
		}
		
		Map<Integer, ProductEntity> map = new HashMap<>();
		productEntities.forEach(item -> {
			map.put(item.getSn(), item);
		});
		
		int order = 1;
		for(Integer sn : snList) {
			map.get(sn).setOrdinal(order++);
		}
		
		this.productRepository.saveAll(productEntities);
		
		return new DataMap(true);
	}

}
