package kr.co.lge.goldstar.mvc.m.event.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import kr.co.lge.goldstar.core.pagination.PageRequestSupport;
import kr.co.lge.goldstar.mvc.m.event.service.EventService;
import kr.co.lge.goldstar.mvc.m.manager.service.ManagerLogExecution;
import kr.co.lge.goldstar.orm.jpa.entity.YesOrNo;
import kr.co.lge.goldstar.orm.jpa.entity.event.EventEntity;
import kr.co.lge.goldstar.orm.jpa.repository.dsl.EventDslRepository;
import kr.co.lge.goldstar.orm.jpa.repository.spring.EventRepository;
import kr.co.rebel9.core.utils.DateUtils;
import kr.co.rebel9.core.utils.FileUtils;
import kr.co.rebel9.web.data.DataMap;

/**
 * @author cerakoted, base@rebel9.co.kr, cerakoted@gmail.com
 * @since 1.0
 * @date 2019. 11. 15
 *
 */
@Service("m.EventService")
public class EventServiceImpl implements EventService {
    
    @Resource(name = "EventRepository")
    private EventRepository eventRepository;

    @Resource(name = "EventDslRepository")
    private EventDslRepository eventDslRepository;
    
    @Value("${multipart.path.event}")
    private String eventPath;

    @Override
	@ManagerLogExecution(process = "getEventEntity(int sn)", menu="이벤트 관리", button="상세")
    public EventEntity getEventEntity(int sn) {
        
    	EventEntity entity = this.eventRepository.findBySnAndDeleted(sn, YesOrNo.N);
        
        if(ObjectUtils.isEmpty(entity)){
            return null;
        }
        
        if(StringUtils.hasLength(entity.getContents())) {
        	entity.setContentsBr(kr.co.rebel9.core.utils.StringUtils.nl2br(entity.getContents()));
        }

        return entity;
    }

    @Override
	@ManagerLogExecution(process = "getPage(DataMap params)", menu="이벤트 관리", button="검색")
    public Page<EventEntity> getPage(DataMap params) {
        
        Page<EventEntity> page = this.eventDslRepository.findPageBySearch(
                params, PageRequestSupport.getPageRequest(params));
        
        if(ObjectUtils.isEmpty(page) || ObjectUtils.isEmpty(page.getContent())){
            return page;
        }
        
        //if 콘텐츠 변경
        page.getContent().forEach(item -> {
        	if(StringUtils.hasLength(item.getContents())) {
        		item.setContents(kr.co.rebel9.core.utils.StringUtils.nl2br(item.getContents()));
            }
        });
        
        return page;
    }

	@Override
	@ManagerLogExecution(process = "saveAction(MultipartFile thumbFile, MultipartFile pcViewFile, MultipartFile moViewFile, DataMap params)", menu="이벤트 관리", button="등록하기")
	public DataMap saveAction(MultipartFile thumbFile, MultipartFile pcViewFile, MultipartFile moViewFile, DataMap params) {

		if(ObjectUtils.isEmpty(thumbFile)){
			DataMap result = new DataMap(false);
			result.put("reason", "썸네일 파일을 찾을 수 없습니다.");
			return result;
        }
		if(ObjectUtils.isEmpty(pcViewFile)){
			DataMap result = new DataMap(false);
			result.put("reason", "PC용 상세 파일을 찾을 수 없습니다.");
			return result;
        }
		if(ObjectUtils.isEmpty(moViewFile)){
			DataMap result = new DataMap(false);
			result.put("reason", "모바일용 상세 파일을 찾을 수 없습니다.");
			return result;
        }
		
        UUID uuid = UUID.randomUUID();
        String dateDir = FileUtils.getDateDir();
        String thumbPath = new StringBuilder(dateDir).append(uuid).append("_t").toString();
        String pcPath = new StringBuilder(dateDir).append(uuid).append("_p").toString();
        String mobilePath = new StringBuilder(dateDir).append(uuid).append("_m").toString();

        try {
			org.apache.commons.io.FileUtils.writeByteArrayToFile(
			        new File(new StringBuilder(this.eventPath).append(thumbPath).toString()), 
			        thumbFile.getBytes());
			org.apache.commons.io.FileUtils.writeByteArrayToFile(
			        new File(new StringBuilder(this.eventPath).append(pcPath).toString()), 
			        pcViewFile.getBytes());
			org.apache.commons.io.FileUtils.writeByteArrayToFile(
			        new File(new StringBuilder(this.eventPath).append(mobilePath).toString()), 
			        moViewFile.getBytes());
		} catch (IOException e) {
			DataMap result = new DataMap(false);
			result.put("reason", e.getMessage());
			return result;
		}
		
		EventEntity event = new EventEntity();

		event.setStartDate(params.getAsString("startDate"));
		event.setEndDate(params.getAsString("endDate"));
		event.setSubject(params.getAsString("subject"));
		event.setContents(params.getAsString("contents"));
		event.setCreated(DateUtils.getToday("yyyyMMddHHmmss"));
		event.setDeleted(YesOrNo.N);

		event.setThumb(thumbPath);
		event.setPcView(pcPath);
		event.setMoView(mobilePath);
		event.setThumbContentType(thumbFile.getContentType());
		event.setPcViewContentType(pcViewFile.getContentType());
		event.setMoViewContentType(moViewFile.getContentType());
		
		this.eventRepository.save(event);
		
		return new DataMap(true);
	}

