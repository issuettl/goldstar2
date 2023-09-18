/**
 * 
 */
package kr.co.lge.goldstar.mvc.m.manager.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import kr.co.lge.goldstar.core.pagination.Paging;
import kr.co.lge.goldstar.mvc.m.manager.service.ManagerService;
import kr.co.lge.goldstar.orm.jpa.entity.ManagerAuth;
import kr.co.lge.goldstar.orm.jpa.entity.member.ManagerEntity;
import kr.co.rebel9.web.data.DataMap;

/**
 * @author issuettl
 *
 */
@Controller("m.ManagerController")
@RequestMapping("m/manager")
public class ManagerController {

    @Autowired
    private ManagerService managerService;

	/**
	 * @param session 
	 * @return 인증페이지
	 */
	@GetMapping("in.do")
	public String signin(HttpSession session){
		
		session.invalidate();
		
		return "m/manager/in";
	}

	/**
	 * @param session 
	 * @return 인증페이지
	 */
	@GetMapping("password.do")
	public String changePassword(HttpSession session){
		
		return "m/manager/password";
	}

    /**
     * @param session 
     * @return 인증페이지
     */
    @GetMapping("out.do")
    public RedirectView out(HttpSession session){
        
        this.managerService.signOut(session);
        
        return new RedirectView("/m/manager/in.do", true, false, false);
    }
    
    @RequestMapping(value = "list.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String list(@RequestParam MultiValueMap<String, Object> paramMap, ModelMap model) {
		
		DataMap params = new DataMap(paramMap);
        params.put("size", params.getAsInt("size", 10));
        
        Page<ManagerEntity> page = this.managerService.getPage(params);
        
        model.put("page", page);
        model.put("paging", new Paging(page));
        
        model.put("managerAuth", ManagerAuth.values());
        
        return "m/manager/list";
	}

}
