/**
 * 
 */
package kr.co.lge.goldstar.mvc.m.index.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import kr.co.lge.goldstar.mvc.m.index.service.IndexService;
import kr.co.rebel9.web.data.DataMap;

/**
 * @author issuettl
 *
 */
@Controller("m.IndexController")
@RequestMapping("m")
public class IndexController {

    @Autowired
    private IndexService indexService;
    
    @RequestMapping(value = "dash.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String index(@RequestParam MultiValueMap<String, Object> paramMap, ModelMap model) {
		
		model.putAll(this.indexService.getDash(new DataMap(paramMap)));
         
        return "m/index";
	}
}
