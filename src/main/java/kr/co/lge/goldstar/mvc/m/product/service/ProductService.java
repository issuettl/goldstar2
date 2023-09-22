package kr.co.lge.goldstar.mvc.m.product.service;

import org.springframework.web.multipart.MultipartFile;

import kr.co.lge.goldstar.orm.jpa.entity.product.ProductEntity;
import kr.co.rebel9.web.data.DataMap;

public interface ProductService {

	DataMap getProducts();

	DataMap save(MultipartFile listFile, MultipartFile viewFile, DataMap dataMap);

	DataMap update(MultipartFile listFile, MultipartFile viewFile, DataMap dataMap);

	DataMap remove(ProductEntity productEntity);

	DataMap get(ProductEntity productEntity);

	DataMap sort(DataMap dataMap);

}
