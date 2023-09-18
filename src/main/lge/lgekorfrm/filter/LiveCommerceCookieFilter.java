package lgekorfrm.filter;

import devonframe.configuration.ConfigService;
import devonframe.util.NullUtil;
import lgekorfrm.context.WebContext;
import lgekorfrm.util.ServletUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component("liveCommerceCookieFilter")
public class LiveCommerceCookieFilter implements Filter {
    private static final Logger LOGGER = LoggerFactory.getLogger(LiveCommerceCookieFilter.class);
    
    @Resource(name = "configService")
    private ConfigService configService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }
    
    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        if("/health".equals(request.getRequestURI())) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        if(WebContext.isApi()) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        if(ServletUtils.isWebResource(request)) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        List<Object> addCookieList = configService.getList("addCookieList",null);

        if(addCookieList != null && addCookieList.size() > 0){
	        Enumeration<String> params = request.getParameterNames();
	        while(params.hasMoreElements()) {
	        	String name = params.nextElement();        	        	
	        	if (matchCookieName(name,addCookieList) && NullUtil.notNone(request.getParameter(name))) {        	                        		        		                		       		       
	            	Cookie cookie = new Cookie(name, request.getParameter(name));
	    			cookie.setPath("/");
	    			String domain = configService.getString("serverInfo.domain");
	    			cookie.setDomain(domain);						
	    			response.addCookie(cookie);						        
	            }
	        }    
        }
        
        filterChain.doFilter(servletRequest, servletResponse);
    }
    
    public boolean matchCookieName(String paramName, List<Object> addCookieList) {		
		List<String> cookieList = addCookieList.stream().map(object -> Objects.toString(object, null))
				.collect(Collectors.toList());

		for (String cookie : cookieList) {
			if (cookie == null)
				continue;
				if(paramName.equals(cookie)){
					return true;
				}					
		}
		return false;
	}
}
