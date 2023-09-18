package kr.co.lge.goldstar.core.pagination;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.springframework.util.StringUtils;

/**
 * TODO desc
 * @author data@rebel9.co.kr JungKyung Park
 * @since Nov 18, 2019
 * @version 1.0
 * @see
 *
 * << 개정이력(Modification Information) >>
 *
 *  수정일		수정자		수정내용
 *  -------		--------	---------------------------
 *  Nov 18, 2019		박정경		최초 생성
 */
public class PagingHelperHome extends SimpleTagSupport {

	private Paging paging;
	private int groups;
	private String context;
	private HttpServletRequest request;
	
	/**
	 * @param paging
	 */
	public void setPaging(Paging paging) {
		this.paging = paging;
	}
	
	/**
	 * @param groups the groups to set
	 */
	public void setGroups(int groups) {
		this.groups = groups;
	}

	/**
	 * @param context the context to set
	 */
	public void setContext(String context) {
		this.context = context;
	}

	/**
	 * @param request the request to set
	 */
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public void doTag() throws JspException, IOException {
		
	    String tags = getTag();
	    
	    if(!StringUtils.hasText(tags)) {
	        return;
	    }
	    
		getJspContext().getOut().println(tags);
		getJspContext().getOut().flush();
	}
	
	/**
     * @param paging 
	 * @param groups 
	 * @return
     */
    public String getTag(Paging paging, int groups){
        this.setPaging(paging);
        this.setGroups(groups);
        
        return getTag();
    }

    /**
     * @return
     */
    public String getTag(){
        
        if(paging == null){
            return null;
        }
        
        if(request != null){
            context = request.getContextPath();
        }
        
        if(context == null){
            context = "";
        }
        
        int pageSize = groups == 0 ? 10 : groups;
        int currentPageNo = paging.getNumber() + 1;
        int firstPageNo = 1;
        int firstPageNoOnPageList = (currentPageNo-1)/pageSize*pageSize + 1;
        int totalPageCount = paging.getTotalPages();
        int lastPageNoOnPageList = firstPageNoOnPageList + pageSize - 1;
        if(lastPageNoOnPageList > totalPageCount){
            lastPageNoOnPageList = totalPageCount;
        }
        int lastPageNo = totalPageCount;
        /*
        <ul class="pagination">
            <li><button class="first disabled"></button></li>
            <li><button class="prev disabled"></button></li>
            <!-- 선택할 수 없는 page는 disabled 클래스 추가
            <li><button class="first"></button></li>
            <li><button class="prev"></button></li>
            -->
            <!-- 숫자 -->
            <li><button class="page on">1</li>
            <li><button class="page">2</li><!-- 해당 페이지일때 on -->
            <li><button class="page">3</li>
            <li><button class="page">4</button></li>
            <li><button class="page">5</button></li>
            <!-- pc에서는 10개까지 최대 노출됩니다.
            <li><button class="page">6</button></li>
            <li><button class="page">7</button></li>
            <li><button class="page">8</button></li>
            <li><button class="page">9</button></li>
            <li><button class="page">10</button></li>
            -->
            <!-- 숫자 end -->
            <li><button class="next"></button></li>
            <li><button class="last"></button></li>
        </ul>
         * */
        
        StringBuilder sb = new StringBuilder();
        
        if(paging.getTotalElements() > 0) {
            sb.append("<ul class=\"pagination\">");
        }
        
        if(totalPageCount > pageSize){
            if(firstPageNoOnPageList > pageSize){
                sb.append(" <li><button class=\"prev\" data-page=\"").append(firstPageNoOnPageList-1).append("\"></button></li>");
            }else{
                sb.append(" <li><button class=\"prev\" data-page=\"").append(firstPageNo).append("\"></button></li>");
            }
        }
        
        for(int i=firstPageNoOnPageList;i<=lastPageNoOnPageList;i++){
            if(i==currentPageNo){
                sb.append(" <li><button class=\"page on\" data-page=\"").append(i).append("\">").append(i).append("</li>");
            }else{
                sb.append(" <li><button class=\"page\" data-page=\"").append(i).append("\">").append(i).append("</li>");
            }
        }
        
        if(totalPageCount > pageSize){
            if(lastPageNoOnPageList < totalPageCount){
                sb.append(" <li><button class=\"next\" data-page=\"").append(firstPageNoOnPageList+pageSize).append("\"></button></li>");
            }else{
                sb.append(" <li><button class=\"next\" data-page=\"").append(lastPageNo).append("\"></button></li>");
            }
        }
        
        if(paging.getTotalElements() > 0) {
            sb.append("</ul>");
        }
        
        return sb.toString();
    }
}
