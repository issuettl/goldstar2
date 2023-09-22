/**
 * 
 */
package kr.co.lge.goldstar.mvc.u.event.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import kr.co.lge.goldstar.mvc.u.event.service.EventService;
import kr.co.lge.goldstar.orm.jpa.entity.YesOrNo;
import kr.co.lge.goldstar.orm.jpa.entity.event.EventEntity;
import kr.co.lge.goldstar.orm.jpa.repository.spring.EventRepository;
import kr.co.rebel9.core.utils.DateUtils;
import kr.co.rebel9.web.data.DataMap;

/**
 * @author issuettl
 *
 */
@Service("u.EventService")
public class EventServiceImpl implements EventService {
	
	@Autowired
	private EventRepository eventRepository;
    
    @Value("${multipart.path.event}")
    private String eventPath;

	@Override
	public List<EventEntity> getList() {
		String today = DateUtils.getToday("yyyyMMdd");
		return this.eventRepository.findAllByDeletedAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByStartDateDescEndDateAsc(YesOrNo.N, today, today);
	}

	@Override
	public List<EventEntity> getIndex() {
		String today = DateUtils.getToday("yyyyMMdd");
		return this.eventRepository.findFirst3ByDeletedAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByStartDateDescEndDateAsc(YesOrNo.N, today, today);
	}

	@Override
	public EventEntity getEventEntity(int sn) {

		EventEntity entity = this.eventRepository.findBySnAndDeleted(sn, YesOrNo.N);
        
        if(ObjectUtils.isEmpty(entity)){
            return null;
        }
        
        if(StringUtils.hasLength(entity.getContents())) {
        	entity.setContents(kr.co.rebel9.core.utils.StringUtils.nl2br(entity.getContents()));
        }

		return entity;
	}

	@Override
	public DataMap getThumbFile(int sn) {

		EventEntity eventEntity = getEventEntity(sn);
		if(ObjectUtils.isEmpty(eventEntity)) {
			DataMap result = new DataMap(false);
			result.put("reason", "데이터를 찾을 수 없습니다.");
			return result;
		}
		
		DataMap result = new DataMap();
		result.put("defaultPath", this.eventPath);
		result.put("filePath", eventEntity.getThumb());
		result.put("contentType", eventEntity.getThumbContentType());
		
		return result;
	}

	@Override
	public DataMap getPcViewFile(int sn) {

		EventEntity eventEntity = getEventEntity(sn);
		if(ObjectUtils.isEmpty(eventEntity)) {
			DataMap result = new DataMap(false);
			result.put("reason", "데이터를 찾을 수 없습니다.");
			return result;
		}
		
		DataMap result = new DataMap();
		result.put("defaultPath", this.eventPath);
		result.put("filePath", eventEntity.getPcView());
		result.put("contentType", eventEntity.getPcViewContentType());
		
		return result;
	}

	@Override
	public DataMap getMoViewFile(int sn) {

		EventEntity eventEntity = getEventEntity(sn);
		if(ObjectUtils.isEmpty(eventEntity)) {
			DataMap result = new DataMap(false);
			result.put("reason", "데이터를 찾을 수 없습니다.");
			return result;
		}
		
		DataMap result = new DataMap();
		result.put("defaultPath", this.eventPath);
		result.put("filePath", eventEntity.getMoView());
		result.put("contentType", eventEntity.getMoViewContentType());
		
		return result;
	}

}
