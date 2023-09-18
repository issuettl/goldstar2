package kr.co.lge.goldstar.mvc.m.event.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import kr.co.lge.goldstar.core.pagination.Paging;
import kr.co.lge.goldstar.mvc.m.event.service.EventService;
import kr.co.lge.goldstar.orm.jpa.entity.event.EventEntity;
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
@Controller("m.EventController")
@RequestMapping(value = "m/event")
public class EventController {
    
	@Autowired
	private EventService eventService;
	
    /**
     * @param bbsSn 
     * @param paramMap
     * @param model
     * @param session 
     * @return view
     */
    @RequestMapping(value = "list.do", method = {RequestMethod.GET, RequestMethod.POST})
    public String list(@RequestParam MultiValueMap<String, Object> paramMap, ModelMap model, HttpSession session) {
        
        DataMap params = new DataMap(paramMap);
        params.put("size", params.getAsInt("size", 999999));
        
        Page<EventEntity> page = this.eventService.getPage(params);
        
        model.put("page", page);
        model.put("paging", new Paging(page));
        
        model.put("params", params);
        
        return "m/event/list";
    }
}
