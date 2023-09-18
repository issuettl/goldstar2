package kr.co.lge.goldstar.mvc.m.event.service;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import kr.co.lge.goldstar.orm.jpa.entity.event.EventEntity;
import kr.co.rebel9.web.data.DataMap;

/**
 * @author cerakoted, base@rebel9.co.kr, cerakoted@gmail.com
 * @since 1.0
 * @date 2019. 11. 15
 *
 */
public interface EventService {
    
    /**
     * @param bbsSn 
     * @param sn
     * @return
     */
     EventEntity getEventEntity(int sn);

    /**
     * @param bbsSn 
     * @param params
     * @return
     */
    Page<EventEntity> getPage(DataMap params);

	/**
	 * @param moViewFile 
	 * @param pcViewFile 
	 * @param thumbFile 
	 * @param params
	 * @return
	 */
	DataMap saveAction(MultipartFile thumbFile, MultipartFile pcViewFile, MultipartFile moViewFile, DataMap params);

	/**
	 * @param moViewFile 
	 * @param pcViewFile 
	 * @param thumbFile 
	 * @param params
	 * @return
	 */
	DataMap updateAction(MultipartFile thumbFile, MultipartFile pcViewFile, MultipartFile moViewFile, DataMap params);

	/**
	 * @param eventEntity
	 * @return
	 */
	DataMap removeAction(EventEntity eventEntity);

}
