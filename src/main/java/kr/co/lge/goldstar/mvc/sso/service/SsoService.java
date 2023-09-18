/**
 * 
 */
package kr.co.lge.goldstar.mvc.sso.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.rebel9.web.data.DataMap;

/**
 * @author NEOFLOW
 *
 */
public interface SsoService {

	/**
	 * @return
	 */
	DataMap loginInfo();

	/**
	 * @param params
	 */
	void saveResult(DataMap params);

	/**
	 * @param params
	 * @param request
	 * @param response
	 */
	DataMap setAccountInfo(DataMap params, HttpServletRequest request, HttpServletResponse response);

	/**
	 * @param request
	 */
	void setAccountInfo(HttpServletRequest request, HttpServletResponse response);

}
