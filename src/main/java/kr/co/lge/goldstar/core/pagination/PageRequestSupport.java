/**
 * 
 */
package kr.co.lge.goldstar.core.pagination;

import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import kr.co.rebel9.web.data.DataMap;
import lombok.extern.slf4j.Slf4j;

/**
 * @author issuettl
 *
 */
@Slf4j
public class PageRequestSupport {
	
	private static int page = 0;
	private static int size = 100;

	/**
	 * @param page
	 * @param size
	 * @param direction 
	 * @param sort 
	 * @return 페이지 요청 객체
	 */
	public static PageRequest getPageRequest(int page, int size, Optional<Direction> direction, String sort){
		
		if(ObjectUtils.isEmpty(direction) || !StringUtils.hasText(sort)){
		    
	        log.debug("new PageRequest page {}, size {}", page, size);
			
	        return PageRequest.of(page, size);
		}
		
		log.debug("getPageRequest page {}, size {}, direction {}, sort {}", page, size, direction, sort);
		
		return getPageRequest(page, size, Sort.by(direction.get(), sort));
	}

	/**
	 * @param page
	 * @param size
	 * @param sort 
	 * @return 페이지 요청 객체
	 */
	public static PageRequest getPageRequest(int page, int size, Sort sort){
	    
	    log.debug("new PageRequest page {}, size {}, sort {}", page, size, sort);
	    
		return PageRequest.of(page, size, sort);
	}

	/**
	 * @param pageString
	 * @param sizeString
	 * @param direction 
	 * @param sort 
	 * @return 페이지 요청 객체
	 */
	public static PageRequest getPageRequest(String pageString, String sizeString, Optional<Direction> direction, String sort){
		
		int page = 0;
		int size = 0;

		try {
			page = Integer.parseInt(pageString) - 1;
		} catch(NumberFormatException e){
			page = PageRequestSupport.page;
		}
		try {
			size = Integer.parseInt(sizeString);
		} catch(NumberFormatException e){
			size = PageRequestSupport.size;
		}
        
        log.debug("getPageRequest page {}, size {}, direction {}, sort {}", pageString, sizeString, direction, sort);
		
		return getPageRequest(page, size, direction, sort);
	}

	/**
	 * @param dataMap
	 * @return 페이지 요청 객체
	 */
	public static PageRequest getPageRequest(DataMap dataMap) {
		return getPageRequest(
				dataMap.getAsString("page"), 
				dataMap.getAsString("size"),
				Direction.fromOptionalString(dataMap.getAsString("direction")),
				dataMap.getAsString("sort"));
	}
}
