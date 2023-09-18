/**
 * 
 */
package kr.co.lge.goldstar.mvc.m.refresh.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import kr.co.lge.goldstar.mvc.m.refresh.service.RefreshService;
import kr.co.lge.goldstar.mvc.u.sign.service.SignService;
import kr.co.lge.goldstar.orm.jpa.entity.StaffCheck;
import kr.co.lge.goldstar.orm.jpa.entity.member.SignEntity;
import kr.co.lge.goldstar.orm.jpa.entity.member.SignId;
import kr.co.lge.goldstar.orm.jpa.entity.refresh.RefreshPartEntity;
import kr.co.lge.goldstar.orm.jpa.repository.spring.RefreshPartRepository;
import kr.co.lge.goldstar.orm.jpa.repository.spring.SignRepository;
import kr.co.rebel9.core.utils.DateUtils;
import kr.co.rebel9.web.data.DataMap;

/**
 * @author issuettl
 *
 */
@Service("m.RefreshService")
public class RefreshServiceImpl implements RefreshService {

	@Autowired
	private RefreshPartRepository refreshPartRepository;

	@Autowired
	private SignRepository signRepository;
	
	@Autowired
	private SignService signService;

	@Override
	public DataMap memberAnswers(SignId signId) {
		
		Optional<SignEntity> signEntity = this.signRepository.findById(signId);
		
		if(signEntity.isEmpty()) {
			DataMap result = new DataMap(false);
			result.put("reason", "등록되지 않은 회원입니다.");
			result.put("code", 1001);
			return result;
		}
		
		RefreshPartEntity saved = this.refreshPartRepository.findBySignMemberSnAndSignCreated(signId.getMemberSn(), signId.getCreated());
		if(!ObjectUtils.isEmpty(saved)) {
			DataMap result = new DataMap(false);
			result.put("reason", "이미 등록된 코너입니다.");
			result.put("code", 1002);
			return result;
		}
		
		RefreshPartEntity refreshEntity = new RefreshPartEntity();
		refreshEntity.setSignMemberSn(signId.getMemberSn());
		refreshEntity.setSignCreated(signId.getCreated());
		refreshEntity.setCreated(DateUtils.getToday("yyyyMMddHHmmss"));

		refreshEntity.setStaffCreated(refreshEntity.getCreated());
		refreshEntity.setStaffCheck(StaffCheck.present);
		this.refreshPartRepository.save(refreshEntity);
		
		DataMap result = new DataMap(true);
		result.put("saved", refreshEntity);
		
		return result;
	}

	@Override
	public RefreshPartEntity getRefreshPart() {
		
		SignEntity signEntity = this.signService.getSignIn();
		
		return this.refreshPartRepository.findBySignMemberSnAndSignCreated(signEntity.getId().getMemberSn(), signEntity.getId().getCreated());
	}

	@Override
	public RefreshPartEntity getRefreshPart(int memberSn, String created) {
		return this.refreshPartRepository.findBySignMemberSnAndSignCreated(memberSn, created);
	}
}
