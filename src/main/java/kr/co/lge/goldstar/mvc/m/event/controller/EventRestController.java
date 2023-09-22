/**
 * 
 */
package kr.co.lge.goldstar.mvc.m.event.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import kr.co.lge.goldstar.mvc.m.event.service.EventService;
import kr.co.lge.goldstar.orm.jpa.entity.event.EventEntity;
import kr.co.rebel9.web.data.DataMap;
import kr.co.rebel9.web.rest.result.ResultBuilder;
import kr.co.rebel9.web.rest.result.ResultConst;

/**
 * @author issuettl
 *
 */
@RestController("m.EventRestController")
@RequestMapping(value = "m/event")
public class EventRestController {

	@Autowired
	private EventService eventService;
	
	/**
	 * @param param
	 * @param session
	 * @return 로그인 결과
	 */
	@PostMapping("save/action.do")
	public DataMap saveAction(MultipartFile thumbFile, MultipartFile pcViewFile, MultipartFile moViewFile, @RequestParam MultiValueMap<String, Object> params) {
        try {
            return this.eventService.saveAction(thumbFile, pcViewFile, moViewFile, new DataMap(params));
        }catch(Exception e) {
			DataMap result = new DataMap(false);
			result.put(ResultConst.META, ResultBuilder.failure(e));
			return result;
		}
	}
	
	/**
	 * @param param
	 * @param session
	 * @return 로그인 결과
	 */
	@PostMapping("get.do")
	public DataMap get(@RequestBody EventEntity eventEntity) {
        try {
        	DataMap result = new DataMap(true);
        	result.put("event", this.eventService.getEventEntity(eventEntity.getSn()));
            return result;
        }catch(Exception e) {
			DataMap result = new DataMap(false);
			result.put(ResultConst.META, ResultBuilder.failure(e));
			return result;
		}
	}
	
	/**
	 * @param param
	 * @param session
	 * @return 로그인 결과
	 */
	@PostMapping("update/action.do")
	public DataMap updateAction(MultipartFile thumbFile, MultipartFile pcViewFile, MultipartFile moViewFile, @RequestParam MultiValueMap<String, Object> params) {
        try {
            return this.eventService.updateAction(thumbFile, pcViewFile, moViewFile, new DataMap(params));
        }catch(Exception e) {
			DataMap result = new DataMap(false);
			result.put(ResultConst.META, ResultBuilder.failure(e));
			return result;
		}
	}
	
	/**
	 * @param param
	 * @param session
	 * @return 로그인 결과
	 */
	@PostMapping("remove/action.do")
	public DataMap remove(@RequestBody EventEntity eventEntity) {
        try {
            return this.eventService.removeAction(eventEntity);
        }catch(Exception e) {
			DataMap result = new DataMap(false);
			result.put(ResultConst.META, ResultBuilder.failure(e));
			return result;
		}
	}
}
