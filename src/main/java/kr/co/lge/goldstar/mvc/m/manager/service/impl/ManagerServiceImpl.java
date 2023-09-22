/**
 * 
 */
package kr.co.lge.goldstar.mvc.m.manager.service.impl;

import java.util.Calendar;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import kr.co.lge.goldstar.core.config.session.SessionConfig;
import kr.co.lge.goldstar.core.pagination.PageRequestSupport;
import kr.co.lge.goldstar.mvc.m.manager.service.ManagerLogExecution;
import kr.co.lge.goldstar.mvc.m.manager.service.ManagerService;
import kr.co.lge.goldstar.mvc.m.manager.service.ResultType;
import kr.co.lge.goldstar.orm.jpa.entity.member.ManagerEntity;
import kr.co.lge.goldstar.orm.jpa.repository.dsl.ManagerDslRepository;
import kr.co.lge.goldstar.orm.jpa.repository.spring.ManagerRepository;
import kr.co.rebel9.core.utils.DateUtils;
import kr.co.rebel9.web.data.DataMap;
import kr.co.rebel9.web.rest.result.ResultConst;
import kr.co.rebel9.web.utils.RequestUtils;
import lombok.extern.slf4j.Slf4j;


/**
 * @author issuettl
 *
 */
@Slf4j
@Service("m.ManagerService")
public class ManagerServiceImpl implements ManagerService {

    @Autowired
	private ManagerRepository managerRepository;
    
    @Autowired
    private ManagerDslRepository managerDslRepository;
	
	@Override
	@ManagerLogExecution(process = "signOut", menu="로그아웃", button="로그아웃")
	public DataMap signOut(HttpSession session) {
        session.removeAttribute(SIGNIN_KEY);
        session.invalidate();
        return new DataMap(true);
	}

	@Override
	@ManagerLogExecution(process = "signIn", menu="로그인", button="로그인")
	public DataMap signIn(ManagerEntity param) {
        
        if(!StringUtils.hasText(param.getUid())){
            return new DataMap(ResultConst.RESULT, ResultType.EMPTY_USERNAME);
        }
        
        if(!StringUtils.hasText(param.getPassword())){
            return new DataMap(ResultConst.RESULT, ResultType.EMPTY_PASSWORD);
        }
        
        HttpSession session = RequestUtils.getRequest().getSession();
        //signOut(session);
        
        String password = DigestUtils.sha512Hex(param.getUid() + param.getPassword());
        log.debug("session creationTime : " + session.getCreationTime());
        log.debug("sessionid : " + session.getId());
        log.debug("username : " + param.getUid());
        log.debug("password : " + param.getPassword());
        log.debug("encoded password : " + password);
        
        ManagerEntity manager = this.managerRepository.findByUid(param.getUid());
        
        if(ObjectUtils.isEmpty(manager)){
            return new DataMap(ResultConst.RESULT, ResultType.NOT_FOUND);
        }
        
        if(!password.equals(manager.getPassword())){
        	
        	manager.setInvalidPass(manager.getInvalidPass() + 1);
        	
        	if(manager.getInvalidPass() > 5) {
        		return new DataMap(ResultConst.RESULT, ResultType.INVALID_PASSWORD_MAX);
        	}
        	
        	//비밀번호 오류 초과횟수 저장
        	this.managerRepository.save(manager);
        	
        	DataMap dataMap = new DataMap(ResultConst.RESULT, ResultType.INVALID_PASSWORD);
        	dataMap.put("count", manager.getInvalidPass());
            return dataMap;
        }
        
        String targetDate =  DateUtils.getAddDate(manager.getUpdatedPass(), Calendar.MONTH, 6, "yyyyMMddHHmmss");
        String todate = DateUtils.getToday("yyyyMMddHHmmss");
        
        if(Long.parseLong(targetDate) < Long.parseLong(todate)) {
        	return new DataMap(ResultConst.RESULT, ResultType.CHANGE_PASSWORD);
        }
        
        /*
         * 로그인 처리
         */
        session.setAttribute(SIGNIN_KEY, manager);
        
        manager.setConnected(todate);
    	manager.setInvalidPass(0);
    	//비밀번호 오류 초과횟수 저장
    	this.managerRepository.save(manager);
        
        SessionConfig.getSessionidCheck("memberSn", manager.getSn() + "");
        session.setAttribute("memberSn", manager.getSn() + "");

        DataMap dataMap = new DataMap(true);
        
        //3개월 이상일때부터 알림
        targetDate =  DateUtils.getAddDate(manager.getUpdatedPass(), Calendar.MONTH, 3, "yyyyMMddHHmmss");
        if(Long.parseLong(targetDate) < Long.parseLong(todate)) {
        	dataMap.put("issue", ResultType.CHANGE_PASSWORD);
        }
        
        return dataMap;
	}

	@Override
	public DataMap changePassword(ManagerEntity param) {
		// TODO Auto-generated method stub
		return null;
	}

    @Override
    public ManagerEntity getSigned() {
    	return (ManagerEntity)RequestUtils.getRequest().getSession().getAttribute(SIGNIN_KEY);
    }

	@Override
	public Page<ManagerEntity> getPage(DataMap params) {

		Page<ManagerEntity> page = this.managerDslRepository.findPageBySearch(
                params, PageRequestSupport.getPageRequest(params));
        
        if(ObjectUtils.isEmpty(page) || ObjectUtils.isEmpty(page.getContent())){
            return page;
        }
        
        //if 콘텐츠 변경
        page.getContent().forEach(item -> {
        	
        	
        });
        
        return page;
	}

	@Override
	public boolean checkPassword(String password) {

		ManagerEntity managerEntity = this.getSigned();
		
		if(ObjectUtils.isEmpty(managerEntity)) {
			return false;
		}
		
		return managerEntity.getPassword().equals(DigestUtils.sha512Hex(managerEntity.getUid() + password));
	}

	@Override
	public DataMap saveAction(ManagerEntity params) {
		
		ManagerEntity managerEntity = this.managerRepository.findByUid(params.getUid());
		if(!ObjectUtils.isEmpty(managerEntity)) {
			DataMap result = new DataMap(false);
			result.put("reason", "중복된 아이디입니다.");
			return result;
		}
		
		params.setPassword(DigestUtils.sha512Hex(params.getUid() + params.getPassword()));
		params.setCreated(DateUtils.getToday("yyyyMMddHHmmss"));
		params.setUpdated(params.getCreated());
		params.setUpdatedPass(params.getCreated());
		
		if(!CollectionUtils.isEmpty(params.getAuthList())) {
			params.getAuthList().forEach(item -> {
				item.setManager(params);
			});
		}
		
		this.managerRepository.save(params);
		
		return new DataMap(true);
	}

	@Override
	public DataMap removeAction(ManagerEntity params) {

		Optional<ManagerEntity> managerEntity = this.managerRepository.findById(params.getSn());
		if(managerEntity.isEmpty()) {
			DataMap result = new DataMap(false);
			result.put("reason", "관리자 정보를 찾을 수 없습니다.");
			return result;
		}
		
		this.managerRepository.delete(managerEntity.get());

		return new DataMap(true);
	}
}
