/**
 * 
 */
package kr.co.lge.goldstar.mvc.m.product.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import kr.co.lge.goldstar.mvc.m.product.service.ProductService;
import kr.co.lge.goldstar.orm.jpa.entity.product.ProductEntity;
import kr.co.rebel9.web.data.DataMap;
import kr.co.rebel9.web.rest.result.ResultBuilder;
import kr.co.rebel9.web.rest.result.ResultConst;

/**
 * @author issuettl
 *
 */
@RestController("m.ProductRestController")
@RequestMapping(value = "m/product")
public class ProductRestController {
	
	@Autowired
	private ProductService productService;

	@PostMapping(value = "save.do")
	public DataMap save(MultipartFile listFile, MultipartFile viewFile, @RequestParam MultiValueMap<String, Object> params) {
		
		try {
			return this.productService.save(listFile, viewFile, new DataMap(params));
			
		}catch(Exception e) {
			DataMap result = new DataMap(false);
			result.put(ResultConst.META, ResultBuilder.failure(e));
			return result;
		}
	}
	
	@PostMapping(value = "update.do")
	public DataMap update(MultipartFile listFile, MultipartFile viewFile, @RequestParam MultiValueMap<String, Object> params) {
		
		try {
			return this.productService.update(listFile, viewFile, new DataMap(params));
			
		}catch(Exception e) {
			DataMap result = new DataMap(false);
			result.put(ResultConst.META, ResultBuilder.failure(e));
			return result;
		}
	}

	
	@PostMapping(value = "remove.do")
	public DataMap remove(@RequestBody ProductEntity productEntity) {
		
		try {
			return this.productService.remove(productEntity);
			
		}catch(Exception e) {
			DataMap result = new DataMap(false);
			result.put(ResultConst.META, ResultBuilder.failure(e));
			return result;
		}
	}

	@PostMapping(value = "get.do")
	public DataMap get(@RequestBody ProductEntity productEntity) {
		
		try {
			return this.productService.get(productEntity);
			
		}catch(Exception e) {
			DataMap result = new DataMap(false);
			result.put(ResultConst.META, ResultBuilder.failure(e));
			return result;
		}
	}

	@PostMapping(value = "sort.do")
	public DataMap sort(@RequestParam MultiValueMap<String, Object> paramMap) {
		
		try {
			return this.productService.sort(new DataMap(paramMap));
			
		}catch(Exception e) {
			DataMap result = new DataMap(false);
			result.put(ResultConst.META, ResultBuilder.failure(e));
			return result;
		}
	}
}
