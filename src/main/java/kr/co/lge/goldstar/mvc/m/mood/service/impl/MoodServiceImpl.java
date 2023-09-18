/**
 * 
 */
package kr.co.lge.goldstar.mvc.m.mood.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import kr.co.lge.goldstar.mvc.m.mood.service.MoodService;
import kr.co.lge.goldstar.orm.jpa.entity.StaffCheck;
import kr.co.lge.goldstar.orm.jpa.entity.member.SignEntity;
import kr.co.lge.goldstar.orm.jpa.entity.member.SignId;
import kr.co.lge.goldstar.orm.jpa.entity.mood.MoodPartEntity;
import kr.co.lge.goldstar.orm.jpa.repository.spring.MoodPartRepository;
import kr.co.lge.goldstar.orm.jpa.repository.spring.SignRepository;
import kr.co.rebel9.web.data.DataMap;

/**
 * @author NEOFLOW
 *
 */
@Service("m.MoodService")
public class MoodServiceImpl implements MoodService {

	@Autowired
	private MoodPartRepository moodPartRepository;

	@Autowired
	private SignRepository signRepository;
	
	@Override
	public DataMap memberAnswers(SignId signId) {
		
		Optional<SignEntity> signEntity = this.signRepository.findById(signId);
		
		if(signEntity.isEmpty()) {
			DataMap result = new DataMap(false);
			result.put("reason", "등록되지 않은 회원입니다.");
			result.put("code", 1001);
			return result;
		}
		
		MoodPartEntity saved = this.moodPartRepository.findBySignMemberSnAndSignCreated(signId.getMemberSn(), signId.getCreated());
		if(ObjectUtils.isEmpty(saved)) {
			DataMap result = new DataMap(false);
			result.put("reason", "사전 문답이 없습니다.");
			result.put("code", 1002);
			return result;
		}
		
		saved.setStaffCreated(saved.getCreated());
		saved.setStaffCheck(StaffCheck.present);
		this.moodPartRepository.save(saved);
		
		DataMap result = new DataMap(true);
		result.put("saved", saved);
		
		return result;
	}

	@Override
	public MoodPartEntity getMoodPart(int memberSn, String created) {
		return this.moodPartRepository.findBySignMemberSnAndSignCreated(memberSn, created);
	}
}