	@Override
	@ManagerLogExecution(process = "updateAction(MultipartFile thumbFile, MultipartFile pcViewFile, MultipartFile moViewFile, DataMap params)", menu="이벤트 관리", button="수정")
	public DataMap updateAction(MultipartFile thumbFile, MultipartFile pcViewFile, MultipartFile moViewFile, DataMap params) {
		
		EventEntity event = this.eventRepository.findBySnAndDeleted(params.getAsInt("sn"), YesOrNo.N);
		if(ObjectUtils.isEmpty(event)) {
			DataMap result = new DataMap(false);
			result.put("reason", "데이터를 찾을 수 없습니다.");
			return result;
		}
		
		UUID uuid = UUID.randomUUID();
        String dateDir = FileUtils.getDateDir();

        try {
        	if(!ObjectUtils.isEmpty(thumbFile)){
        		
                String thumbPath = new StringBuilder(dateDir).append(uuid).append("_t").toString();
				org.apache.commons.io.FileUtils.writeByteArrayToFile(
				        new File(new StringBuilder(this.eventPath).append(thumbPath).toString()), 
				        thumbFile.getBytes());
				
				event.setThumb(thumbPath);
				event.setThumbContentType(thumbFile.getContentType());
        	}
    		if(!ObjectUtils.isEmpty(pcViewFile)){
    			
    	        String pcPath = new StringBuilder(dateDir).append(uuid).append("_p").toString();
				org.apache.commons.io.FileUtils.writeByteArrayToFile(
				        new File(new StringBuilder(this.eventPath).append(pcPath).toString()), 
				        pcViewFile.getBytes());
				
				event.setPcView(pcPath);
				event.setPcViewContentType(pcViewFile.getContentType());
    		}
			if(!ObjectUtils.isEmpty(moViewFile)){
				
		        String mobilePath = new StringBuilder(dateDir).append(uuid).append("_m").toString();
				org.apache.commons.io.FileUtils.writeByteArrayToFile(
				        new File(new StringBuilder(this.eventPath).append(mobilePath).toString()), 
				        moViewFile.getBytes());
				
				event.setMoView(mobilePath);
				event.setMoViewContentType(moViewFile.getContentType());
			}
			
		} catch (IOException e) {
			DataMap result = new DataMap(false);
			result.put("reason", e.getMessage());
			return result;
		}

		event.setStartDate(params.getAsString("startDate"));
		event.setEndDate(params.getAsString("endDate"));
		event.setSubject(params.getAsString("subject"));
		event.setContents(params.getAsString("contents"));
				
		this.eventRepository.save(event);
		
		return new DataMap(true);
	}

	@Override
	@ManagerLogExecution(process = "removeAction(EventEntity params)", menu="이벤트 관리", button="삭제")
	public DataMap removeAction(EventEntity params) {
		
		EventEntity eventEntity = getEventEntity(params.getSn());
		if(ObjectUtils.isEmpty(eventEntity)) {
			DataMap result = new DataMap(false);
			result.put("reason", "데이터를 찾을 수 없습니다.");
			return result;
		}
		
		eventEntity.setDeleted(YesOrNo.Y);
		
		this.eventRepository.save(eventEntity);
		
		return new DataMap(true);
	}
}
