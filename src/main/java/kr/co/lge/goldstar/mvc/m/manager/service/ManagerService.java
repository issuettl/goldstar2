/**
 * 
 */
package kr.co.lge.goldstar.mvc.m.manager.service;

import javax.servlet.http.HttpSession;

import org.springframework.data.domain.Page;

import kr.co.lge.goldstar.orm.jpa.entity.member.ManagerEntity;
import kr.co.rebel9.web.data.DataMap;


/**
 * @author issuettl
 *
 */
public interface ManagerService {
    
    public static final String SIGNIN_KEY = "AUTH-MANAGER";

	/**
	 * @param session
	 * @return 로그아웃 결과
	 */
	public DataMap signOut(HttpSession session);

	/**
	 * @param param
	 * @param session
	 * @return 로그인 결과
	 */
	public DataMap signIn(ManagerEntity param);

    /**
     * @return
     */
    public ManagerEntity getSigned();

	/**
	 * @param params
	 * @return
	 */
	public Page<ManagerEntity> getPage(DataMap params);

	/**
	 * @param param
	 * @return
	 */
	public DataMap changePassword(ManagerEntity param);

	/**
	 * @param asString
	 * @return 
	 */
	public boolean checkPassword(String asString);

	/**
	 * @param dataMap
	 * @return
	 */
	public DataMap saveAction(ManagerEntity managerEntity);

	/**
	 * @param managerEntity
	 * @return
	 */
	public DataMap removeAction(ManagerEntity managerEntity);
}
