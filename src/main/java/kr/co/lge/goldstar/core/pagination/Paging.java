/**
 * 
 */
package kr.co.lge.goldstar.core.pagination;

import org.springframework.data.domain.Page;
import org.springframework.util.ObjectUtils;

import lombok.Data;

/**
 * @author issuettl
 *
 */
@Data
public class Paging {

	private int totalPages;
	private long totalElements;
	private boolean last;
	private boolean first;
	private int size;
	private int number;
	private int numberOfElements;
	private long numberOfPages;
	
	/**
	 * @param pages 
	 * 
	 */
	public Paging(Page<?> pages) {
	    
	    if(ObjectUtils.isEmpty(pages)) {
	        return;
	    }
	    
		setTotalPages(pages.getTotalPages());
		setTotalElements(pages.getTotalElements());
		setLast(pages.isLast());
		setFirst(pages.isFirst());
		setSize(pages.getSize());
		setNumber(pages.getNumber());
		setNumberOfElements(pages.getNumberOfElements());
		setNumberOfPages(pages.getTotalElements() - (pages.getNumber() * pages.getSize()));
	}
}
