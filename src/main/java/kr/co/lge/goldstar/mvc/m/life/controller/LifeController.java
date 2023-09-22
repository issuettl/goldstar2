/**
 * 
 */
package kr.co.lge.goldstar.mvc.m.life.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.co.lge.goldstar.core.pagination.Paging;
import kr.co.lge.goldstar.mvc.m.life.service.LifeService;
import kr.co.lge.goldstar.orm.jpa.entity.LifeTime;
import kr.co.lge.goldstar.orm.jpa.entity.StaffCheck;
import kr.co.lge.goldstar.orm.jpa.entity.life.LifePartEntity;
import kr.co.rebel9.web.data.DataMap;

/**
 * @author issuettl
 *
 */
@Controller("m.LifeController")
@RequestMapping("m/life")
public class LifeController {

    @Autowired
    private LifeService lifeService;

	/**
	 * @param session 
	 * @return 인증페이지
	 */
	@RequestMapping("weekday.do")
	public String weekday(@RequestParam MultiValueMap<String, Object> paramMap, ModelMap model){
		
		DataMap params = new DataMap(paramMap);
        params.put("size", params.getAsInt("size", 10));
        
        Page<LifePartEntity> page = this.lifeService.getPageWeekday(params);
        model.put("page", page);
        model.put("paging", new Paging(page));
        
        model.put("staffCheck", StaffCheck.values());
        model.put("notDates", this.lifeService.getNotDates());
		
		return "m/life/weekday";
	}

	/**
	 * @param session 
	 * @return 인증페이지
	 */
	@RequestMapping("weekend.do")
	public String weekend(@RequestParam MultiValueMap<String, Object> paramMap, ModelMap model){
		
		DataMap params = new DataMap(paramMap);
        params.put("size", params.getAsInt("size", 10));
        
        Page<LifePartEntity> page = this.lifeService.getPageWeekend(params);
        model.put("page", page);
        model.put("paging", new Paging(page));

        model.put("lifeTime", LifeTime.values());
		
		return "m/life/weekend";
	}

}
