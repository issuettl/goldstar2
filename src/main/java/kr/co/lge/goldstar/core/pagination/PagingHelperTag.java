package kr.co.lge.goldstar.core.pagination;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class PagingHelperTag extends SimpleTagSupport {

	private Paging paging;
	private int groups;
	private String context;
	private HttpServletRequest request;
	
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
		
		if(paging == null){
			return;
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
		<div class="ui pagination menu">
            <a class="icon item">
              <i class="left chevron icon"></i>
            </a>
            <a class="item">1</a>
            <a class="item">2</a>
            <a class="item">3</a>
            <a class="item">4</a>
            <a class="icon item">
              <i class="right chevron icon"></i>
            </a>
        </div>
		 * */
		
		StringBuilder sb = new StringBuilder();
		
		if(paging.getTotalElements() > 0) {
		    sb.append("<div class=\"ui pagination menu\">");
		}
		
		if(totalPageCount > pageSize){
			if(firstPageNoOnPageList > pageSize){
				sb.append(" <a class=\"page-buttons icon item\" data-page=\"").append(firstPageNoOnPageList-1).append("\"><i class=\"left chevron icon\"></i></a>");
	        }else{
	        	    sb.append(" <a class=\"page-buttons icon item\" data-page=\"").append(firstPageNo).append("\"><i class=\"left chevron icon\"></i></a>");
	        }
		}
		
		for(int i=firstPageNoOnPageList;i<=lastPageNoOnPageList;i++){
			if(i==currentPageNo){
	    		sb.append(" <a class=\"page-buttons item active\" data-page=\"").append(i).append("\"><b>").append(i).append("</b></a>");
	    	}else{
	    		sb.append(" <a class=\"page-buttons item\" data-page=\"").append(i).append("\">").append(i).append("</a>");
	    	}
	    }
	    
		if(totalPageCount > pageSize){
			if(lastPageNoOnPageList < totalPageCount){
				sb.append(" <a class=\"page-buttons icon item\" data-page=\"").append(firstPageNoOnPageList+pageSize).append("\"><i class=\"right chevron icon\"></i></a>");
	        }else{
	        	    sb.append(" <a class=\"page-buttons icon item\" data-page=\"").append(lastPageNo).append("\"><i class=\"right chevron icon\"></i></a>");
	        }
		}
		
		if(paging.getTotalElements() > 0) {
		    sb.append("</div>");
		}
		
		getJspContext().getOut().println(sb.toString());
		getJspContext().getOut().flush();
	}
}
