/**
 * 
 */
package kr.co.lge.goldstar.mvc.m.product.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.co.lge.goldstar.mvc.m.product.service.ProductService;

/**
 * @author issuettl
 *
 */
@Controller("m.ProductController")
@RequestMapping(value = "m/product")
public class ProductController {
	
	@Autowired
	private ProductService productService;

	/**
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "index.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String index(ModelMap model) {
		
		model.putAll(this.productService.getProducts());
         
        return "m/product/index";
	}
}
