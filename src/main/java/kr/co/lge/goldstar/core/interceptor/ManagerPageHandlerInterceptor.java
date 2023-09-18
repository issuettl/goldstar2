/**
 * 
 */
package kr.co.lge.goldstar.core.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import kr.co.lge.goldstar.mvc.m.manager.service.ManagerService;
import kr.co.rebel9.core.utils.DateUtils;
import kr.co.rebel9.web.utils.RequestUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * @author issuettl, data@rebel9.co.kr
 * @since 1.0
 * @date 2018. 4. 10.
 *
 */
@Slf4j
public class ManagerPageHandlerInterceptor implements HandlerInterceptor, InitializingBean {

	@Autowired
	private ManagerService managerService;
	
    private String versionHtml;
    
    @Override
    public boolean preHandle(HttpServletRequest request,
            HttpServletResponse response, Object handler) throws Exception {
        
        return true;
    }

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
		response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
		response.setDateHeader("Expires", 0); // Proxies.
		
		
		if("XMLHttpRequest".equals(request.getHeader("x-requested-with"))) {
			return;
		}
		
		if(ObjectUtils.isEmpty(modelAndView)) {
			return;
		}
		
		if(isRedirect(modelAndView)) {
			return;
		}

		if(request.getServerName().startsWith("localhost")) {
    		this.versionHtml = DateUtils.getToday("yyyyMMddHHmmssSSS");
    	}
    	
        modelAndView.addObject("versionHtml", this.versionHtml);
        modelAndView.addObject("currentURI", RequestUtils.getRequestURIExcludeContextPath());
        modelAndView.addObject("signManager", this.managerService.getSigned());
	}
	
	private boolean isRedirect(ModelAndView modelAndView) {
		return modelAndView.getView() instanceof RedirectView || modelAndView.getViewName().startsWith("redirect:");
	}

	@Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        
    }

	@Override
	public void afterPropertiesSet() throws Exception {
		this.versionHtml = DateUtils.getToday("yyyyMMddHHmmssSSS");
	}
}
