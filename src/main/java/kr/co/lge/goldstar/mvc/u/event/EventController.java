package kr.co.lge.goldstar.mvc.u.event;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import kr.co.lge.goldstar.mvc.u.event.service.EventService;
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
@Controller("u.EventController")
@RequestMapping(value = "u/event")
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
    public String list(ModelMap model) {
        
        List<EventEntity> events = this.eventService.getList();
        model.put("events", events);
        
        return "u/event/list";
    }
    
    /**
     * @param bbsSn 
     * @param sn 
     * @param paramMap
     * @param model
     * @param session 
     * @return view
     */
    @RequestMapping(value = "view/{sn}.do", method = {RequestMethod.GET, RequestMethod.POST})
    public String view(@PathVariable int sn, ModelMap model) {
        
        EventEntity entity = this.eventService.getEventEntity(sn);
        model.put("event", entity);
        
        List<EventEntity> events = this.eventService.getList();
        model.put("events", events);

        return "u/event/view";
    }
    
    /**
	 * @param sn
	 * @param modelMap 
	 * @return 파일 다운로드
	 */
	@RequestMapping(value = "file/thumb/{sn}.do", method = RequestMethod.GET)
	public ModelAndView thumbFile(@PathVariable int sn, ModelMap modelMap) {
		
	    DataMap data = this.eventService.getThumbFile(sn);
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
	@RequestMapping(value = "file/pc/{sn}.do", method = RequestMethod.GET)
	public ModelAndView pcViewFile(@PathVariable int sn, ModelMap modelMap) {
		
	    DataMap data = this.eventService.getPcViewFile(sn);
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
	@RequestMapping(value = "file/mobile/{sn}.do", method = RequestMethod.GET)
	public ModelAndView mobileViewFile(@PathVariable int sn, ModelMap modelMap) {
		
	    DataMap data = this.eventService.getMoViewFile(sn);
	    if(!ObjectUtils.isEmpty(data)) {
	        modelMap.putAll(data);
	    }
	    
	    return new ModelAndView("ImageView");
	}
}
