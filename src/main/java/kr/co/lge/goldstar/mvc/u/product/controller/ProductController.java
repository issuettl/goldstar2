package kr.co.lge.goldstar.mvc.u.product.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import kr.co.lge.goldstar.mvc.u.product.service.ProductService;
import kr.co.rebel9.web.data.DataMap;

/**
 * TODO desc
 * @author base@rebel9.co.kr JongHan Park
 * @since Jun 27, 2019
 * @version 1.0
 * @see
 *
 * << 개정이력(Modification Information) >>
 *
 *  수정일     수정자     수정내용
 *  -------     --------    ---------------------------
 *  Jun 27, 2019        박종한     최초 생성
 */
@Controller("u.ProductController")
@RequestMapping(value = "u/product")
public class ProductController {
    
    @Autowired
    private ProductService productService;
    
    /**
	 * @param sn
	 * @param modelMap 
	 * @return 파일 다운로드
	 */
	@RequestMapping(value = "file/list/{sn}.do", method = RequestMethod.GET)
	public ModelAndView thumbFile(@PathVariable int sn, ModelMap modelMap) {
		
	    DataMap data = this.productService.getListFile(sn);
	    if(!ObjectUtils.isEmpty(data)) {
	        modelMap.putAll(data);
	    }
	    
	    return new ModelAndView("ImageView");
	}
    
    /**
	 * @param sn
	 * @param modelMap 
	 * @return 파일 다운로드
	 */
	@RequestMapping(value = "file/view/{sn}.do", method = RequestMethod.GET)
	public ModelAndView pcViewFile(@PathVariable int sn, ModelMap modelMap) {
		
	    DataMap data = this.productService.getViewFile(sn);
	    if(!ObjectUtils.isEmpty(data)) {
	        modelMap.putAll(data);
	    }
	    
	    return new ModelAndView("ImageView");
	}
}
