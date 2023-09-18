/**
 * 
 */
package kr.co.lge.goldstar.mvc.m.manager.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.lge.goldstar.mvc.m.manager.service.ManagerService;
import kr.co.lge.goldstar.orm.jpa.entity.member.ManagerEntity;
import kr.co.rebel9.web.data.DataMap;
import kr.co.rebel9.web.rest.result.ResultBuilder;
import kr.co.rebel9.web.rest.result.ResultConst;

/**
 * @author issuettl
 *
 */
@RestController("m.ManagerRestController")
@RequestMapping(value = "m/manager")
public class ManagerRestController {

	@Autowired
	private ManagerService managerService;
	
	/**
	 * @param param
	 * @param session
	 * @return 로그인 결과
	 */
	@PostMapping("in/action.do")
	public DataMap signIn(@RequestBody ManagerEntity param) {
        try {
            return this.managerService.signIn(param);
        }catch(Exception e) {
			DataMap result = new DataMap(false);
			result.put(ResultConst.META, ResultBuilder.failure(e));
			return result;
		}
	}
	
	/**
	 * @param param
	 * @param session
	 * @return 로그인 결과
	 */
	@PostMapping("password/action.do")
	public DataMap changePassword(@RequestBody ManagerEntity param) {
        try {
            return this.managerService.changePassword(param);
        }catch(Exception e) {
			DataMap result = new DataMap(false);
			result.put(ResultConst.META, ResultBuilder.failure(e));
			return result;
		}
	}
	
	/**
	 * @param param
	 * @param session
	 * @return 로그인 결과
	 */
	@PostMapping("save/action.do")
	public DataMap save(@RequestBody ManagerEntity managerEntity) {
        try {
            return this.managerService.saveAction(managerEntity);
        }catch(Exception e) {
			DataMap result = new DataMap(false);
			result.put(ResultConst.META, ResultBuilder.failure(e));
			return result;
		}
	}
	
	/**
	 * @param param
	 * @param session
	 * @return 로그인 결과
	 */
	@PostMapping("remove/action.do")
	public DataMap remove(@RequestBody ManagerEntity managerEntity) {
        try {
            return this.managerService.removeAction(managerEntity);
        }catch(Exception e) {
			DataMap result = new DataMap(false);
			result.put(ResultConst.META, ResultBuilder.failure(e));
			return result;
		}
	}
	
	/**
	 * @param session
	 * @return 로그아웃 결과
	 */
	@PostMapping("out/action.do")
	public DataMap signOut(HttpSession session) {
		DataMap data = new DataMap();
        try {
            
            this.managerService.signOut(session);
            data.put(ResultConst.RESULT, ResultConst.SUCCESS);
            data.put(ResultConst.META, ResultBuilder.success());

        } catch (Exception e) {
            e.printStackTrace();
            data.put(ResultConst.META, ResultBuilder.failure(e));
        }
        
        return data;
	}
}
