/**
 * 
 */
package kr.co.lge.goldstar.mvc.c.request.service.impl;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import kr.co.lge.goldstar.mvc.c.request.service.RequestService;
import kr.co.rebel9.web.utils.RequestUtils;

/**
 * @author NEOFLOW
 *
 */
@Service("c.RequestService")
public class RequestServiceImpl implements RequestService {

	@Override
	public String getIp() {
		
		HttpServletRequest request = RequestUtils.getRequest();
		
		String xForwardFor = RequestUtils.getRequest().getHeader("x-forwarded-for");
		if(StringUtils.isNotEmpty(xForwardFor)) {
			if(xForwardFor.indexOf(",") > -1) {
				String[] xForwardFors = xForwardFor.split(",");
				return xForwardFors[0].trim();
			}
		}
		
		return request.getRemoteAddr();
	}
}
