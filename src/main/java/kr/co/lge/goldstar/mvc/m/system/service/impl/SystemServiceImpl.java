/**
 * 
 */
package kr.co.lge.goldstar.mvc.m.system.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.lge.goldstar.mvc.m.manager.service.ManagerLogExecution;
import kr.co.lge.goldstar.mvc.m.system.service.SystemService;
import kr.co.lge.goldstar.orm.jpa.entity.system.SystemEntity;
import kr.co.lge.goldstar.orm.jpa.repository.spring.SystemRepository;
import kr.co.rebel9.web.data.DataMap;

/**
 * @author issuettl
 *
 */
@Service("SystemService")
public class SystemServiceImpl implements SystemService {

	@Autowired
	private SystemRepository systemRepository;

	@Override
	public SystemEntity getEntity(int sn) {
		
		return this.systemRepository.findById(sn).get();
	}

	@Override
	@ManagerLogExecution(process = "survey(SystemEntity systemEntity)", menu="내부활용문답 리스트", button="활성화/비활성화")
	public DataMap survey(SystemEntity systemEntity) {
		
		Optional<SystemEntity> saved = this.systemRepository.findById(1);
		if(saved.isEmpty()) {
			DataMap result = new DataMap(false);
			result.put("reason", "시스템 데이터를 찾을 수 없습니다.");
			return result;
		}
		
		SystemEntity savedEntity = saved.get();
		savedEntity.setSurvey(systemEntity.getSurvey());
		this.systemRepository.save(savedEntity);
		
		return new DataMap(true);
	}

	@Override
	@ManagerLogExecution(process = "product(SystemEntity systemEntity)", menu="체험제품관리 리스트", button="활성화/비활성화")
	public DataMap product(SystemEntity systemEntity) {
		
		Optional<SystemEntity> saved = this.systemRepository.findById(1);
		if(saved.isEmpty()) {
			DataMap result = new DataMap(false);
			result.put("reason", "시스템 데이터를 찾을 수 없습니다.");
			return result;
		}
		
		SystemEntity savedEntity = saved.get();
		savedEntity.setProduct(systemEntity.getProduct());
		this.systemRepository.save(savedEntity);
		
		return new DataMap(true);
	}

}
