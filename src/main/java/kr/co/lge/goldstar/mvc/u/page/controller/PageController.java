/**
 * 
 */
package kr.co.lge.goldstar.mvc.u.page.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.lge.goldstar.mvc.u.event.service.EventService;

/**
 * @author issuettl
 *
 */
@Controller("u.PageController")
@RequestMapping(value = "u")
public class PageController {
    
    @Autowired
    private EventService eventService;

	@RequestMapping(value = "page/{page}.do")
	public String page(@PathVariable String page, ModelMap model) {
		
		if("index".equals(page)) {
			model.put("events", this.eventService.getIndex());
		}
		
		return "u/page/" + page;
	}

	@RequestMapping(value = "page/{folder}/{page}.do")
	public String page(@PathVariable String folder, @PathVariable String page) {
		return "u/page/" + folder + "/" +  page;
	}

	@RequestMapping(value = "popup/{popup}.do")
	public String popup(@PathVariable String popup) {
		return "u/popup/" + popup;
	}
}
